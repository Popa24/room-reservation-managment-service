package com.example.demo.room.service;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.sql.Timestamp;

@Value
@Builder
public class TimeFrameDto {
    @NonNull
    Timestamp startDate;
    @NonNull
    Timestamp endDate;
}
