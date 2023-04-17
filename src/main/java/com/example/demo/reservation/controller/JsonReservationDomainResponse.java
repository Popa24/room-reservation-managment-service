package com.example.demo.reservation.controller;

import com.example.demo.reservation.service.ReservationDomainObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.sql.Timestamp;

@Value
@Builder
@Jacksonized
public class JsonReservationDomainResponse {
    @JsonProperty("id")
    Long id;
    @JsonProperty("userId")
    Long userId;
    @JsonProperty("roomId")
    Long roomId;
    @JsonProperty("startDate")
    Timestamp startDate;
    @JsonProperty("endDate")
    Timestamp endDate;


}
