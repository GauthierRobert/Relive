package be.relive.Global.group.service.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import be.relive.Global.dto.UserDto;
import be.relive.Global.group.service.SubscriberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class SubscriptionListener {
    public static final String SUBSCRIBE_QUEUE = "subscribeQueue";
    public static final String UNSUBSCRIBE_QUEUE = "unsubscribeQueue";
    private static final Logger logger = LoggerFactory.getLogger(SubscriptionListener.class);

    private final SubscriberService subscriberService;

    public SubscriptionListener(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @RabbitListener(queues = SUBSCRIBE_QUEUE, concurrency = "5")
    public void receiveSubscribeMessage(Message message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        UserDto userDto = objectMapper.readValue(objectMapper.readValue(message.getBody(), String.class), UserDto.class);
        subscriberService.addSubscriber(userDto);
    }

    @RabbitListener(queues = UNSUBSCRIBE_QUEUE, concurrency = "5")
    public void receiveUnsubscribeMessage(Message message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        UserDto userDto = objectMapper.readValue(objectMapper.readValue(message.getBody(), String.class), UserDto.class);
        subscriberService.removeSubscriber(userDto);
    }
}
