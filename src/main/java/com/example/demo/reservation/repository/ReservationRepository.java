package com.example.demo.reservation.repository;

import com.example.demo.reservation.service.CreateReservationDomainObjectRequest;
import com.example.demo.reservation.service.ReservationDomainObject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ReservationRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    public ReservationRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @NonNull
    public ReservationDomainObject save(@NonNull final CreateReservationDomainObjectRequest createReservationDomainObjectRequest) {
        final EntityManager em = entityManager
                .getEntityManagerFactory()
                .createEntityManager();

        em.getTransaction().begin();

        final ReservationEntity entity = toEntity(createReservationDomainObjectRequest);

        em.persist(entity);
        em.getTransaction().commit();
        return fromEntity(entity);
    }

    public ReservationDomainObject findById(Long id) {
        ReservationEntity reservationEntity = entityManager.find(ReservationEntity.class, id);
        return reservationEntity != null ? fromEntity(reservationEntity) : null;
    }

    public List<ReservationDomainObject> findAll() {
        List<ReservationEntity> entities = entityManager.createQuery("SELECT r FROM ReservationEntity r", ReservationEntity.class).getResultList();
        return entities.stream().map(ReservationRepository::fromEntity).collect(Collectors.toList());
    }

    public void delete(Long id) {
        EntityManager em = entityManager.getEntityManagerFactory().createEntityManager();
        ReservationEntity reservationEntity = em.find(ReservationEntity.class, id);

        if (reservationEntity != null) {
            em.getTransaction().begin();
            em.remove(reservationEntity);
            em.getTransaction().commit();
        }
    }

    public ReservationDomainObject update(ReservationDomainObject reservationDomainObject) {
        EntityManager em = entityManager.getEntityManagerFactory().createEntityManager();
        ReservationEntity reservationEntity = em.find(ReservationEntity.class, reservationDomainObject.getId());

        if (reservationEntity != null) {
            reservationEntity.setUserId(Long.valueOf(reservationDomainObject.getUserId()));
            reservationEntity.setRoomId(reservationDomainObject.getRoomId());
            reservationEntity.setStartDate(reservationDomainObject.getStartDate());
            reservationEntity.setEndDate(reservationDomainObject.getEndDate());

            em.getTransaction().begin();
            em.merge(reservationEntity);
            em.getTransaction().commit();

            return fromEntity(reservationEntity);
        } else {
            return null;
        }
    }

    public List<ReservationDomainObject> getReservationsByRoomId(Long roomId) {
        TypedQuery<ReservationEntity> query = entityManager.createQuery("SELECT r FROM ReservationEntity r WHERE r.roomId = :roomId", ReservationEntity.class);
        query.setParameter("roomId", roomId);
        List<ReservationEntity> resultList = query.getResultList();
        return resultList.stream().map(ReservationRepository::fromEntity).collect(Collectors.toList());
    }

    public List<ReservationDomainObject> getReservationsByUserId(Long userId) {
        TypedQuery<ReservationEntity> query = entityManager.createQuery("SELECT r FROM ReservationEntity r WHERE r.userId = :userId", ReservationEntity.class);
        query.setParameter("userId", userId);
        List<ReservationEntity> resultList = query.getResultList();
        return resultList.stream().map(ReservationRepository::fromEntity).collect(Collectors.toList());
    }

    @NonNull
    private static ReservationDomainObject fromEntity(@NonNull final ReservationEntity entity) {
        return ReservationDomainObject.builder()
                .id(entity.getId())
                .userId(Math.toIntExact(entity.getUserId()))
                .roomId(entity.getRoomId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .build();
    }

    @NonNull
    private static ReservationEntity toEntity(@NonNull final CreateReservationDomainObjectRequest createReservationDomainObjectRequest) {
        final ReservationEntity entity = new ReservationEntity();

        entity.setUserId(createReservationDomainObjectRequest.getUserId());
        entity.setRoomId(createReservationDomainObjectRequest.getRoomId());
        entity.setStartDate(createReservationDomainObjectRequest.getStartDate());
        entity.setEndDate(createReservationDomainObjectRequest.getEndDate());

        return entity;
    }
}
