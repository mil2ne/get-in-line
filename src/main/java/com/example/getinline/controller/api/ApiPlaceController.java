package com.example.getinline.controller.api;

import com.example.getinline.constant.PlaceType;
import com.example.getinline.dto.ApiDataResponse;
import com.example.getinline.dto.PlaceDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api")
@RestController
public class ApiPlaceController {

    @GetMapping("/places")
    public ApiDataResponse<List<PlaceDTO>> getPlaces() {
        return ApiDataResponse.of(List.of(PlaceDTO.of(
                PlaceType.COMMON,
                "밀리배드민턴장",
                "서울시 강남구 청담대로 1234",
                "010-1234-5678",
                30,
                "신장개업",
                LocalDateTime.now(),
                LocalDateTime.now()
        )));
    }

    @PostMapping("/places")
    public Boolean createPlace() {
        return true;
    }

    @GetMapping("/places/{placeId}")
    public ApiDataResponse<PlaceDTO> getPlace(@PathVariable Integer placeId) {
        if (placeId.equals(2)) {
            return ApiDataResponse.of(null);
        }


        return ApiDataResponse.of(PlaceDTO.of(
                PlaceType.COMMON,
                "밀리배드민턴장",
                "서울시 강남구 청담대로 1234",
                "010-1234-5678",
                30,
                "신장개업",
                LocalDateTime.now(),
                LocalDateTime.now()
        ));
    }

    @PutMapping("/places/{placeId}")
    public Boolean modifyPlace(@PathVariable Integer placeId) {
        return true;
    }

    @DeleteMapping("/places/{placeId}")
    public Boolean deletePlace(@PathVariable Integer placeId) {
        return true;
    }
}
