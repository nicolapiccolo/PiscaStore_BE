package it.unito.catalog_service.controller;

import it.unito.catalog_service.entity.Author;
import it.unito.catalog_service.entity.Category;
import it.unito.catalog_service.entity.Product;
import it.unito.catalog_service.messaging.ProductAuthor;
import it.unito.catalog_service.repository.AuthorRepository;
import it.unito.catalog_service.repository.CategoryRepository;
import it.unito.catalog_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/catalog")
    public List<Product> find(){
        return productRepository.findByAvailableTrue();
    }


    @GetMapping("/catalog/category/{id}")
    public List<Product> findByCategory(@PathVariable Long id){
        System.out.println("ID: "+ id);
        return productRepository.findByCategoryId(id);
    }

    @GetMapping("/products")
    public List<Product> findByAuthor(@RequestBody String id_author){
        Long id = Long.parseLong(id_author);
        System.out.println("ID: "+ id);
        return productRepository.findByAuthorId(id);
    }

    @GetMapping("/products/{id}")
    public ProductAuthor findById(@PathVariable Long id){

        Optional<Product> p_opt =  productRepository.findById(id);

        if(p_opt.isPresent()){
            Product p = p_opt.get();
            Author a = p.getAuthor();
            Category c = p.getCategory();

            ProductAuthor details = new ProductAuthor(p,a.getId(),c.getName());
            return details;
        }
        else return null;

    }

    @PostMapping("/products/create")
    public ResponseEntity<?> create(@RequestBody ProductAuthor prod){

        Product product = prod.getProduct();
        String category_name = prod.getCategory();


        Optional<Author> aut_opt = authorRepository.findByIdUser(prod.getId_author());

        if(aut_opt.isPresent()) {
            Author aut = aut_opt.get();

            Optional c_opt = categoryRepository.findByName(category_name);

            if(c_opt.isPresent()) product.setCategory((Category) c_opt.get());

            product.setAuthor(aut);

            Product p = productRepository.save(product);
            return new ResponseEntity("Product saved ",HttpStatus.OK);
        }
        else return new ResponseEntity("The author not exists ",HttpStatus.NOT_FOUND);

    }




}
