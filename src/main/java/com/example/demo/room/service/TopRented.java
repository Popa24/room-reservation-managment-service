package com.example.demo.room.service;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class TopRented {
    @NonNull
    Long roomId;
    int nrOfReservations;
    int totalAmountOfRentedTime;
    int generatedRevenue;
}
