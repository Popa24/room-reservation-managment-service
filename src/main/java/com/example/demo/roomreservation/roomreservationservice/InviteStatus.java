package com.example.demo.roomreservation.roomreservationservice;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class InviteStatus {
    @NonNull
    String email;
    @NonNull
    String status;
    @NonNull
    String errorMessage;
}
