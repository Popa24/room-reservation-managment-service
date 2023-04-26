package com.example.demo.room.service;

import com.example.demo.room.repository.RoomRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoomService {
    @NonNull
    final RoomRepository roomRepository;


    public RoomService(@NonNull final RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @NonNull
    public RoomDomainObject save(@NonNull final CreateRoomDomainObjectRequest createRoomDomainObjectRequest) {
        return roomRepository.save(createRoomDomainObjectRequest);
    }

    public boolean existsByNameAndLocation(String name, String ciy, String street, int streetNo) {
        return roomRepository.existsByNameAndLocation(name, ciy, street, streetNo);
    }

    @NonNull
    public RoomDomainObject update(@NonNull final RoomDomainObject roomDomainObject) {
        return roomRepository.update(roomDomainObject);
    }

    public RoomDomainObject getById(Long roomId) {

        return roomRepository.getById(roomId);
    }


    public void delete(@NonNull final Long roomId) {

        roomRepository.delete(roomId);
    }

    public List<RoomDomainObject> getAllRooms() {

        return roomRepository.getAllRooms();
    }


}