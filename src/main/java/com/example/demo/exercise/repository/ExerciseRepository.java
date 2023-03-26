package com.example.demo.exercise.repository;

import com.example.demo.exercise.service.CreateExerciseDomain;
import com.example.demo.exercise.service.ExerciseDomain;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class ExerciseRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    public ExerciseRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ExerciseDomain create(CreateExerciseDomain createExerciseDomain) {
        final EntityManager em = entityManager
                .getEntityManagerFactory()
                .createEntityManager();

        em.getTransaction().begin();

        final ExerciseEntity entity = toEntity(createExerciseDomain);

        em.persist(entity);
        em.getTransaction().commit();
        return fromEntity(entity);
    }

    public ExerciseDomain update(ExerciseDomain exercise) {
        final EntityManager em = entityManager
                .getEntityManagerFactory()
                .createEntityManager();
        ExerciseEntity entity = em.find(ExerciseEntity.class, exercise.getId());

        entity.setName(exercise.getName());
        entity.setCreated_at(Timestamp.from(exercise.getCreated_at()));
        entity.setUpdated_at(Timestamp.from(exercise.getUpdated_at()));
        entity.setValue(exercise.getValue());
        entity.setType(exercise.getType().toString());
        em.getTransaction().begin();

        em.persist(entity);
        em.getTransaction().commit();
        return fromEntity(entity);
    }

    public void delete(Long exerciseId) {
        final EntityManager em = entityManager
                .getEntityManagerFactory()
                .createEntityManager();
        ExerciseEntity entity = em.find(ExerciseEntity.class, exerciseId);

        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
    }

    public List<ExerciseDomain> listByValue(BigDecimal value) {
        TypedQuery<ExerciseEntity> query = entityManager.createQuery("SELECT e FROM ExerciseEntity e WHERE e.value = :value", ExerciseEntity.class);
        query.setParameter("value", value);
        List<ExerciseEntity> entities = query.getResultList();
        return entities.stream().map(ExerciseRepository::fromEntity).toList();
    }
    @NonNull
    private static ExerciseDomain fromEntity(@NonNull final ExerciseEntity entity) {
        return ExerciseDomain.builder()
                .id(entity.getId())
                .name(entity.getName())
                .created_at(entity.getCreated_at().toInstant())
                .updated_at(entity.getUpdated_at().toInstant())
                .value(entity.getValue())
                .type(TypeEnum.valueOf(entity.getType()))
                .build();
    }

    @NonNull
    private static ExerciseEntity toEntity(@NonNull final CreateExerciseDomain createExerciseDomain) {
        final ExerciseEntity entity = new ExerciseEntity();
        entity.setName(createExerciseDomain.getName());
        entity.setCreated_at(Timestamp.from(createExerciseDomain.getCreated_at()));
        entity.setUpdated_at(Timestamp.from(createExerciseDomain.getUpdated_at()));
        entity.setValue(createExerciseDomain.getValue());
        entity.setType(createExerciseDomain.getType().toString());
        return entity;
    }
}
