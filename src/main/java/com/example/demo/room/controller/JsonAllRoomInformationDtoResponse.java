package com.example.demo.room.controller;

import com.example.demo.room.service.AllRoomInformationDto;
import com.example.demo.reservation.service.ReservationInfoDto;
import com.example.demo.room.service.TimeFrameDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
@Value
@Builder
@Jacksonized
public class JsonAllRoomInformationDtoResponse {
    @JsonProperty("room_name")
    String name;
    @JsonProperty("generatedRevenue")
    int generatedRevenue;
    @JsonProperty("reservation timeframe")
    TimeFrameDto timeframe;
    @JsonProperty("reservationInfoList")
    List<ReservationInfoDto> reservationInfoList;

}
