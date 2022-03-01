package it.unito.catalog_service.controller;

import it.unito.catalog_service.entity.Author;
import it.unito.catalog_service.entity.Category;
import it.unito.catalog_service.entity.Product;
import it.unito.catalog_service.messaging.ProductAuthor;
import it.unito.catalog_service.messaging.ResponseMessage;
import it.unito.catalog_service.repository.AuthorRepository;
import it.unito.catalog_service.repository.CategoryRepository;
import it.unito.catalog_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Object user = SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        System.out.println(user.toString());

        return productRepository.findByAvailableTrue();
    }


    @GetMapping("/catalog/category/{id}")
    public List<Product> findByCategory(@PathVariable Long id){
        System.out.println("ID: "+ id);
        return productRepository.findByCategoryId(id);
    }

    @GetMapping("/products")
    public List<Product> findByAuthor(@RequestParam(value="id") Long id){
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

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ResponseMessage> delete(@PathVariable Long id){
        if(productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return new ResponseEntity(new ResponseMessage("Deleted"),HttpStatus.OK);
        }
        else return new ResponseEntity(new ResponseMessage("Product not exists"),HttpStatus.NOT_FOUND);
    }

    @PostMapping("/products/create")
    public ResponseEntity<ResponseMessage> create(@RequestBody ProductAuthor prod){

        Product product = prod.getProduct();
        String category_name = prod.getCategory();


        Optional<Author> aut_opt = authorRepository.findByIdUser(prod.getId_author());

        if(aut_opt.isPresent()) {
            Author aut = aut_opt.get();

            Optional c_opt = categoryRepository.findByName(category_name);


            if(c_opt.isPresent()) product.setCategory((Category) c_opt.get());

            product.setAuthor(aut);

            Product p = productRepository.save(product);
            return new ResponseEntity(new ResponseMessage(String.valueOf(p.getId())),HttpStatus.OK);
        }
        else return new ResponseEntity(new ResponseMessage("The author not exists "),HttpStatus.NOT_FOUND);
    }



}
