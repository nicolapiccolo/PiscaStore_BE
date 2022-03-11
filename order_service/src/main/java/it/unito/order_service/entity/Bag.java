package it.unito.order_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

@Entity
public class Bag implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float price;

    private Long idUser;
    private Long idAddress;

    @JsonIgnore
    @OneToMany(mappedBy="bag",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Item> items;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date creation;

    public Bag() {
    }

    public Bag(Long idUser, Long idAddress) {
        this.idUser = idUser;
        this.idAddress = idAddress;
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
}
