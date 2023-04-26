package com.example.demo.room.repository;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "room")
@Data
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "city")
    private String city;
    @Column(name = "street")
    private String street;
    @Column(name = "streetNo")
    private int streetNo;
    @Column(name = "capacity")
    private int capacity;
    @Column(name = "price")
    private int price;
    @Column(name = "description")
    private String description;
}
