package com.example.getinline.controller.api;

import com.example.getinline.constant.ErrorCode;
import com.example.getinline.constant.PlaceType;
import com.example.getinline.dto.PlaceRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled("API 컨트롤러가 필요없는 상황이어서 비활성화")
@WebMvcTest(ApiPlaceController.class)
class ApiPlaceControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper mapper;

    public ApiPlaceControllerTest(
            @Autowired MockMvc mvc,
            @Autowired ObjectMapper mapper
            ) {
        this.mvc = mvc;
        this.mapper = mapper;
    }

    @Test
    void 플레이스_컨트롤러_정상조회_테스트() throws Exception{
        // Given

        // When & Then
        mvc.perform(get("/api/places"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].placeType").value(PlaceType.COMMON.name()))
                .andExpect(jsonPath("$.data[0].placeName").value("밀리배드민턴장"))
                .andExpect(jsonPath("$.data[0].address").value("서울시 강남구 청담대로 1234"))
                .andExpect(jsonPath("$.data[0].phoneNumber").value("010-1234-5678"))
                .andExpect(jsonPath("$.data[0].capacity").value(30))
                .andExpect(jsonPath("$.data[0].memo").value("신장개업"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    @DisplayName("[API][POST] 장소 생성")
    @Test
    void givenPlace_whenCreatingAPlace_thenReturnsSuccessfulStandardResponse() throws Exception {
        // Given
        PlaceRequest placeRequest = PlaceRequest.of(
                PlaceType.COMMON,
                "밀리배드민턴장",
                "서울시 강남구 청담대로 1234",
                "010-1234-5678",
                30,
                "신장개업"
        );

        // When & Then
        mvc.perform(
                post("/api/places")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(placeRequest))
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    @Test
    void 단일플레이스_컨트롤러_정상조회_테스트() throws Exception{
        // Given
        Long placeId = 1L;

        // When & Then
        mvc.perform(get("/api/places/" + placeId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.placeType").value(PlaceType.COMMON.name()))
                .andExpect(jsonPath("$.data.placeName").value("밀리배드민턴장"))
                .andExpect(jsonPath("$.data.address").value("서울시 강남구 청담대로 1234"))
                .andExpect(jsonPath("$.data.phoneNumber").value("010-1234-5678"))
                .andExpect(jsonPath("$.data.capacity").value(30))
                .andExpect(jsonPath("$.data.memo").value("신장개업"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    @Test
    void 단일플레이스_컨트롤러_실패_테스트() throws Exception{
        // Given
        Long placeId = 2L;

        // When & Then
        mvc.perform(get("/api/places/" + placeId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    @DisplayName("[API][PUT] 장소 변경")
    @Test
    void givenPlace_whenModifyingPlace_thenReturnsSuccessfulStandardResponse() throws Exception {
        // Given
        Long placeId = 1l;
        PlaceRequest placeRequest = PlaceRequest.of(
                PlaceType.COMMON,
                "밀리배드민턴장",
                "서울시 강남구 청담대로 1234",
                "010-1234-5678",
                30,
                "신장개업"
        );

        // When & Then
        mvc.perform(
                put("/api/places/" + placeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(placeRequest))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    @DisplayName("[API][DELETE] 장소 삭제")
    @Test
    void givenPlace_whenDeletingAPlace_thenReturnSuccessfulStandardResponse() throws Exception {
        // Given
        Long placeId = 1L;

        // When & Then
        mvc.perform(delete("/api/places/" + placeId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

}