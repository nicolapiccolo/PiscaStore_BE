package it.unito.order_service.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.unito.order_service.entity.Bag;
import it.unito.order_service.entity.Item;

import java.util.Date;
import java.util.Set;

public class OrderUser {


    @JsonProperty("id")
    private Long id;

    @JsonProperty("id_user")
    private Long idUser;

    @JsonProperty("id_address")
    private Long idAddress;

    private Date creation;

    private Set<Item> items;

    public OrderUser(){
    }

    public OrderUser(Long id, Long idUser, Long idAddress, Set<Item> items, Date creation) {
        this.id = id;
        this.idUser = idUser;
        this.items = items;
        this.idAddress = idAddress;
        this.creation = creation;
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

    public Long getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(Long idAddress) {
        this.idAddress = idAddress;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

