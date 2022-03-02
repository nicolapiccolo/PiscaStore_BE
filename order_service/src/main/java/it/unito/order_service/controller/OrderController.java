package it.unito.order_service.controller;

import it.unito.order_service.entity.Bag;
import it.unito.order_service.messaging.OrderUser;
import it.unito.order_service.messaging.ResponseMessage;
import it.unito.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bag")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;


    /*@GetMapping("/{id}")
    public List<Bag> findByUser(@PathVariable Long id_user){
        System.out.println("ID: "+id_user);
        return orderRepository.findByIdUser(id_user);
    }*/

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> create(@RequestBody OrderUser ord){
        Bag bag = ord.getBag();
        bag.setPrice(ord.getTotalPrice());

        bag.setIdUser(ord.getId_user());

        Bag o = orderRepository.save(bag);
        return new ResponseEntity(new ResponseMessage(String.valueOf(o.getId())),HttpStatus.OK);
    }

}
