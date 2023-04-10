package com.example.demo.reservation.service;

import com.example.demo.reservation.repository.ReservationRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    @NonNull
    final ReservationRepository reservationRepository;


    public ReservationService(@NonNull final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;

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
    public boolean isRoomAvailable(Long roomId, Timestamp startDate, Timestamp endDate) {
        List<ReservationDomainObject> reservations = reservationRepository.getReservationsByRoomId(roomId);
        return reservations.stream().noneMatch(reservation ->
                (reservation.getStartDate().before(endDate) || reservation.getStartDate().equals(endDate)) &&
                        (reservation.getEndDate().after(startDate) || reservation.getEndDate().equals(startDate)));
    }
    private int getNrOfReservations(Long roomId, Map<Long, List<ReservationDomainObject>> reservationMap) {
        List<ReservationDomainObject> reservations = reservationMap.getOrDefault(roomId, Collections.emptyList());
        return reservations.size();
    }

    private int getTotalAmountOfRentedTime( List<ReservationDomainObject> reservations) {
        return reservations.stream()
                .mapToInt(reservationDomainObject -> (int) TimeUnit.MILLISECONDS.toHours(
                        reservationDomainObject.getEndDate().getTime() - reservationDomainObject.getStartDate().getTime()
                ))
                .sum();
    }
    public Timestamp findFirstStartDateByRoomId(Long roomId){
        return reservationRepository.findFirstStartDateByRoomId(roomId);
    }
    public Timestamp findLastEndDateByRoomId(Long roomId){
        return reservationRepository.findLastEndDateByRoomId(roomId);
    }
    public ReservationDomainObject findReservationByRoomId(Long roomId){
        return reservationRepository.findReservationByRoomId(roomId);
    }
    public List<Long> findReservationIdsByRoomId(Long roomId){
        return reservationRepository.findReservationIdsByRoomId(roomId);
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
