package com.example.demo.Reservation;

import com.example.demo.Room.Room;
import com.example.demo.User.userDomain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@Table(name = "reservation")
public class Reservation {
    private @Id @GeneratedValue Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @PrimaryKeyJoinColumn
    private userDomain userId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @PrimaryKeyJoinColumn
    private Room roomId;

    private Date startDate;
    private Date endDate;

    public Reservation() {

}}
