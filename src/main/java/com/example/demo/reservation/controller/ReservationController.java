package com.example.demo.reservation.controller;

import com.example.demo.reservation.service.ReservationDomainObject;
import com.example.demo.reservation.service.ReservationService;
import com.example.demo.roomreservation.guestrepository.GuestStatus;
import com.example.demo.roomreservation.roomreservationservice.Guest;
import com.example.demo.roomreservation.roomreservationservice.InviteStatus;
import com.example.demo.roomreservation.roomreservationservice.RoomReservationService;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final RoomReservationService roomReservationService;

    public ReservationController(@NonNull final ReservationService reservationService, @NonNull final RoomReservationService roomReservationService) {
        this.reservationService = reservationService;
        this.roomReservationService = roomReservationService;
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
                errorMessage += " The room would be available for booking after " + nextAvailableReservation.getEndDate();
            }

            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        }
    }

    @GetMapping("/top-users")
    public ResponseEntity<List<JsonAllUserInfoDto>> getTopUsers(@RequestParam(required = false) Integer limit, @RequestParam(required = false) Integer minimumSpent) {
        List<JsonAllUserInfoDto> jsonResponse = roomReservationService.buildAllUserInfoDtoList(limit, minimumSpent)
                .stream().map(ReservationControllerHelper::toJson).toList();
        return ResponseEntity.ok().body(jsonResponse);
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

    @PostMapping("/invite/{id}")
    public List<InviteStatusDto> sendInvitations(@NonNull @PathVariable("id") Long reservationId, @NonNull @RequestBody List<GuestsDto> guestsDtoList) {
        List<Guest> guests = guestsDtoList.stream()
                .map(ReservationControllerHelper::toGuest)
                .collect(Collectors.toList());
        List<InviteStatus> inviteStatusList = roomReservationService.sendInvitations(reservationId, guests);
        return inviteStatusList.stream()
                .map(ReservationControllerHelper::toJson)
                .collect(Collectors.toList());
    }
    @GetMapping("/status/{encodedGuestId}")
    public ResponseEntity<Void> updateGuestStatus(@PathVariable String encodedGuestId, @RequestParam GuestStatus status) {
        String decodedGuestIdStr = new String(Base64.getDecoder().decode(encodedGuestId));
        Long guestId = Long.parseLong(decodedGuestIdStr);

        roomReservationService.updateStatus(guestId, status);
        return ResponseEntity.ok().build();
    }
}
