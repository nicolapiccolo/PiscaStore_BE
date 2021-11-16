package it.unito.pisca.entity;


import javax.persistence.*;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="category_id")
    private Long id;

    private String name;

    private String description;


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
