package it.unito.order_service.controller;

import it.unito.order_service.entity.Bag;
import it.unito.order_service.entity.Item;
import it.unito.order_service.messaging.OrderUser;
import it.unito.order_service.messaging.ResponseMessage;
import it.unito.order_service.repository.OrderItemRepository;
import it.unito.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("api/v1/bag")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository itemRepository;


    @GetMapping("/{id}")
    public Optional<Bag> findById(@PathVariable Long id){
        return orderRepository.findById(id);
    }


   /* @PostMapping("/create")
    public ResponseEntity<ResponseMessage> create(@RequestBody OrderUser ord){
        Bag bag = ord.getBag();
        bag.setPrice(ord.getTotalPrice());

        bag.setIdUser(ord.getId_user());

        Bag o = orderRepository.save(bag);
        return new ResponseEntity(new ResponseMessage(String.valueOf(o.getId())),HttpStatus.OK);
    }*/



    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> create(@RequestBody OrderUser order){
        float totalPrice = 0f;


        //order.getIdUser();

        Bag bag = orderRepository.save(new Bag(order.getIdUser()));


        Set<Item> items = order.getItems();

        for(Item i : items){
            i.setBag(bag);
            totalPrice += i.getPrice();
            itemRepository.save(i);
        }

        System.out.println("total: " + totalPrice);

        bag.setItems(items);
        bag.setPrice(totalPrice);

        return new ResponseEntity(new ResponseMessage(String.valueOf(bag.getId())),HttpStatus.OK);
    }



}
