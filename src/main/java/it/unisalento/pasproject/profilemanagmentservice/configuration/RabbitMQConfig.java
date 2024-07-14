package it.unisalento.pasproject.profilemanagmentservice.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQConfig is a configuration class that sets up the RabbitMQ message broker.
 * It defines the queues, exchanges, and bindings used in the application, as well as the message converter and AMQP template.
 */
@Configuration
public class RabbitMQConfig {
    // ------  DATA AUTH  ------ //

    // Needed by authentication service
    @Value("${rabbitmq.queue.data.name}")
    private String dataQueue;

    @Value("${rabbitmq.exchange.data.name}")
    private String dataExchange;

    @Value("${rabbitmq.routing.data.key}")
    private String dataRoutingKey;

    /**
     * Defines the security response queue.
     *
     * @return a new Queue instance
     */
    @Bean
    public Queue dataResponseQueue() {
        return new Queue(dataQueue);
    }

    /**
     * Defines the security exchange.
     *
     * @return a new TopicExchange instance
     */
    @Bean
    public TopicExchange dataExchange() {
        return new TopicExchange(dataExchange);
    }

    /**
     * Defines the binding between the security response queue and the security exchange.
     *
     * @return a new Binding instance
     */
    @Bean
    public Binding dataBinding() {
        return BindingBuilder
                .bind(dataResponseQueue())
                .to(dataExchange())
                .with(dataRoutingKey);
    }

    // ------  END DATA AUTH  ------ //

    /**
     * Creates a message converter for JSON messages.
     *
     * @return a new Jackson2JsonMessageConverter instance.
     */
    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Creates an AMQP template for sending messages.
     *
     * @param connectionFactory the connection factory to use.
     * @return a new RabbitTemplate instance.
     */
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
