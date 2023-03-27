package com.example.demo.exercise.repository;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "exercie")
@Data
public class ExerciseEntity {
    // in total 6 coloane id, name, created_at(timestamp si in cod instant la fel si la updated_at), updated_at, value(big_decimal in cod),type( in cod enum si in db string)
//create, update,delete, list_by_value.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name = "updated_at")
    private Timestamp updated_at;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "type")
    private String type;
}
