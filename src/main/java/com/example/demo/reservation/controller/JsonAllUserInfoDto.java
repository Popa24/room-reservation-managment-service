package com.example.demo.reservation.controller;

import com.example.demo.reservation.service.ReservationListDto;
import com.example.demo.room.service.TimeFrameDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonAllUserInfoDto {
    @JsonProperty("userId")
    Long user_id;
    @JsonProperty("userEmail")
    String user_email;
    @JsonProperty("totalSpent")
    int totalSpent;
    @JsonProperty("timeframe")
    TimeFrameDto timeFrameDto;
    @JsonProperty("reservationList")
    List<ReservationListDto> reservationListDtos;
}
