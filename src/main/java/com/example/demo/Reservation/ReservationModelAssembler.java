package com.example.demo.Reservation;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ReservationModelAssembler implements RepresentationModelAssembler<Reservation, EntityModel<Reservation>> {
    @Override
    public EntityModel<Reservation> toModel(Reservation reservation) {

        // Unconditional links to single-item resource and aggregate root

        EntityModel<Reservation> reservationModel = EntityModel.of(reservation,
                linkTo(methodOn(ReservationController.class).one(reservation.getId())).withSelfRel(),
                linkTo(methodOn(ReservationController.class).all()).withRel("reservation"));



        return reservationModel;
    }
}
