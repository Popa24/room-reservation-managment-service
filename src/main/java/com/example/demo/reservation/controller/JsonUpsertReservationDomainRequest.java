package com.example.demo.reservation.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.sql.Timestamp;

@Value
@Builder
@Jacksonized
public class JsonUpsertReservationDomainRequest {
    @JsonProperty("userId")
    Long userId;
    @JsonProperty("roomId")
    Long roomId;
    @JsonProperty("startDate")
    Timestamp startDate;
    @JsonProperty("endDate")
    Timestamp endDate;
}
