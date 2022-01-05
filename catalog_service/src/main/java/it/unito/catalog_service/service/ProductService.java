package it.unito.catalog_service.service;

import it.unito.catalog_service.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProductByAuthor(Long idAuthor);
}
