package com.example.demo.Room;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rooms")
public class Room {
        private @Id @GeneratedValue Long id;
        private String name;
        private String city;
        private String street;
        private int streetNo;
        private  int capacity;
        private int price;

    public Room() {
    }

    public Room(String name, String city, String street, int streetNo, int capacity, int price) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.streetNo = streetNo;
        this.capacity = capacity;
        this.price = price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreetNo(int streetNo) {
        this.streetNo = streetNo;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getStreetNo() {
        return streetNo;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getPrice() {
        return price;
    }

    public Room(Long id, String name, String city, String street, int streetNo, int capacity, int price) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.street = street;
        this.streetNo = streetNo;
        this.capacity = capacity;
        this.price = price;
    }
}
