package com.example.demo.Reservation;


import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ReservationController {
    private final ReservationRepository reservationRepository;
    private final ReservationModelAssembler reservationModelAssembler;
    ReservationController(ReservationRepository reservationRepository, ReservationModelAssembler reservationModelAssembler){
        this.reservationRepository=reservationRepository;
        this.reservationModelAssembler=reservationModelAssembler;
    }
    @GetMapping("reservation")
    CollectionModel<EntityModel<Reservation>> all(){
        List<EntityModel<Reservation>> reservation=reservationRepository.findAll().stream()
                .map(reservationModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(reservation,
                linkTo(methodOn(ReservationController.class).all()).withSelfRel());
    }
    @PostMapping("reservation")
    ResponseEntity<?> newReservation(@RequestBody Reservation newReservation){
        EntityModel<Reservation> entityModel=reservationModelAssembler.toModel(reservationRepository.save(newReservation));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
    @GetMapping("reservation/{id}")
    EntityModel<Reservation> one(@PathVariable Long id){
        Reservation reservation=reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
        return reservationModelAssembler.toModel(reservation);
    }
    @PutMapping("reservation/{id}")
    ResponseEntity<?>replaceReservation(@RequestBody Reservation newReservation, @PathVariable Long id){
        Reservation updateReservation=reservationRepository.findById(id)
                .map( reservation -> {
                    reservation.setStartDate(newReservation.getStartDate());
                    reservation.setEndDate(newReservation.getEndDate());

                    return reservationRepository.save(newReservation);
                })
                .orElseGet(()->{
                    newReservation.setId(id);
                    return reservationRepository.save(newReservation);
                });
        EntityModel<Reservation>entityModel=reservationModelAssembler.toModel(updateReservation);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
    @DeleteMapping("reservation/{id}")
    ResponseEntity<?>deleteRoom(@PathVariable Long id){
        reservationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
