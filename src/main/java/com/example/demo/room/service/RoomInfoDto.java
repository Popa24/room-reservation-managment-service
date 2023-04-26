package com.example.demo.room.service;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class RoomInfoDto {
    @NonNull
    Long roomId;
    @NonNull
    String roomName;
}
