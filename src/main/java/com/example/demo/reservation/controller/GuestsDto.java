package com.example.demo.reservation.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class GuestsDto {
    @JsonProperty("name")
    String name;
    @JsonProperty("email")
    String email;
}
