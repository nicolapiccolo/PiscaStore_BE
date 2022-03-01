package it.unito.catalog_service.repository;

import it.unito.catalog_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository  extends JpaRepository<Product,Long> {

    List<Product> findByAuthorId(Long id);
    List<Product> findByAvailableTrue();
    List<Product> findByCategoryId(Long id);
    void deleteById(Long id);

}
