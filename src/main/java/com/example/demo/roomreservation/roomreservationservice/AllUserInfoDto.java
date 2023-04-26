package com.example.demo.roomreservation.roomreservationservice;

import com.example.demo.reservation.service.ReservationListDto;
import com.example.demo.room.service.TimeFrameDto;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class AllUserInfoDto {
    @NonNull
    Long user_id;
    @NonNull
    String user_email;
    int totalSpent;
    @NonNull
    TimeFrameDto timeFrameDto;
    @NonNull
    List<ReservationListDto> reservationListDtos;
}
