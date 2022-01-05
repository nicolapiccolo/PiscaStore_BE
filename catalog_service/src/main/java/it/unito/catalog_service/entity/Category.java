package it.unito.catalog_service.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        })
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @JsonIgnore
    @OneToMany(mappedBy="category",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Product> products;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Category(){}

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString(){
        return String.format("Category[id=%d, name='%s', description='%s']",
                id, name, description);
    }

}
