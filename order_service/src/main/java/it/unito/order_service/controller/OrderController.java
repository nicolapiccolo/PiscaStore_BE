package it.unito.order_service.controller;

import it.unito.order_service.entity.Order;
import it.unito.order_service.entity.OrderItem;
import it.unito.order_service.entity.User;
import it.unito.order_service.messaging.OrderUser;
import it.unito.order_service.messaging.ResponseMessage;
import it.unito.order_service.repository.OrderRepository;
import it.unito.order_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bag")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public List<Order> findByUser(@RequestBody String id_user){
        Long id = Long.parseLong(id_user);
        System.out.println("ID: "+id);
        return orderRepository.findOrderById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> create(@RequestBody OrderUser ord){
        Order order = ord.getOrder();
        order.setPrice(ord.getTotalPrice());
        User user = userRepository.findByIdUser(ord.getId_user());
        order.setUser(user);
        Order o = orderRepository.save(order);
        return new ResponseEntity(new ResponseMessage(String.valueOf(o.getId())),HttpStatus.OK);
    }

}
