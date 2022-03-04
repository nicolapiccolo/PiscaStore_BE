package it.unito.order_service.service;

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

    @Value("${spring.rabbitmq.catalog_exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.catalog_routingkey}")
    private String routingkey;

    //public void send(User user){  rabbitTemplate.convertAndSend(exchange,routingkey, user);  }
}