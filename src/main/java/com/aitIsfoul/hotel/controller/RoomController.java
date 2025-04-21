package com.aitIsfoul.hotel.controller;

import com.aitIsfoul.hotel.entity.dto.GenericPage;
import com.aitIsfoul.hotel.entity.dto.SearchRoomDTO;
import com.aitIsfoul.hotel.entity.dto.request.AddRoomRequestDTO;
import com.aitIsfoul.hotel.entity.dto.request.UpdateRoomRequestDTO;
import com.aitIsfoul.hotel.entity.dto.response.AddRoomResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.SearchRoomResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.UpdateRoomResponseDTO;
import com.aitIsfoul.hotel.enums.RoomStatus;
import com.aitIsfoul.hotel.services.RoomService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/search")
    public ResponseEntity<GenericPage<SearchRoomResponseDTO>> getRooms(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) RoomStatus roomStatus,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer maxPeople,
            Pageable pageable) {

        log.info("Received request to search rooms with keyword: {}, roomStatus: {}, price between {} and {}, maxPeople: {}",
                keyword, roomStatus, minPrice, maxPrice, maxPeople);

        SearchRoomDTO searchRoomDTO = SearchRoomDTO.builder()
                .roomStatus(roomStatus)
                .keyword(keyword)
                .maxPeople(maxPeople)
                .maxPrice(maxPrice)
                .minPrice(minPrice)
                .build();

        GenericPage<SearchRoomResponseDTO> rooms = roomService.getRooms(searchRoomDTO, pageable);

        log.info("Returning {} rooms.", rooms.getPage().getContent().size());

        return ResponseEntity.ok(rooms);
    }

    @PostMapping("/add")
    public ResponseEntity<AddRoomResponseDTO> addRoom(@RequestBody @Valid AddRoomRequestDTO addRoomRequestDTO) {
        log.info("Received request to add a new room: {}", addRoomRequestDTO);

        AddRoomResponseDTO response = roomService.addRoom(addRoomRequestDTO);

        log.info("New room created successfully with title: {}", response.getTitle());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/update")
    public ResponseEntity<UpdateRoomResponseDTO> updateRoom(@RequestBody UpdateRoomRequestDTO updateRoom) {
        log.info("Received request to update room with ID: {}", updateRoom.getId());

        UpdateRoomResponseDTO response = roomService.updateRoom(updateRoom);

        log.info("Room updated successfully with ID: {}", response.getId());

        return ResponseEntity.ok(response);
    }
}
