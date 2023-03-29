package com.example.demo.reservation.controller;

import com.example.demo.reservation.service.ReservationDomainObject;
import com.example.demo.reservation.service.ReservationService;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(@NonNull final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/create/reservation")
    public ResponseEntity<JsonReservationDomainResponse> createReservation(@RequestBody @NonNull final JsonUpsertReservationDomainRequest request) {
        if (reservationService.isRoomAvailable(request.getRoomId(), request.getStartDate(), request.getEndDate())) {
            final ReservationDomainObject reservationDomainObject = reservationService.save(ReservationControllerHelper.toCreateReservationRequest(request));
            return ResponseEntity.ok().body(JsonReservationDomainResponse.toJson(reservationDomainObject));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }


    @GetMapping("/reservation/{id}")
    public ResponseEntity<JsonReservationDomainResponse> getReservationById(@PathVariable Long id) {
        ReservationDomainObject reservationDomainObject = reservationService.findById(id);
        return reservationDomainObject != null ? ResponseEntity.ok(ReservationControllerHelper.toJsonReservationDomainResponse(reservationDomainObject))
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<JsonReservationDomainResponse>> getAllReservations() {
        List<ReservationDomainObject> reservationDomainObjects = reservationService.findAll();
        List<JsonReservationDomainResponse> jsonResponse = reservationDomainObjects.stream()
                .map(ReservationControllerHelper::toJsonReservationDomainResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(jsonResponse);
    }

    @PutMapping("/reservation/{id}")
    public ResponseEntity<JsonReservationDomainResponse> updateReservation(@RequestBody @NonNull final JsonUpsertReservationDomainRequest request, @PathVariable Long id) {
        final ReservationDomainObject reservationDomainObject = reservationService.update(ReservationControllerHelper.toReservationDomainObject(request, id));
        return ResponseEntity.ok().body(JsonReservationDomainResponse.toJson(reservationDomainObject));
    }

    @DeleteMapping("/reservation/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
