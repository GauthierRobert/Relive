package be.relive.Global.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

    private static final String USER_EXCHANGE = "user";

    @Bean(name = "userExchange")
    public TopicExchange userExchange() {
        return new TopicExchange(USER_EXCHANGE);
    }

}
