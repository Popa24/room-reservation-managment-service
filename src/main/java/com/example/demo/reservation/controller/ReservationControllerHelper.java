package com.example.demo.reservation.controller;

import com.example.demo.roomreservation.roomreservationservice.AllUserInfoDto;
import com.example.demo.reservation.service.CreateReservationDomainObjectRequest;
import com.example.demo.reservation.service.ReservationDomainObject;
import com.example.demo.roomreservation.roomreservationservice.Guest;
import com.example.demo.roomreservation.roomreservationservice.InviteStatus;
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

    @NonNull
    public Guest toGuest(@NonNull final GuestsDto guestsDto) {
        return Guest.builder()
                .name(guestsDto.getName())
                .email(guestsDto.getEmail())
                .build();

    }

    @NonNull
    public InviteStatusDto toJson(@NonNull final InviteStatus inviteStatus) {
        return InviteStatusDto.builder()
                .email(inviteStatus.getEmail())
                .status(inviteStatus.getStatus())
                .errorMessage(inviteStatus.getErrorMessage())
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

    @NonNull
    public static JsonReservationDomainResponse toJson(@NonNull final ReservationDomainObject reservationDomainObject) {
        return JsonReservationDomainResponse.builder()
                .id(reservationDomainObject.getId())
                .userId(Long.valueOf(reservationDomainObject.getUserId()))
                .roomId(reservationDomainObject.getRoomId())
                .startDate(reservationDomainObject.getStartDate())
                .endDate(reservationDomainObject.getEndDate())
                .build();
    }

    @NonNull
    public static JsonAllUserInfoDto toJson(@NonNull final AllUserInfoDto allUserInfoDto) {
        return JsonAllUserInfoDto.builder()
                .reservationListDtos(allUserInfoDto.getReservationListDtos())
                .timeFrameDto(allUserInfoDto.getTimeFrameDto())
                .totalSpent(allUserInfoDto.getTotalSpent())
                .user_email(allUserInfoDto.getUser_email())
                .user_id(allUserInfoDto.getUser_id())
                .build();
    }

}

