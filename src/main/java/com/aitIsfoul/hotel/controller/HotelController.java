package com.aitIsfoul.hotel.controller;

import com.aitIsfoul.hotel.entity.dto.GenericPage;
import com.aitIsfoul.hotel.entity.dto.request.AddHotelRequestDTO;
import com.aitIsfoul.hotel.entity.dto.request.SearchHotelCriteriaDTO;
import com.aitIsfoul.hotel.entity.dto.request.UpdateHotelRequestDTO;
import com.aitIsfoul.hotel.entity.dto.response.AddHotelResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.SearchHotelResponseDTO;
import com.aitIsfoul.hotel.entity.dto.response.UpdateHotelResponseDTO;
import com.aitIsfoul.hotel.services.HotelService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @GetMapping("/search")
    @Transactional
    public ResponseEntity<GenericPage<SearchHotelResponseDTO>> searchHotels(@RequestParam(required = false) String keyword,
                                                                            @RequestParam(required = false) Integer stars,
                                                                            @RequestParam(required = false) String ownerIden
            , Pageable pageable
    ){

        SearchHotelCriteriaDTO searchHotelCriteriaDTO=new SearchHotelCriteriaDTO(keyword,stars,ownerIden);
        log.info("HotelController :: searchHotels {}", searchHotelCriteriaDTO);
        GenericPage<SearchHotelResponseDTO> allHotels = hotelService.getAllHotels(searchHotelCriteriaDTO, pageable);
        log.info("Search users response {}", allHotels.getPage().getContent());
        return ResponseEntity.ok(allHotels);
    }

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
