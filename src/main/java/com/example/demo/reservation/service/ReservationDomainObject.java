package com.example.demo.reservation.service;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.sql.Timestamp;

@Value
@Builder
public class ReservationDomainObject {
    @NonNull
    Long id;
    @NonNull
    Long userId;
    @NonNull
    Long roomId;
    @NonNull
    Timestamp startDate;
    @NonNull
    Timestamp endDate;
}
