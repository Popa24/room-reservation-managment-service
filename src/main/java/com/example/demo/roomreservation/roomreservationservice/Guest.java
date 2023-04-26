package com.example.demo.roomreservation.roomreservationservice;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Guest {
    @NonNull
    String name;
    @NonNull
    String email;
}
