package it.unito.order_service.messaging;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.stereotype.Component;

import java.io.Serializable;

public class ProductItem implements Serializable {

    @JsonProperty("id_product")
    private Long idProduct;

    private String name;
    private float price;

    public ProductItem(Long idProduct, String name, float price) {
        this.idProduct = idProduct;
        this.name = name;
        this.price = price;
    }

    public ProductItem() {
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}

