package com.example.getinline.controller.api;

import com.example.getinline.constant.PlaceType;
import com.example.getinline.dto.ApiDataResponse;
import com.example.getinline.dto.PlaceDTO;
import com.example.getinline.dto.PlaceRequest;
import com.example.getinline.dto.PlaceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

//@RequestMapping("/api")
//@RestController
public class ApiPlaceController {

    @GetMapping("/places")
    public ApiDataResponse<List<PlaceResponse>> getPlaces() {
        return ApiDataResponse.of(List.of(PlaceResponse.of(
                PlaceType.COMMON,
                "밀리배드민턴장",
                "서울시 강남구 청담대로 1234",
                "010-1234-5678",
                30,
                "신장개업"
        )));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/places")
    public ApiDataResponse<Void> createPlace(@RequestBody PlaceRequest placeRequest) {
        return ApiDataResponse.empty();
    }

    @GetMapping("/places/{placeId}")
    public ApiDataResponse<PlaceResponse> getPlace(@PathVariable Long placeId) {
        if (placeId.equals(2L)) {
            return ApiDataResponse.of(null);
        }

        return ApiDataResponse.of(PlaceResponse.of(
                PlaceType.COMMON,
                "밀리배드민턴장",
                "서울시 강남구 청담대로 1234",
                "010-1234-5678",
                30,
                "신장개업"
        ));
    }

    @PutMapping("/places/{placeId}")
    public ApiDataResponse<Void> modifyPlace(@PathVariable Long placeId) {
        return ApiDataResponse.empty();
    }

    @DeleteMapping("/places/{placeId}")
    public ApiDataResponse<Void> deletePlace(@PathVariable Long placeId) {
        return ApiDataResponse.empty();
    }
}
