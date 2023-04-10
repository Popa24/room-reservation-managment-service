package com.example.demo.room.controller;

import com.example.demo.room.service.AllRoomInformationDto;
import com.example.demo.room.service.RoomDomainObject;
import com.example.demo.room.service.RoomService;
import com.example.demo.room.service.TopRented;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/room")
public class RoomController {


    private final RoomService roomService;

    public RoomController(@NonNull final RoomService roomService) {
        this.roomService = roomService;

    }

    @GetMapping
    public ResponseEntity<List<JsonRoomDomainResponse>> getAllRooms() {
        List<RoomDomainObject> roomDomainObjects = roomService.getAllRooms();
        List<JsonRoomDomainResponse> jsonResponse = roomDomainObjects.stream().map(JsonRoomDomainResponse::toJson).collect(Collectors.toList());
        return ResponseEntity.ok().body(jsonResponse);
    }
    @GetMapping("/top-rented")
    public ResponseEntity<List<JsonTopRentedResponse>> getTopRentedRooms(@RequestParam(required = false) Integer top, @RequestParam(required = false) Integer minGeneratedRevenue) {
        List<TopRented>room= roomService.getTopRentedRooms(top, minGeneratedRevenue);
        List<JsonTopRentedResponse>jsonResponse=room.stream().map(JsonTopRentedResponse::toJson).toList();
        return ResponseEntity.ok().body(jsonResponse);
    }

    @GetMapping("/all-room-info/{roomId}")
    public ResponseEntity<JsonAllRoomInformationDtoResponse> getAllRoomInfo(@PathVariable Long roomId) {
        AllRoomInformationDto allRoomInformationDto = roomService.buildAllRoomInformationDto(roomId);
        JsonAllRoomInformationDtoResponse response = JsonAllRoomInformationDtoResponse.toJson(allRoomInformationDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<JsonRoomDomainResponse> newRoom(@RequestBody @NonNull final JsonUpsertRoomDomainRequest request) {
        final RoomDomainObject roomDomainObject = roomService.save(RoomControllerHelper.toCreateRoomRequest(request));
        return ResponseEntity.ok().body(JsonRoomDomainResponse.toJson(roomDomainObject));
    }
    @PostMapping
    public ResponseEntity<JsonRoomDomainResponse> registerRoom(@RequestBody @NonNull final JsonUpsertRoomDomainRequest request) {
        if (roomService.existsByNameAndLocation(request.getName(), request.getCity(),request.getStreet(),request.getStreetNo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            final RoomDomainObject roomDomainObject = roomService.save(RoomControllerHelper.toCreateRoomRequest(request));
            return ResponseEntity.ok().body(JsonRoomDomainResponse.toJson(roomDomainObject));
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<JsonRoomDomainResponse> updateRoom(@RequestBody @NonNull final JsonUpsertRoomDomainRequest request, @PathVariable Long id) {
        final RoomDomainObject roomDomainObject = roomService.update(RoomControllerHelper.toRoomDomainObject(request, id));
        return ResponseEntity.ok().body(JsonRoomDomainResponse.toJson(roomDomainObject));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}