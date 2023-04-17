package com.example.demo.reservation.service;

import com.example.demo.reservation.repository.ReservationRepository;
import com.example.demo.user.service.UserInfoDto;
import com.example.demo.user.service.UserService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    @NonNull
    final ReservationRepository reservationRepository;
    final UserService userService;

    public ReservationService(@NonNull final ReservationRepository reservationRepository,@NonNull final UserService userService) {
        this.reservationRepository = reservationRepository;
        this.userService = userService;
    }

    public ReservationDomainObject save(@NonNull final CreateReservationDomainObjectRequest createReservationDomainObjectRequest) {
        return reservationRepository.save(createReservationDomainObjectRequest);
    }
    public List<ReservationDomainObject> findOverlappingReservations(Long roomId, Timestamp startDate, Timestamp endDate) {
        return reservationRepository.getReservationsByRoomId(roomId).stream()
                .filter(reservation -> (reservation.getStartDate().before(endDate) || reservation.getStartDate().equals(endDate)) &&
                        (reservation.getEndDate().after(startDate) || reservation.getEndDate().equals(startDate)))
                .collect(Collectors.toList());
    }

    public ReservationDomainObject findById(@NonNull final Long id) {
        return reservationRepository.findById(id);
    }

    public List<ReservationDomainObject> findAll() {
        return reservationRepository.findAll();
    }

    public void delete(@NonNull final Long id) {
        reservationRepository.delete(id);
    }

    public ReservationDomainObject update(@NonNull final ReservationDomainObject reservationDomainObject) {
        return reservationRepository.update(reservationDomainObject);
    }
    public int getTotalAmountOfRentedTime(Long roomId, List<ReservationDomainObject> reservations) {
        List<ReservationDomainObject> reservationsForRoom = reservations.stream()
                .filter(r -> r.getRoomId().equals(roomId))
                .toList();

        return reservationsForRoom.stream()
                .mapToInt(r -> (int) Duration.between(r.getStartDate().toLocalDateTime(), r.getEndDate().toLocalDateTime()).toHours())
                .sum();
    }

    public List<ReservationInfoDto> buildReservationInfoDtoList(List<Long> reservationIds, List<ReservationDomainObject> reservations) {
        List<ReservationInfoDto> reservationInfoDtoList = new ArrayList<>();
        Map<Integer, UserInfoDto> localUserCache = new HashMap<>();

        Map<Long, ReservationDomainObject> reservationsById = reservations.stream()
                .collect(Collectors.toMap(ReservationDomainObject::getId, Function.identity()));

        for (Long reservationId : reservationIds) {
            ReservationDomainObject reservationDomainObject = reservationsById.get(reservationId);
            Integer userId = reservationDomainObject.getUserId();

            UserInfoDto userInfo = localUserCache.computeIfAbsent(userId, userService::getUserInfo);

            ReservationInfoDto reservationInfoDto = ReservationInfoDto.builder()
                    .reservationId(reservationDomainObject.getId())
                    .userInfo(userInfo)
                    .stardDate(reservationDomainObject.getStartDate())
                    .enddDate(reservationDomainObject.getEndDate())
                    .build();
            reservationInfoDtoList.add(reservationInfoDto);
        }

        return reservationInfoDtoList;
    }
    public boolean isRoomAvailable(Long roomId, Timestamp startDate, Timestamp endDate) {
        List<ReservationDomainObject> reservations = reservationRepository.getReservationsByRoomId(roomId);
        return reservations.stream().noneMatch(reservation ->
                (reservation.getStartDate().before(endDate) || reservation.getStartDate().equals(endDate)) &&
                        (reservation.getEndDate().after(startDate) || reservation.getEndDate().equals(startDate)));
    }


    private int getTotalAmountOfRentedTime( List<ReservationDomainObject> reservations) {
        return reservations.stream()
                .mapToInt(reservationDomainObject -> (int) TimeUnit.MILLISECONDS.toHours(
                        reservationDomainObject.getEndDate().getTime() - reservationDomainObject.getStartDate().getTime()
                ))
                .sum();
    }

    public List<AggregateRoomReservationInfo> getMostRented() {
        List<AggregateRoomReservationInfo> output = new ArrayList<>();
        Map<Long, List<ReservationDomainObject>> reservationMap = findAll().stream()
                .collect(Collectors.groupingBy(ReservationDomainObject::getRoomId));

        for (Map.Entry<Long, List<ReservationDomainObject>> entry : reservationMap.entrySet()) {
            Long roomId = entry.getKey();
            int nrOfReservations = entry.getValue().size();
            int totalAmountOfRentedTime = getTotalAmountOfRentedTime(entry.getValue());


            output.add(
                    AggregateRoomReservationInfo.builder()
                            .roomId(roomId)
                            .nrOfReservations(nrOfReservations)
                            .totalAmountOfRentedTime(totalAmountOfRentedTime)
                            .build());
        }
        return output;
    }
}
