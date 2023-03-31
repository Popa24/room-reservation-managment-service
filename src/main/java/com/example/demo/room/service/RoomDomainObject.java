package com.example.demo.room.service;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class RoomDomainObject {
    @NonNull
    Long id;
    @NonNull
    String name;
    @NonNull
    String city;
    @NonNull
    String street;
    @NonNull
    int streetNo;
    @NonNull
    int capacity;
    @NonNull
    int price;
    @NonNull
    String description;
}
