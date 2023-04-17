package com.example.demo.room.service;

import com.example.demo.reservation.service.ReservationInfoDto;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Builder

public class AllRoomInformationDto {
    @NonNull
    String name;
    @NonNull
    int generatedRevenue;
    @NonNull
    TimeFrameDto timeframe;
    @NonNull
    List<ReservationInfoDto> reservationInfoList;
}
