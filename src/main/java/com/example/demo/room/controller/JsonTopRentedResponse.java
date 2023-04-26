package com.example.demo.room.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
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
    @JsonProperty("generatedRevenue")
    int generatedRevenue;


}
