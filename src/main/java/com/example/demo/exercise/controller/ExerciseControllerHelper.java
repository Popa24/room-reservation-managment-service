package com.example.demo.exercise.controller;

import com.example.demo.exercise.service.TypeEnum;
import com.example.demo.exercise.service.CreateExerciseRequest;
import com.example.demo.exercise.service.ExerciseDomain;
import lombok.NonNull;

public class ExerciseControllerHelper {
    static CreateExerciseRequest toCreateExerciseDomain(@NonNull final JsonUpsertExerciseDomainRequest request) {
        return CreateExerciseRequest.builder()
                .name(request.getName())
                .created_at(request.getCreated_at())
                .updated_at(request.getUpdated_at())
                .value(request.getValue())
                .type(TypeEnum.valueOf(request.getType().name()))
                .build();
    }

    static ExerciseDomain toExerciseDomain(@NonNull final JsonUpsertExerciseDomainRequest request, Long id) {
        return ExerciseDomain.builder()
                .id(id)
                .name(request.getName())
                .created_at(request.getCreated_at())
                .updated_at(request.getUpdated_at())
                .value(request.getValue())
                .type(TypeEnum.valueOf(request.getType().toString()))
                .build();
    }
    @NonNull
    public static JsonExerciseDomainResponse toJson(@NonNull final ExerciseDomain exerciseDomain) {
        return JsonExerciseDomainResponse.builder()
                .id(exerciseDomain.getId())
                .name(exerciseDomain.getName())
                .created_at(exerciseDomain.getCreated_at())
                .updated_at(exerciseDomain.getUpdated_at())
                .value(exerciseDomain.getValue())
                .type(JsonTypeEnum.valueOf(exerciseDomain.getType().toString()))
                .build();
    }
}
