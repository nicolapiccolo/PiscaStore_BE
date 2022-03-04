package it.unito.order_service.controller;


import it.unito.order_service.entity.Item;
import it.unito.order_service.messaging.ResponseMessage;
import it.unito.order_service.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class OrderItemController {

    @Autowired
    private OrderItemRepository itemRepository;

    @GetMapping("/items")
    public List<Item> find(){
        return itemRepository.findAll();
    }

    @PostMapping("/items/create")
    public ResponseEntity<ResponseMessage> create(@RequestBody Item item){
        Item i = itemRepository.save(item);
        return new ResponseEntity(new ResponseMessage(String.valueOf(i.getId())), HttpStatus.OK);

    }

    @GetMapping("/items/{id}")
    public Optional<Item> findById(@PathVariable Long id){
        return itemRepository.findById(id);
    }
}
