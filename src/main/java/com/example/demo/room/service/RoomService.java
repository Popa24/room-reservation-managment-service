package com.example.demo.room.service;

import com.example.demo.reservation.service.AggregateRoomReservationInfo;
import com.example.demo.reservation.service.ReservationService;
import com.example.demo.room.repository.RoomRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService  {
    @NonNull
    final RoomRepository roomRepository;
   final ReservationService reservationService;

    public RoomService(@NonNull final RoomRepository roomRepository, @NonNull final ReservationService reservationService) {
        this.roomRepository = roomRepository;
        this.reservationService=reservationService;
    }
    public List<com.example.demo.room.service.TopRented> getTopRentedRooms() {
        List<com.example.demo.room.service.TopRented>output=new ArrayList<>();
        List<AggregateRoomReservationInfo>rooms=reservationService.getMostRented();
        for(AggregateRoomReservationInfo room:rooms) {
            output.add(
                    com.example.demo.room.service.TopRented.builder()
                            .roomId(room.getRoomId())
                            .nrOfReservations(room.getNrOfReservations())
                            .totalAmountOfRentedTime(room.getTotalAmountOfRentedTime())
                            .generatedRevenue(roomRepository.getById(room.getRoomId()).getPrice()* room.getTotalAmountOfRentedTime())
                            .build()
            );
        }
        return output;
    }
    @NonNull
    public RoomDomainObject save(@NonNull final CreateRoomDomainObjectRequest createRoomDomainObjectRequest) {
        return roomRepository.save(createRoomDomainObjectRequest);
    }
    public boolean existsByNameAndLocation(String name, String ciy,String street, int streetNo) {
        return roomRepository.existsByNameAndLocation(name, ciy,street,streetNo);
    }
    @NonNull
    public RoomDomainObject update(@NonNull final RoomDomainObject roomDomainObject) {
        return roomRepository.update(roomDomainObject);
    }

    public RoomDomainObject getById(Long roomId) {
        return roomRepository.getById(roomId);
    }
    public Integer getPriceById(Long roomId) {
        return roomRepository.getPriceById(roomId);
    }
    public void delete(@NonNull final Long roomId) {
        roomRepository.delete(roomId);
    }

    public List<RoomDomainObject> getAllRooms() {
        return roomRepository.getAllRooms();
    }
}