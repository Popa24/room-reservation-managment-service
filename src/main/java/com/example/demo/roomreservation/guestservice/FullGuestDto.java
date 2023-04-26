package com.example.demo.roomreservation.guestservice;

import com.example.demo.roomreservation.guestrepository.GuestStatus;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class FullGuestDto {
    @NonNull
    Long reservationId;
    @NonNull
    String name;
    @NonNull
    String email;
    @NonNull
    GuestStatus status;


}
