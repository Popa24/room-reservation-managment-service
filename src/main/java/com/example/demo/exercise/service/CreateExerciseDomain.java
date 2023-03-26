package com.example.demo.exercise.service;

import com.example.demo.exercise.repository.TypeEnum;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
public class CreateExerciseDomain {
    @NonNull
    String name;
    @NonNull
    Instant created_at;
    @NonNull
    Instant updated_at;
    @NonNull
   BigDecimal value;
   @NonNull
    TypeEnum type;


}
