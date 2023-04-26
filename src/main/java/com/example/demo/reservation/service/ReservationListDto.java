package com.example.demo.reservation.service;

import com.example.demo.room.service.RoomInfoDto;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.sql.Timestamp;

@Value
@Builder
public class ReservationListDto {
    @NonNull
    Long id;
    @NonNull
    Timestamp startDate;
    @NonNull
    Timestamp endDate;
    @NonNull
    RoomInfoDto roomInfoDto;
    int generatedRevenue;

}
