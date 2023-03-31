package com.example.demo.room.controller;

import com.example.demo.room.service.RoomDomainObject;
import com.example.demo.room.service.RoomService;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class RoomController {


    private final RoomService roomService;

    public RoomController(@NonNull final RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/api/rooms")
    public ResponseEntity<List<JsonRoomDomainResponse>> getAllRooms() {
        List<RoomDomainObject> roomDomainObjects = roomService.getAllRooms();
        List<JsonRoomDomainResponse> jsonResponse = roomDomainObjects.stream().map(JsonRoomDomainResponse::toJson).collect(Collectors.toList());
        return ResponseEntity.ok().body(jsonResponse);
    }

    @PostMapping("/api/create/room")
    public ResponseEntity<JsonRoomDomainResponse> newRoom(@RequestBody @NonNull final JsonUpsertRoomDomainRequest request) {
        final RoomDomainObject roomDomainObject = roomService.save(RoomControllerHelper.toCreateRoomRequest(request));
        return ResponseEntity.ok().body(JsonRoomDomainResponse.toJson(roomDomainObject));
    }
    @PostMapping("/api/room")
    public ResponseEntity<JsonRoomDomainResponse> registerRoom(@RequestBody @NonNull final JsonUpsertRoomDomainRequest request) {
        if (roomService.existsByNameAndLocation(request.getName(), request.getCity(),request.getStreet(),request.getStreetNo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            final RoomDomainObject roomDomainObject = roomService.save(RoomControllerHelper.toCreateRoomRequest(request));
            return ResponseEntity.ok().body(JsonRoomDomainResponse.toJson(roomDomainObject));
        }
    }
    @PutMapping("/api/room/{id}")
    public ResponseEntity<JsonRoomDomainResponse> updateRoom(@RequestBody @NonNull final JsonUpsertRoomDomainRequest request, @PathVariable Long id) {
        final RoomDomainObject roomDomainObject = roomService.update(RoomControllerHelper.toRoomDomainObject(request, id));
        return ResponseEntity.ok().body(JsonRoomDomainResponse.toJson(roomDomainObject));
    }

    @DeleteMapping("/api/room/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}