package it.unito.order_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Locale;
import java.util.Set;

@Entity
public class Bag implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float price;

    private Long idUser;

    @JsonIgnore
    @OneToMany(mappedBy="bag",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<OrderItem> item;



    public Bag(float price, Set<OrderItem> item, Long idUser) {
        this.price = price;
        this.item = item;
        this.idUser = idUser;
    }

    public Bag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Set<OrderItem> getItem() {
        return item;
    }

    public void setItem(Set<OrderItem> item) {
        this.item = item;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
    /*@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }*/



}
