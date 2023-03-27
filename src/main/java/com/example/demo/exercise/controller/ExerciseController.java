package com.example.demo.exercise.controller;

import com.example.demo.exercise.service.CreateExerciseRequest;
import com.example.demo.exercise.service.ExerciseDomain;
import com.example.demo.exercise.service.ExerciseService;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.exercise.controller.ExerciseControllerHelper.toJson;

@RestController(value = "exercise")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(@NonNull final ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping
    public ResponseEntity<JsonExerciseDomainResponse> create(@RequestBody @NonNull final JsonUpsertExerciseDomainRequest request) {
        final CreateExerciseRequest createExerciseRequest = ExerciseControllerHelper.toCreateExerciseDomain(request);
        final ExerciseDomain exerciseDomain = exerciseService.create(createExerciseRequest);
        return ResponseEntity.ok().body(ExerciseControllerHelper.toJson(exerciseDomain));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JsonExerciseDomainResponse> update(@RequestBody @NonNull final JsonUpsertExerciseDomainRequest request,
                                                             @PathVariable Long id) {
        final ExerciseDomain exerciseDomain = ExerciseControllerHelper.toExerciseDomain(request, id);
        final ExerciseDomain updatedExerciseDomain = exerciseService.update(exerciseDomain);
        return ResponseEntity.ok().body(ExerciseControllerHelper.toJson(updatedExerciseDomain));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        exerciseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("list")
    public ResponseEntity<List<JsonExerciseDomainResponse>> listByValue(@RequestParam(name = "value") BigDecimal value) {
        final List<ExerciseDomain> exerciseDomains = exerciseService.listByValue(value);

        final List<JsonExerciseDomainResponse> jsonExerciseDomainResponses =
                exerciseDomains.stream()
                        .map(ExerciseControllerHelper::toJson)
                        .collect(Collectors.toList());

        return ResponseEntity.ok().body(jsonExerciseDomainResponses);
    }


}
