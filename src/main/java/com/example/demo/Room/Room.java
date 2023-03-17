package com.example.demo.Room;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Table(name = "room")
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


}
