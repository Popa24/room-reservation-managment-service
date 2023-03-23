package com.example.demo.user.repository;

import com.example.demo.user.service.CreateUserDomainObjectRequest;
import com.example.demo.user.service.UserDomainObject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NonNull;
import org.springframework.stereotype.Repository;


@Repository
public class UserRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @NonNull
    public UserDomainObject save(@NonNull final CreateUserDomainObjectRequest createUserDomainObjectRequest) {
        final EntityManager em = entityManager
                .getEntityManagerFactory()
                .createEntityManager();

        em.getTransaction().begin();

        final UserEntity entity = toEntity(createUserDomainObjectRequest);

        em.persist(entity);
        em.getTransaction().commit();
        return fromEntity(entity);
    }

    @NonNull
    private static UserDomainObject fromEntity(@NonNull final UserEntity entity) {
        return UserDomainObject.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .roles(entity.getRoles())
                .build();
    }

    @NonNull
    private static UserEntity toEntity(@NonNull final CreateUserDomainObjectRequest createUserDomainObjectRequest) {
        final UserEntity entity = new UserEntity();
        entity.setName(createUserDomainObjectRequest.getName());
        entity.setSurname(createUserDomainObjectRequest.getSurname());
        entity.setEmail(createUserDomainObjectRequest.getEmail());
        entity.setPassword(createUserDomainObjectRequest.getPassword());
        entity.setRoles("admin");

        return entity;
    }

}
