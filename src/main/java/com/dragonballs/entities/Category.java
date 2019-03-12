package com.dragonballs.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "deed_category")
public class Category {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;


    @OneToMany(mappedBy = "id")
    private List<Deed> deed;

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

    public List<Deed> getDeed() {
        return deed;
    }

    public void setDeed(List<Deed> deed) {
        this.deed = deed;
    }
}
