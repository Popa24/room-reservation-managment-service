package com.example.demo.room.controller;

import com.example.demo.room.service.AllRoomInformationDto;
import com.example.demo.room.service.CreateRoomDomainObjectRequest;
import com.example.demo.room.service.RoomDomainObject;
import com.example.demo.room.service.TopRented;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoomControllerHelper {
    public @NonNull CreateRoomDomainObjectRequest toCreateRoomRequest(
            @NonNull final JsonUpsertRoomDomainRequest jsonRequest) {

        return CreateRoomDomainObjectRequest.builder()
                .name(jsonRequest.getName())
                .city(jsonRequest.getCity())
                .street(jsonRequest.getStreet())
                .streetNo(jsonRequest.getStreetNo())
                .capacity(jsonRequest.getCapacity())
                .price(jsonRequest.getPrice())
                .description(jsonRequest.getDescription())
                .build();
    }

    public static JsonTopRentedResponse toJson(@NonNull final TopRented topRented) {
        return JsonTopRentedResponse.builder()
                .roomId(topRented.getRoomId())
                .nrOfReservations(topRented.getNrOfReservations())
                .totalAmountOfRentedTime(topRented.getTotalAmountOfRentedTime())
                .generatedRevenue(topRented.getGeneratedRevenue())
                .build();
    }

    public @NonNull RoomDomainObject toRoomDomainObject(@NonNull final JsonUpsertRoomDomainRequest jsonUpsertRoomDomainRequest, Long id) {
        return RoomDomainObject.builder()
                .id(id)
                .name(jsonUpsertRoomDomainRequest.getName())
                .city(jsonUpsertRoomDomainRequest.getCity())
                .street(jsonUpsertRoomDomainRequest.getStreet())
                .streetNo(jsonUpsertRoomDomainRequest.getStreetNo())
                .capacity(jsonUpsertRoomDomainRequest.getCapacity())
                .price(jsonUpsertRoomDomainRequest.getPrice())
                .description(jsonUpsertRoomDomainRequest.getDescription())
                .build();
    }

    public static JsonAllRoomInformationDtoResponse toJson(@NonNull final AllRoomInformationDto allRoomInformationDto) {
        return JsonAllRoomInformationDtoResponse.builder()
                .name(allRoomInformationDto.getName())
                .generatedRevenue(allRoomInformationDto.getGeneratedRevenue())
                .timeframe(allRoomInformationDto.getTimeframe())
                .reservationInfoList(allRoomInformationDto.getReservationInfoList())
                .build();
    }

    @NonNull
    public static JsonRoomDomainResponse toJson(@NonNull final RoomDomainObject roomDomainObject) {
        return JsonRoomDomainResponse.builder()
                .id(roomDomainObject.getId())
                .name(roomDomainObject.getName())
                .city(roomDomainObject.getCity())
                .street(roomDomainObject.getStreet())
                .streetNo(roomDomainObject.getStreetNo())
                .capacity(roomDomainObject.getCapacity())
                .price(roomDomainObject.getPrice())
                .description(roomDomainObject.getDescription())
                .build();
    }
}