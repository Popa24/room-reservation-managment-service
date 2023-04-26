package com.example.demo.room.repository;


import com.example.demo.room.service.CreateRoomDomainObjectRequest;
import com.example.demo.room.service.RoomDomainObject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RoomRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    public RoomRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @NonNull
    public RoomDomainObject save(@NonNull final CreateRoomDomainObjectRequest createRoomDomainObjectRequest) {
        final EntityManager em = entityManager
                .getEntityManagerFactory()
                .createEntityManager();

        em.getTransaction().begin();

        final RoomEntity entity = toEntity(createRoomDomainObjectRequest);

        em.persist(entity);
        em.getTransaction().commit();
        return fromEntity(entity);
    }

    public Integer getPriceById(Long roomId) {
        RoomEntity roomEntity = entityManager.find(RoomEntity.class, roomId);
        return roomEntity != null ? roomEntity.getPrice() : null;
    }

    public boolean existsByNameAndLocation(String name, String city, String street, int streetNo) {
        return false;
    }

    public @NonNull RoomDomainObject update(@NonNull RoomDomainObject room) {
        final EntityManager em = entityManager
                .getEntityManagerFactory()
                .createEntityManager();
        RoomEntity entity = em.find(RoomEntity.class, room.getId());

        entity.setName(room.getName());
        entity.setCity(room.getCity());
        entity.setStreet(room.getStreet());
        entity.setStreetNo(room.getStreetNo());
        entity.setCapacity(room.getCapacity());
        entity.setPrice(room.getPrice());
        entity.setDescription(room.getDescription());

        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        return fromEntity(entity);
    }

    @NonNull
    public RoomDomainObject getById(Long roomId) {
        RoomEntity room = entityManager.find(RoomEntity.class, roomId);
        return fromEntity(room);
    }

    public void delete(@NonNull final Long roomId) {
        RoomEntity room = entityManager.find(RoomEntity.class, roomId);
        entityManager.remove(room);
        entityManager.getTransaction().commit();
    }

    public List<RoomDomainObject> getAllRooms() {
        List<RoomEntity> entities = entityManager.createQuery("SELECT r FROM RoomEntity r", RoomEntity.class).getResultList();
        return entities.stream().map(RoomRepository::fromEntity).collect(Collectors.toList());
    }

    @NonNull
    private static RoomDomainObject fromEntity(@NonNull final RoomEntity entity) {
        return RoomDomainObject.builder()
                .id(entity.getId())
                .name(entity.getName())
                .city(entity.getCity())
                .street(entity.getStreet())
                .streetNo(entity.getStreetNo())
                .capacity(entity.getCapacity())
                .price(entity.getPrice())
                .description(entity.getDescription())
                .build();
    }

    @NonNull
    private static RoomEntity toEntity(@NonNull final CreateRoomDomainObjectRequest createRoomDomainObjectRequest) {
        final RoomEntity entity = new RoomEntity();

        entity.setName(createRoomDomainObjectRequest.getName());
        entity.setCity(createRoomDomainObjectRequest.getCity());
        entity.setStreet(createRoomDomainObjectRequest.getStreet());
        entity.setStreetNo(createRoomDomainObjectRequest.getStreetNo());
        entity.setCapacity(createRoomDomainObjectRequest.getCapacity());
        entity.setPrice(createRoomDomainObjectRequest.getPrice());
        entity.setDescription(createRoomDomainObjectRequest.getDescription());

        return entity;
    }
}
