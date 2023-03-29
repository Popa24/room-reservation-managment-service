package com.example.demo.room.controller;

import com.example.demo.room.service.RoomDomainObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonRoomDomainResponse {
    @JsonProperty("id")
    Long id;
    @JsonProperty("name")
    String name;
    @JsonProperty("city")
    String city;
    @JsonProperty("street")
    String street;
    @JsonProperty("streetNo")
    int streetNo;
    @JsonProperty("capacity")
    int capacity;
    @JsonProperty("price")
    int price;

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
                .build();
    }
}
