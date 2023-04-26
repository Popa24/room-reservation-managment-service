package com.example.demo.user.repository;

import com.example.demo.user.service.CreateUserDomainObjectRequest;
import com.example.demo.user.service.UserDomainObject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;


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


    public @NonNull UserDomainObject update(@NonNull UserDomainObject user) {
        final EntityManager em = entityManager
                .getEntityManagerFactory()
                .createEntityManager();
        UserEntity entity = em.find(UserEntity.class, user.getId());

        entity.setName(user.getName());
        entity.setSurname(user.getSurname());
        entity.setEmail(user.getEmail());
        entity.setRoles(user.getRoles());

        em
                .getTransaction()
                .begin();


        em.persist(entity);
        em.getTransaction().commit();
        return fromEntity(entity);
    }


    @NonNull
    public UserDomainObject getById(Integer userId) {
        UserEntity user = entityManager.find(UserEntity.class, userId);


        return fromEntity(user);
    }

    public void delete(@NonNull final Integer userId) {
        UserEntity user = entityManager.find(UserEntity.class, userId);
        entityManager
                .remove(user);
        entityManager
                .getTransaction()
                .commit();
    }

    public UserDomainObject findByEmail(String email) {
        TypedQuery<UserEntity> query = entityManager.createQuery("SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class);
        query.setParameter("email", email);
        List<UserEntity> resultList = query.getResultList();
        return resultList.isEmpty() ? null : fromEntity(resultList.get(0));
    }

    public List<UserDomainObject> getAllUsers() {
        List<UserEntity> entities = entityManager.createQuery("SELECT u FROM UserEntity u", UserEntity.class).getResultList();
        return entities.stream().map(UserRepository::fromEntity).collect(Collectors.toList());
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
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        entity.setName(createUserDomainObjectRequest.getName());
        entity.setSurname(createUserDomainObjectRequest.getSurname());
        entity.setEmail(createUserDomainObjectRequest.getEmail());
        entity.setPassword(passwordEncoder.encode(createUserDomainObjectRequest.getPassword()));
        entity.setRoles("user");

        return entity;
    }


}
