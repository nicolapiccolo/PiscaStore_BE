package it.unito.catalog_service.controller;

import it.unito.catalog_service.entity.Author;
import it.unito.catalog_service.messaging.User;
import it.unito.catalog_service.repository.AuthorRepository;
import it.unito.catalog_service.service.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    private RabbitMQSender rabbitMqSender;

    private String message = "Message sent successfully";

    @GetMapping("/authors")
    public List<Author> find(){
        return authorRepository.findAll();
    }

    @PostMapping("/authors/create")
    public void create(@RequestBody Author author){
        authorRepository.save(author);
    }




    @Autowired
    public AuthorController(RabbitMQSender rabbitMqSender) {
        this.rabbitMqSender = rabbitMqSender;
    }

    @PostMapping("/user")
    public String publishUserDetails(@RequestBody User user) {
        rabbitMqSender.send(user);
        return message;
    }

}
