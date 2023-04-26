package com.example.demo.room.controller;

import com.example.demo.reservation.service.ReservationInfoDto;
import com.example.demo.room.service.TimeFrameDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonAllRoomInformationDtoResponse {
    @JsonProperty("roomName")
    String name;
    @JsonProperty("generatedRevenue")
    int generatedRevenue;
    @JsonProperty("reservationTimeframe")
    TimeFrameDto timeframe;
    @JsonProperty("reservationInfoList")
    List<ReservationInfoDto> reservationInfoList;

}
