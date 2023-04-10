package com.example.demo.room.service;

import com.example.demo.reservation.service.AggregateRoomReservationInfo;
import com.example.demo.reservation.service.ReservationDomainObject;
import com.example.demo.reservation.service.ReservationService;
import com.example.demo.room.repository.RoomRepository;
import com.example.demo.user.service.UserService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<ReservationInfoDto> buildReservationInfoDtoList(List<Long> reservationIds) {
        List<ReservationInfoDto> reservationInfoDtoList = new ArrayList<>();

        for (Long reservationId : reservationIds) {
            ReservationDomainObject reservationDomainObject = reservationService.findById(reservationId);
            ReservationInfoDto reservationInfoDto = ReservationInfoDto.builder()
                    .reservationId(reservationDomainObject.getId())
                    .userInfo(userService.getUserInfo(reservationDomainObject.getUserId()))
                    .stardDate(reservationDomainObject.getStartDate())
                    .enddDate(reservationDomainObject.getEndDate())
                    .build();
            reservationInfoDtoList.add(reservationInfoDto);
        }

        return reservationInfoDtoList;
    }
    public TimeFrameDto buildTimeFrameDto(Long roomId){
        return TimeFrameDto.builder()
                .startDate(reservationService.findFirstStartDateByRoomId(roomId))
                .endDate(reservationService.findLastEndDateByRoomId(roomId))
                .build();
    }

    public AllRoomInformationDto buildAllRoomInformationDto(Long roomId){
        ReservationDomainObject reservationDomainObject=reservationService.findReservationByRoomId(roomId);
        List<AggregateRoomReservationInfo>  aggregateRoomReservationInfoList=reservationService.getMostRented();
        return AllRoomInformationDto.builder()
                .name(roomRepository.getById(roomId).getName())
                .generatedRevenue(getTotalAmountOfRentedTimeByRoomId(roomId,aggregateRoomReservationInfoList)*roomRepository.getById(roomId).getPrice())
                .timeframe(buildTimeFrameDto(roomId))
                .reservationInfoList(buildReservationInfoDtoList(reservationService.findReservationIdsByRoomId(roomId)))
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