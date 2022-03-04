package it.unito.order_service.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.unito.order_service.entity.Bag;
import it.unito.order_service.entity.Item;

import java.util.Set;

public class OrderUser {


    @JsonProperty("id_user")
    private Long idUser;

    private Set<Item> items;

    public OrderUser(Long idUser, Set<Item> items) {
        this.idUser = idUser;
        this.items = items;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}

