package com.example.demo.reservation.service;

import com.example.demo.user.service.UserInfoDto;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.sql.Timestamp;

@Value
@Builder
public class ReservationInfoDto {
    @NonNull
    Long reservationId;
    @NonNull
    UserInfoDto userInfo;
    @NonNull
    Timestamp stardDate;
    @NonNull
    Timestamp enddDate;
}
