package com.example.demo.reservation.service;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class AggregateRoomReservationInfo {
    @NonNull
    Long roomId;
    int nrOfReservations;
    int totalAmountOfRentedTime;
}
