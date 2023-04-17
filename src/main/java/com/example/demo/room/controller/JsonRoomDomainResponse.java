package com.example.demo.room.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
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
    @JsonProperty("description")
    String description;


}
