package com.example.demo.Room;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class RoomModelAssembler implements RepresentationModelAssembler<Room, EntityModel<Room>> {

    @Override
    public EntityModel<Room> toModel(Room room) {

        // Unconditional links to single-item resource and aggregate root

        EntityModel<Room> roomModel = EntityModel.of(room,
                linkTo(methodOn(RoomController.class).one(room.getId())).withSelfRel(),
                linkTo(methodOn(RoomController.class).all()).withRel("room"));



        return roomModel;
    }
}