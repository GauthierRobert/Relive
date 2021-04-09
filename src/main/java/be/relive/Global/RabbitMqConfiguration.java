package be.relive.Global;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * At initialization, the action processor have to create dynamically one queue
 * for each type of procedure type they can handle, and to bind each queue to
 * the “action-requests” exchange with the routing key corresponding to the “procType”.
 */
@Configuration
public class RabbitMqConfiguration {
    @Value("${amqp.uri:localhost:5672}")
    private String uri;
    @Value("${amqp.username:guest}")
    private String username;
    @Value("${amqp.password:guest}")
    private String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUri(uri);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }


    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setMessageConverter(jsonMessageConverter());
        template.setConnectionFactory(connectionFactory());
        return template;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * @return the admin bean that can declare queues etc.
     */
    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    private static final String NOTIFICATION_EXCHANGE = "notifications";

    @Bean(name = "notificationExchange")
    public TopicExchange notificationExchange() {
        return new TopicExchange(NOTIFICATION_EXCHANGE);
    }
}
