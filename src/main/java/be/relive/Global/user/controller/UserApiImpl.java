package be.relive.Global.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import be.relive.Global.user.domain.FacebookProfile;
import be.relive.Global.user.domain.Subscription;
import be.relive.Global.user.domain.User;
import be.relive.Global.user.dto.UserDto;
import be.relive.Global.user.service.FacebookService;
import be.relive.Global.user.service.SubscriptionService;
import be.relive.Global.user.service.UserService;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

import static be.relive.Global.user.domain.builder.UserBuilder.anUser;
import static be.relive.Global.user.domain.builder.UserBuilder.modify;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class UserApiImpl implements UserApi {

    private final FacebookService facebookService;
    private final UserService userService;
    private final SubscriptionService subscriptionService;

    @Qualifier("userExchange")
    private final TopicExchange userExchange;
    @Qualifier("subscriptionExchange")
    private final TopicExchange subscriptionExchange;
    private final RabbitTemplate rabbitTemplate;


    public UserApiImpl(FacebookService facebookService, UserService userService, SubscriptionService subscriptionService, TopicExchange userExchange, TopicExchange subscriptionExchange, RabbitTemplate rabbitTemplate) {
        this.facebookService = facebookService;
        this.userService = userService;
        this.subscriptionService = subscriptionService;
        this.userExchange = userExchange;
        this.subscriptionExchange = subscriptionExchange;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public ResponseEntity login(String token) throws IOException {

        FacebookProfile facebookProfile = facebookService.getFacebookProfile(token);

        User user = userService.findByFacebookId(facebookProfile.getId());

        if (user == null) {
            user = userService.save(anUser().withProfile(facebookProfile).build());
        } else if (!user.getProfile().equals(facebookProfile)) {
            user = userService.save(modify(user).withProfile(facebookProfile).build());
            rabbitTemplate.convertAndSend(userExchange.getName(), "user.updated", convert(user));
        }

        return ok().body(convert(user));
    }

    @Override
    public ResponseEntity Subscribe(UUID userId, String key) throws JsonProcessingException {
        User user = userService.findById(userId);
        subscriptionService.subscribe(user, key);

        String jsonUser = new ObjectMapper().writeValueAsString(convert(user, key));

        rabbitTemplate.convertAndSend(subscriptionExchange.getName(), "group.subscribe", jsonUser);
        return ok().build();
    }

    @Override
    public ResponseEntity unSubscribe(UUID userId, String key) throws JsonProcessingException {
        User user = userService.findById(userId);
        subscriptionService.unSubscribe(user, key);

        String jsonUser = new ObjectMapper().writeValueAsString(convert(user, key));

        rabbitTemplate.convertAndSend(subscriptionExchange.getName(), "group.unsubscribe", jsonUser);
        return ok().build();
    }

    private UserDto convert(User user) {
        return new UserDto(user.getId(),
                user.getProfile().getName(),
                user.getProfile().getImageUrl(),
                user.getSubscriptions().stream().map(Subscription::getKey).collect(Collectors.toList()));
    }

    private UserDto convert(User user, String key) {
        return new UserDto(user.getId(),
                user.getProfile().getName(),
                user.getProfile().getImageUrl(),
                Collections.singletonList(key));
    }
}
