package com.example.demo.room.service;

import com.example.demo.reservation.service.AggregateRoomReservationInfo;
import com.example.demo.reservation.service.ReservationDomainObject;
import com.example.demo.reservation.service.ReservationService;
import com.example.demo.room.repository.RoomRepository;
import com.example.demo.user.service.UserService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class RoomService {
    @NonNull
    final RoomRepository roomRepository;
    final ReservationService reservationService;
    final UserService userService;

    public RoomService(@NonNull final RoomRepository roomRepository, @NonNull final ReservationService reservationService, @NonNull final UserService userService) {
        this.roomRepository = roomRepository;
        this.reservationService = reservationService;
        this.userService = userService;
    }
    public AllRoomInformationDto buildAllRoomInformationDto(Long roomId) {
        List<ReservationDomainObject> reservations = reservationService.findAll();

        List<ReservationDomainObject> reservationsForRoom = reservations.stream()
                .filter(r -> r.getRoomId().equals(roomId))
                .toList();

        List<Long> reservationIds = reservationsForRoom.stream().map(ReservationDomainObject::getId).collect(Collectors.toList());

        int totalAmountOfRentedTime = reservationService.getTotalAmountOfRentedTime(roomId, reservations);
        TimeFrameDto timeFrameDto = buildTimeFrameDto(roomId, reservations);

        return AllRoomInformationDto.builder()
                .name(roomRepository.getById(roomId).getName())
                .generatedRevenue(totalAmountOfRentedTime * roomRepository.getById(roomId).getPrice())
                .timeframe(timeFrameDto)
                .reservationInfoList(reservationService.buildReservationInfoDtoList(reservationIds, reservations))
                .build();
    }




    public TimeFrameDto buildTimeFrameDto(Long roomId, List<ReservationDomainObject> reservations) {
        List<ReservationDomainObject> reservationsForRoom = reservations.stream()
                .filter(r -> r.getRoomId().equals(roomId))
                .toList();

        Optional<Timestamp> firstStartDate = reservationsForRoom.stream()
                .map(ReservationDomainObject::getStartDate)
                .min(Comparator.comparing(Timestamp::getTime));

        Optional<Timestamp> lastEndDate = reservationsForRoom.stream()
                .map(ReservationDomainObject::getEndDate)
                .max(Comparator.comparing(Timestamp::getTime));

        assert firstStartDate.orElse(null) != null;
        return TimeFrameDto.builder()
                .startDate(firstStartDate.orElse(null))
                .endDate(lastEndDate.orElse(null))
                .build();
    }






    public Integer getTotalAmountOfRentedTimeByRoomId(Long roomId, List<AggregateRoomReservationInfo> aggregateRoomReservationInfoList) {
        for (AggregateRoomReservationInfo info : aggregateRoomReservationInfoList) {
            if (info.getRoomId().equals(roomId)) {
                return info.getTotalAmountOfRentedTime();
            }
        }
        return null;
    }

    public List<TopRented> getTopRentedRooms(Integer top, Integer minGeneratedRevenue) {
        return reservationService.getMostRented()
                .stream()
                .map(room -> TopRented.builder()
                        .roomId(room.getRoomId())
                        .nrOfReservations(room.getNrOfReservations())
                        .totalAmountOfRentedTime(room.getTotalAmountOfRentedTime())
                        .generatedRevenue(roomRepository.getById(room.getRoomId()).getPrice() * room.getTotalAmountOfRentedTime())
                        .build())
                .filter(topRented -> (minGeneratedRevenue == null || topRented.getGeneratedRevenue() > minGeneratedRevenue))
                .sorted((o1, o2) -> Double.compare(o2.getGeneratedRevenue(), o1.getGeneratedRevenue()))
                .limit(Optional.ofNullable(top).orElse(5))
                .collect(Collectors.toList());
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