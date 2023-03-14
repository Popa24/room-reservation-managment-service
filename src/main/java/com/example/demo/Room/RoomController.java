package com.example.demo.Room;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class RoomController {
    private final RoomRepository roomRepository;
    private final RoomModelAssembler roomModelAssembler;
    RoomController(RoomRepository roomRepository, RoomModelAssembler roomModelAssembler){
        this.roomRepository=roomRepository;
        this.roomModelAssembler=roomModelAssembler;
    }
    @GetMapping("rooms")
    CollectionModel<EntityModel<Room>>all(){
        List<EntityModel<Room>> rooms=roomRepository.findAll().stream()
                .map(roomModelAssembler::toModel)
                .collect(Collectors.toList());
                return CollectionModel.of(rooms,
                        linkTo(methodOn(RoomController.class).all()).withSelfRel());
    }
    @GetMapping("rooms/{id}")
    EntityModel<Room> one(@PathVariable Long id){
        Room room=roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
        return roomModelAssembler.toModel(room);
    }
}
