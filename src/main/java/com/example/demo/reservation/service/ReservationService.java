package com.example.demo.reservation.service;

import com.example.demo.reservation.repository.ReservationRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
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


}
