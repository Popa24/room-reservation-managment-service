package com.example.demo.Room;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("rooms")
    ResponseEntity<?> newRoom(@RequestBody Room newRoom){
        EntityModel<Room> entityModel=roomModelAssembler.toModel(roomRepository.save(newRoom));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
    @GetMapping("rooms/{id}")
    EntityModel<Room> one(@PathVariable Long id){
        Room room=roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
        return roomModelAssembler.toModel(room);
    }
    @PutMapping("user/{id}")
    ResponseEntity<?>replaceRoom(@RequestBody Room newRoom, @PathVariable Long id){
        Room updateRoom=roomRepository.findById(id)
                .map( room -> {
                    room.setCapacity(newRoom.getCapacity());
                    room.setCity(newRoom.getCity());
                    room.setName(newRoom.getName());
                    room.setPrice(newRoom.getPrice());
                    room.setStreet(newRoom.getStreet());
                    room.setStreetNo(newRoom.getStreetNo());
                    return roomRepository.save(newRoom);
                })
                .orElseGet(()->{
                    newRoom.setId(id);
                    return roomRepository.save(newRoom);
                });
        EntityModel<Room>entityModel=roomModelAssembler.toModel(updateRoom);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
    @DeleteMapping("room/{id}")
    ResponseEntity<?>deleteRoom(@PathVariable Long id){
        roomRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
