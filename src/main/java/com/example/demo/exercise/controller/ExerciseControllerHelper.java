package com.example.demo.exercise.controller;

import com.example.demo.exercise.repository.TypeEnum;
import com.example.demo.exercise.service.CreateExerciseDomain;
import com.example.demo.exercise.service.ExerciseDomain;
import lombok.NonNull;

public class ExerciseControllerHelper {
    static CreateExerciseDomain toCreateExerciseDomain(@NonNull final JsonUpsertExerciseDomainRequest request) {
        return CreateExerciseDomain.builder()
                .name(request.getName())
                .created_at(request.getCreated_at())
                .updated_at(request.getUpdated_at())
                .value(request.getValue())
                .type(TypeEnum.valueOf(request.getType()))
                .build();
    }

    static ExerciseDomain toExerciseDomain(@NonNull final JsonUpsertExerciseDomainRequest request, Long id) {
        return ExerciseDomain.builder()
                .id(id)
                .name(request.getName())
                .created_at(request.getCreated_at())
                .updated_at(request.getUpdated_at())
                .value(request.getValue())
                .type(TypeEnum.valueOf(request.getType()))
                .build();
    }
}
