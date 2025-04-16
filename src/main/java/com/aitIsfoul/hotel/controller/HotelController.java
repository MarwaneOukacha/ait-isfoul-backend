package com.aitIsfoul.hotel.controller;

import com.aitIsfoul.hotel.entity.dto.request.AddHotelRequestDTO;
import com.aitIsfoul.hotel.entity.dto.request.UpdateHotelRequestDTO;
import com.aitIsfoul.hotel.entity.dto.response.AddHotelResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.UpdateHotelResponseDTO;
import com.aitIsfoul.hotel.services.HotelService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @PostMapping("/add")
    public ResponseEntity<AddHotelResponseDTO> addHotel(@Valid @RequestBody AddHotelRequestDTO addHotelRequestDTO){
        log.info("HotelController :: addHotel {}", addHotelRequestDTO);
        return ResponseEntity.ok(hotelService.addHotel(addHotelRequestDTO));
    }

    @PatchMapping("/update")
    public ResponseEntity<UpdateHotelResponseDTO> updadteHotel(@Valid @RequestBody UpdateHotelRequestDTO updateHotelRequestDTO){
        log.info("HotelController :: updadteHotel {}", updateHotelRequestDTO);
        return ResponseEntity.ok(hotelService.updateHotel(updateHotelRequestDTO));
    }
}
