package it.unito.catalog_service.controller;

import it.unito.catalog_service.entity.Category;
import it.unito.catalog_service.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public List<Category> find(){
        return categoryRepository.findAll();
    }

    @PostMapping("/categories/create")
    public void create(@RequestBody Category category){
        categoryRepository.save(category);
    }

    @GetMapping("/category")
    public Optional<Category> findById(@RequestParam(value="id") Long id){
        return categoryRepository.findById(id);
    }

    @GetMapping("/categories/{name}")
    public Optional<Category> findByName(@PathVariable String name){
        return categoryRepository.findByName(name);
    }

}
