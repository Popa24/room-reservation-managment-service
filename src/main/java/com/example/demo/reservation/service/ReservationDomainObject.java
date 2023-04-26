package com.example.demo.reservation.service;

import com.example.demo.room.service.TimeFrameDto;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Value
@Builder
public class ReservationDomainObject {
    @NonNull
    Long id;
    @NonNull
    Integer userId;
    @NonNull
    Long roomId;
    @NonNull
    Timestamp startDate;
    @NonNull
    Timestamp endDate;
    //add method getDuration
    public static int getDuration(ReservationDomainObject reservation) {
        return (int) Duration.between(
                reservation.getStartDate().toLocalDateTime(),
                reservation.getEndDate().toLocalDateTime())
                .toHours();
    }
    public static TimeFrameDto buildTimeFrameDto(List<ReservationDomainObject> reservations) {
        Optional<Timestamp> firstStartDate = reservations.stream()
                .map(ReservationDomainObject::getStartDate)
                .min(Comparator.comparing(Timestamp::getTime));

        Optional<Timestamp> lastEndDate = reservations.stream()
                .map(ReservationDomainObject::getEndDate)
                .max(Comparator.comparing(Timestamp::getTime));

        firstStartDate.orElseThrow(() -> new RuntimeException("first start date missing"));
        return TimeFrameDto.builder()
                .startDate(firstStartDate.orElse(null))
                .endDate(lastEndDate.orElse(null))
                .build();
    }
}
