package be.relive.Global.user.controller;

import be.relive.Global.dto.UserDto;
import be.relive.Global.group.service.SubscriberService;
import be.relive.Global.user.domain.Subscription;
import be.relive.Global.user.domain.User;
import be.relive.Global.user.domain.facebook.FacebookProfile;
import be.relive.Global.user.service.FacebookService;
import be.relive.Global.user.service.SubscriptionService;
import be.relive.Global.user.service.UserService;
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
    private final SubscriberService subscriberService;


    public UserApiImpl(FacebookService facebookService, UserService userService, SubscriptionService subscriptionService, SubscriberService subscriberService) {
        this.facebookService = facebookService;
        this.userService = userService;
        this.subscriptionService = subscriptionService;
        this.subscriberService = subscriberService;
    }

    @Override
    public ResponseEntity login(String token) throws IOException {

        FacebookProfile facebookProfile = facebookService.getFacebookProfile(token);

        User user = userService.findByFacebookId(facebookProfile.getId());

        if (user == null) {
            user = userService.save(anUser().withProfile(facebookProfile).build());
        } else if (!user.getProfile().equals(facebookProfile)) {
            user = userService.save(modify(user).withProfile(facebookProfile).build());
        }

        return ok().body(convert(user));
    }

    @Override
    public ResponseEntity Subscribe(UUID userId, String key) {
        User user = userService.findById(userId);
        subscriptionService.subscribe(user, key);

        subscriberService.addSubscriber(convert(user, key));

        return ok().build();
    }

    @Override
    public ResponseEntity unSubscribe(UUID userId, String key) {
        User user = userService.findById(userId);
        subscriptionService.unSubscribe(user, key);
        subscriberService.removeSubscriber(convert(user, key));
        return ok().build();
    }

    private UserDto convert(User user) {
        return new UserDto(user.getId(),
                user.getProfile().getName(),
                user.getProfile().getPicture().getData().getUrl(),
                user.getSubscriptions().stream().map(Subscription::getKey).collect(Collectors.toList()));
    }

    private UserDto convert(User user, String key) {
        return new UserDto(user.getId(),
                user.getProfile().getName(),
                user.getProfile().getPicture().getData().getUrl(),
                Collections.singletonList(key));
    }
}
