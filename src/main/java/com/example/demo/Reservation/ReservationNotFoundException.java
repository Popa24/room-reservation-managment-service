package com.example.demo.Reservation;

public class ReservationNotFoundException extends RuntimeException{
    public ReservationNotFoundException(Long id) {
        super("Could not find room "+id);
    }
}
