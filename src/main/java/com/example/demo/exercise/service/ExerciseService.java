package com.example.demo.exercise.service;

import com.example.demo.exercise.repository.ExerciseRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExerciseService {
    @NonNull
    final ExerciseRepository exerciseRepository;

    public ExerciseService(@NonNull ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public ExerciseDomain create(@NonNull final CreateExerciseDomain createExerciseDomain) {
        return exerciseRepository.create(createExerciseDomain);
    }

    public ExerciseDomain update(@NonNull final ExerciseDomain exerciseDomain
    ) {
        return exerciseRepository.update(exerciseDomain);
    }

    public void delete(@NonNull final Long exerciseId) {
        exerciseRepository.delete(exerciseId);
    }

    public List<ExerciseDomain> listByValue(@NonNull final BigDecimal value) {
        return exerciseRepository.listByValue(value);
    }
}