package com.example.demo.roomreservation.guestrepository;

import com.example.demo.roomreservation.guestservice.CreateFullGuestDto;
import com.example.demo.roomreservation.guestservice.FullGuestDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GuestRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    public GuestRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void saveAll(@NonNull final List<FullGuestDto> guestEntities) {
        final EntityManager em = entityManager
                .getEntityManagerFactory()
                .createEntityManager();

        final List<GuestEntity> entity=toEntities(guestEntities);
        em.getTransaction().begin();
        entity.forEach(em::persist);
        em.getTransaction().commit();
        fromEntities(entity);
    }

    public void updateStatus(@NonNull final Long guestId, @NonNull final GuestStatus status) {
        final EntityManager em = entityManager
                .getEntityManagerFactory()
                .createEntityManager();

        GuestEntity guestEntity = em.find(GuestEntity.class, guestId);

        guestEntity.setStatus(status);

        em.getTransaction().begin();
        em.persist(guestEntity);
        em.getTransaction().commit();
        fromEntity(guestEntity);
    }
    public FullGuestDto findById(@NonNull final Long guestId) {
        GuestEntity guestEntity = entityManager.find(GuestEntity.class, guestId);
        return guestEntity == null ? null : fromEntity(guestEntity);
    }

    public List<CreateFullGuestDto> findByReservationId(@NonNull final Long reservationId) {
        TypedQuery<GuestEntity> query = entityManager.createQuery("SELECT g FROM GuestEntity g WHERE g.reservationId = :reservationId", GuestEntity.class);
        query.setParameter("reservationId", reservationId);
        List<GuestEntity>resultList=query.getResultList();
        return resultList.isEmpty()? null : fromEntitiesWithId(resultList);

    }
    @NonNull
    private static List<GuestEntity> toEntities(final @NonNull List<FullGuestDto> fullGuestDtos) {
        return fullGuestDtos.stream()
                .map(fullGuestDto -> {
                    GuestEntity entity = new GuestEntity();
                    entity.setReservationId(fullGuestDto.getReservationId());
                    entity.setName(fullGuestDto.getName());
                    entity.setEmail(fullGuestDto.getEmail());
                    entity.setStatus(GuestStatus.valueOf(String.valueOf(fullGuestDto.getStatus())));
                    return entity;
                })
                .collect(Collectors.toList());
    }
    @NonNull
    private static FullGuestDto fromEntity(@NonNull final GuestEntity guestEntity){
        return FullGuestDto.builder()
                .reservationId(guestEntity.getReservationId())
                .name(guestEntity.getName())
                .email(guestEntity.getEmail())
                .status(guestEntity.getStatus())
                .build();
    }




    @NonNull
    private static List<FullGuestDto> fromEntities(final @NonNull List<GuestEntity> guestEntities) {
        return guestEntities.stream()
                .map(guestEntity -> FullGuestDto.builder()
                        .reservationId(guestEntity.getReservationId())
                        .name(guestEntity.getName())
                        .email(guestEntity.getEmail())
                        .status(GuestStatus.valueOf(guestEntity.getStatus().toString()))
                        .build())
                .collect(Collectors.toList());
    }
    @NonNull
    private static List<CreateFullGuestDto> fromEntitiesWithId(final @NonNull List<GuestEntity> guestEntities) {
        return guestEntities.stream()
                .map(guestEntity -> CreateFullGuestDto.builder()
                        .id(guestEntity.getId())
                        .reservationId(guestEntity.getReservationId())
                        .name(guestEntity.getName())
                        .email(guestEntity.getEmail())
                        .status(GuestStatus.valueOf(guestEntity.getStatus().toString()))
                        .build())
                .collect(Collectors.toList());
    }

}
