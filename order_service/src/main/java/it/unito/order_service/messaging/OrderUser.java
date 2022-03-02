package it.unito.order_service.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.unito.order_service.entity.Bag;

public class OrderUser {
    private Bag bag;

    @JsonProperty("id_user")
    private Long id_user;

    private float totalPrice;

    public OrderUser(){}

    public OrderUser(Bag bag, Long id_user){
        this.bag = bag;
        this.id_user = id_user;
    }

    public Bag getBag() {
        return bag;
    }

    public Long getId_user() {
        return id_user;
    }

    public float getTotalPrice(){
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice){
        this.totalPrice = totalPrice;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }
}
