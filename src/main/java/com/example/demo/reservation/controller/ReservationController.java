package com.example.demo.reservation.controller;

import com.example.demo.reservation.service.ReservationDomainObject;
import com.example.demo.reservation.service.ReservationService;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(@NonNull final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createReservation(@RequestBody @NonNull final JsonUpsertReservationDomainRequest request) {
        if (reservationService.isRoomAvailable(request.getRoomId(), request.getStartDate(), request.getEndDate())) {
            final ReservationDomainObject reservationDomainObject = reservationService.save(ReservationControllerHelper.toCreateReservationRequest(request));
            return ResponseEntity.ok().body(ReservationControllerHelper.toJson(reservationDomainObject));
        } else {
            List<ReservationDomainObject> overlappingReservations = reservationService.findOverlappingReservations(request.getRoomId(), request.getStartDate(), request.getEndDate());
            ReservationDomainObject nextAvailableReservation = overlappingReservations.stream().max(Comparator.comparing(ReservationDomainObject::getEndDate)).orElse(null);

            String errorMessage = "The room is already booked for that interval.";
            if (nextAvailableReservation != null) {
                errorMessage += " The room would be available for booking after " + nextAvailableReservation.getEndDate() ;
            }

            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<JsonReservationDomainResponse> getReservationById(@PathVariable Long id) {
        ReservationDomainObject reservationDomainObject = reservationService.findById(id);
        return reservationDomainObject != null ? ResponseEntity.ok(ReservationControllerHelper.toJsonReservationDomainResponse(reservationDomainObject))
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<JsonReservationDomainResponse>> getAllReservations() {
        List<ReservationDomainObject> reservationDomainObjects = reservationService.findAll();
        List<JsonReservationDomainResponse> jsonResponse = reservationDomainObjects.stream()
                .map(ReservationControllerHelper::toJsonReservationDomainResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(jsonResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JsonReservationDomainResponse> updateReservation(@RequestBody @NonNull final JsonUpsertReservationDomainRequest request, @PathVariable Long id) {
        final ReservationDomainObject reservationDomainObject = reservationService.update(ReservationControllerHelper.toReservationDomainObject(request, id));
        return ResponseEntity.ok().body(ReservationControllerHelper.toJson(reservationDomainObject));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
