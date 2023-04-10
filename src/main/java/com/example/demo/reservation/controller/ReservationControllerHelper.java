package com.example.demo.reservation.controller;

import com.example.demo.reservation.service.CreateReservationDomainObjectRequest;
import com.example.demo.reservation.service.ReservationDomainObject;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ReservationControllerHelper {
    public @NonNull CreateReservationDomainObjectRequest toCreateReservationRequest(
            @NonNull final JsonUpsertReservationDomainRequest jsonRequest) {

        return CreateReservationDomainObjectRequest.builder()
                .userId(jsonRequest.getUserId())
                .roomId(jsonRequest.getRoomId())
                .startDate(jsonRequest.getStartDate())
                .endDate(jsonRequest.getEndDate())
                .build();
    }

    @NonNull
    public static JsonReservationDomainResponse toJsonReservationDomainResponse(@NonNull final ReservationDomainObject reservationDomainObject) {
        return JsonReservationDomainResponse.builder()
                .id(reservationDomainObject.getId())
                .userId(Long.valueOf(reservationDomainObject.getUserId()))
                .roomId(reservationDomainObject.getRoomId())
                .startDate(reservationDomainObject.getStartDate())
                .endDate(reservationDomainObject.getEndDate())
                .build();
    }
    public @NonNull ReservationDomainObject toReservationDomainObject(@NonNull final JsonUpsertReservationDomainRequest jsonRequest, Long id) {
        return ReservationDomainObject.builder()
                .userId(Math.toIntExact(id))
                .roomId(jsonRequest.getRoomId())
                .startDate(jsonRequest.getStartDate())
                .endDate(jsonRequest.getEndDate())
                .build();
    }
}
