package it.unito.user_service.entity;

import it.unito.user_service.enums.RoleNumber;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleNumber name;

    public Role(){}

    public Role(RoleNumber name){
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoleNumber getName() {
        return name;
    }

    public void setName(RoleNumber name) {
        this.name = name;
    }

}
