package com.example.demo.reservation.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class InviteStatusDto {
    @JsonProperty("email")
    String email;
    @JsonProperty("status")
    String status;
    @JsonProperty("errorMessage:")
    String errorMessage;
}
