package it.unito.order_service.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.unito.order_service.entity.Order;

public class OrderUser {
    private Order order;

    @JsonProperty("id_user")
    private Long id_user;

    private float totalPrice;

    public OrderUser(){}

    public OrderUser(Order order, Long id_user){
        this.order = order;
        this.id_user = id_user;
    }

    public Order getOrder() {
        return order;
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

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }
}
