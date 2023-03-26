package com.example.demo.exercise.controller;

import com.example.demo.exercise.service.ExerciseDomain;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
@Jacksonized
public class JsonExerciseDomainResponse {
    @JsonProperty("id")
    Long id;

    @JsonProperty("name")
    String name;

    @JsonProperty("created_at")
    Instant created_at;
    @JsonProperty("created_at")
    Instant updated_at;
    @JsonProperty("value")
    BigDecimal value;

    @JsonProperty("type")
    String type;

    @NonNull
    public static JsonExerciseDomainResponse toJson(@NonNull final ExerciseDomain exerciseDomain) {
        return JsonExerciseDomainResponse.builder()
                .id(exerciseDomain.getId())
                .name(exerciseDomain.getName())
                .created_at(exerciseDomain.getCreated_at())
                .updated_at(exerciseDomain.getUpdated_at())
                .value(exerciseDomain.getValue())
                .type(exerciseDomain.getType().toString())
                .build();
    }
}