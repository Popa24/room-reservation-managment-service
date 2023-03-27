package com.example.demo.exercise.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
@Jacksonized
public class JsonUpsertExerciseDomainRequest {
    @JsonProperty("name")
    String name;

    @JsonProperty("created_at")
    Instant created_at;

    @JsonProperty("updated_at")
    Instant updated_at;

    @JsonProperty("value")
    BigDecimal value;

    @JsonProperty("type")
    JsonTypeEnum type;
}
