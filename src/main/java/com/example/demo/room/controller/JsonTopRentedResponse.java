package com.example.demo.room.controller;

import com.example.demo.room.service.TopRented;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonTopRentedResponse {
    @JsonProperty("roomId")
    Long roomId;
    @JsonProperty("number of reservations")
    int nrOfReservations;
    @JsonProperty("total amount of rented time")
    int totalAmountOfRentedTime;
    @JsonProperty("generated revenue")
    int generatedRevenue;

    public static JsonTopRentedResponse toJson(@NonNull final TopRented topRented){
        return JsonTopRentedResponse.builder()
                .roomId(topRented.getRoomId())
                .nrOfReservations(topRented.getNrOfReservations())
                .totalAmountOfRentedTime(topRented.getTotalAmountOfRentedTime())
                .generatedRevenue(topRented.getGeneratedRevenue())
                .build();
    }
}
