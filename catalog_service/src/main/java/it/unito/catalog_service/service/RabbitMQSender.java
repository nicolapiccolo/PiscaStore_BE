package it.unito.catalog_service.service;

import it.unito.catalog_service.entity.Author;
import it.unito.catalog_service.messaging.ProductItem;
import it.unito.catalog_service.messaging.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${spring.rabbitmq.s_exchange}")
    private String exchange_user;

    @Value("${spring.rabbitmq.s_routingkey}")
    private String routingkey_user;

    @Value("${spring.rabbitmq.order_exchange}")
    private String exchange_order;

    @Value("${spring.rabbitmq.order_routingkey}")
    private String routingkey_order;


    public void sendUser(User user){
        rabbitTemplate.convertAndSend(exchange_user,routingkey_user, user);
    }

    public void sendProductToOrder(ProductItem product){ rabbitTemplate.convertAndSend(exchange_order,routingkey_order, product); }

}