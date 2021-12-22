package it.unito.catalog_service.service;

import it.unito.catalog_service.entity.Author;
import it.unito.catalog_service.messaging.User;
import it.unito.catalog_service.repository.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQReceiver implements RabbitListenerConfigurer {

    @Autowired
    private AuthorRepository authorRepository;

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQReceiver.class);

    @RabbitListener(queues = "${spring.rabbitmq.r_queue}")
    public void receivedMessage(User user) {

        logger.info("Received from ${spring.rabbitmq.r_queue}: " + user);

        Author author = new Author(user.getName(),user.getSurname(),user.getId());
        authorRepository.save(author);

    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {

    }
}
