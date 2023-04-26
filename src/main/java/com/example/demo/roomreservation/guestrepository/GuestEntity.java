package com.example.demo.roomreservation.guestrepository;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "guest")
public class GuestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name = "reservationId")
    Long reservationId;
    @Column(name = "name")
    String name;
    @Column(name = "email")
    String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    GuestStatus status = GuestStatus.PENDING;
}
