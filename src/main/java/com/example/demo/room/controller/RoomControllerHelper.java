package com.example.demo.room.controller;

import com.example.demo.room.service.CreateRoomDomainObjectRequest;
import com.example.demo.room.service.RoomDomainObject;
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
                .build();
    }
}