package it.unito.catalog_service.messaging;

import it.unito.catalog_service.entity.Product;

public class ProductAuthor {

    private Product product;
    private Long id_author;
    private String category;

    public ProductAuthor(Product product, Long id_author, String category) {
        this.product = product;
        this.id_author = id_author;
        this.category = category;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getId_author() {
        return id_author;
    }

    public void setId_author(Long id_author) {
        this.id_author = id_author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
