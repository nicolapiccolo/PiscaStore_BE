package it.unito.catalog_service.controller;

import it.unito.catalog_service.entity.Author;
import it.unito.catalog_service.entity.Product;
import it.unito.catalog_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public List<Product> find(){
        return productRepository.findAll();
    }

    @PostMapping("/products/create")
    public void create(@RequestBody Product product){
        productRepository.save(product);
    }


}
