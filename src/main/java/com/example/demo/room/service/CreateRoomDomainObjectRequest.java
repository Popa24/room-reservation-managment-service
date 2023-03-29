package com.example.demo.room.service;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CreateRoomDomainObjectRequest {
    @NonNull
    String name;
    @NonNull
    String city;
    @NonNull
    String street;
    int streetNo;
    int capacity;
    int price;
}