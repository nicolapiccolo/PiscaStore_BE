package it.unito.user_service.controller;

import it.unito.user_service.entity.Category;
import it.unito.user_service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) { this.categoryService = categoryService; }

    @GetMapping
    public List<Category> getAll() {
        return categoryService.listCategories();
    }

    @GetMapping("/{id}")
    public Category getById(@PathVariable("id") long id){
        return categoryService.findCategory(id);
    }

    @PostMapping
    public void create(@RequestBody Category category){
        categoryService.createCategory(category);
    }
}
