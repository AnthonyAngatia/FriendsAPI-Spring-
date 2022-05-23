package com.wiredbrain.friends.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/*
* When Address is Embeddable, it doesn't need to have an id
* street and city are added as columns to the friends table.
* However, when we use @Entity annotation then a new table is created and we should specify an ID*/
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String street;
    private String city;

    @JsonBackReference
    @ManyToOne
    private Friend friend;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
