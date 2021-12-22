package it.unito.user_service.service;

import it.unito.user_service.messaging.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class RabbitMQReceiver implements RabbitListenerConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQReceiver.class);

    @RabbitListener(queues = "${spring.rabbitmq.r_queue}")
    public void receivedMessage(User user) {

        logger.info("Received from ${spring.rabbitmq.r_queue}: " + user);
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {

    }
}
