package com.example.demo.exercise.controller;

import com.example.demo.exercise.service.CreateExerciseDomain;
import com.example.demo.exercise.service.ExerciseDomain;
import com.example.demo.exercise.service.ExerciseService;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController

public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(@NonNull final ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping("exercise")
    public ResponseEntity<JsonExerciseDomainResponse> create(@RequestBody @NonNull final JsonUpsertExerciseDomainRequest request) {
        final CreateExerciseDomain createExerciseDomain = ExerciseControllerHelper.toCreateExerciseDomain(request);
        final ExerciseDomain exerciseDomain = exerciseService.create(createExerciseDomain);
        return ResponseEntity.ok().body(JsonExerciseDomainResponse.toJson(exerciseDomain));
    }

    @PutMapping("exercise/{id}")
    public ResponseEntity<JsonExerciseDomainResponse> update(@RequestBody @NonNull final JsonUpsertExerciseDomainRequest request,
                                                             @PathVariable Long id) {
        final ExerciseDomain exerciseDomain = ExerciseControllerHelper.toExerciseDomain(request, id);
        final ExerciseDomain updatedExerciseDomain = exerciseService.update(exerciseDomain);
        return ResponseEntity.ok().body(JsonExerciseDomainResponse.toJson(updatedExerciseDomain));
    }

    @DeleteMapping("exercise/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        exerciseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(params = "value")
    public ResponseEntity<List<JsonExerciseDomainResponse>> listByValue(@RequestParam BigDecimal value) {
        final List<ExerciseDomain> exerciseDomains = exerciseService.listByValue(value);
        final List<JsonExerciseDomainResponse> jsonExerciseDomainResponses =
                exerciseDomains.stream()
                        .map(JsonExerciseDomainResponse::toJson)
                        .collect(Collectors.toList());
        return ResponseEntity.ok().body(jsonExerciseDomainResponses);
    }


}
