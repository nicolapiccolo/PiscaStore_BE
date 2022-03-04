package it.unito.order_service.service;

import it.unito.order_service.messaging.ProductItem;
import it.unito.order_service.repository.OrderItemRepository;
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
    private OrderItemRepository itemRepository;

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQReceiver.class);

    @RabbitListener(queues = "${spring.rabbitmq.order_queue}")
    public void receivedFromCatalog(ProductItem item) {

        logger.info("Received from ${spring.rabbitmq.order_queue}: " + item);

        //Author author = new Author(user.getName(),user.getSurname(),user.getId());
        //authorRepository.save(author);
    }



    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {

    }
}

