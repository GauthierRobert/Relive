package be.relive.Global.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubscriptionConfiguration {

    private static final String SUBSCRIPTION_EXCHANGE = "subscription";
    public static final String SUBSCRIBE_QUEUE = "subscribeQueue";
    public static final String UNSUBSCRIBE_QUEUE = "unsubscribeQueue";

    @Bean(name = "subscriptionExchange")
    public TopicExchange subscriptionExchange() {
        return new TopicExchange(SUBSCRIPTION_EXCHANGE);
    }


    @Bean(name = "subscribeQueue")
    Queue subscribeQueue() {
        return new Queue(SUBSCRIBE_QUEUE, false);
    }

    @Bean("subscribeBinding")
    Binding subscribeBinding() {
        return BindingBuilder.bind(subscribeQueue()).to(subscriptionExchange()).with("group.subscribe");
    }

    @Bean(name = "unsubscribeQueue")
    Queue unsubscribeQueue() {
        return new Queue(UNSUBSCRIBE_QUEUE, false);
    }

    @Bean("unsubscribeBinding")
    Binding unsubscribeBinding() {
        return BindingBuilder.bind(unsubscribeQueue()).to(subscriptionExchange()).with("group.unsubscribe");
    }
}
