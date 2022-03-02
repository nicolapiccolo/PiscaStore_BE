package it.unito.order_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class OrderItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idProduct;

    private String name;
    private float price;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name= "order_id", nullable = false)
    private Bag bag;

    public OrderItem(){

    }


    public OrderItem(Long idProduct, String name, float price, Bag bag) {
        this.idProduct = idProduct;
        this.name = name;
        this.price = price;
        this.bag = bag;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }


    public void setName(String name) {
        this.name = name;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
