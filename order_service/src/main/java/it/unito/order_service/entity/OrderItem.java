package it.unito.order_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class OrderItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long product_id;

    private String name;

    private float price;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name= "order_id", nullable = false)
    private Order order;

    public Long getProduct_id() {
        return product_id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
