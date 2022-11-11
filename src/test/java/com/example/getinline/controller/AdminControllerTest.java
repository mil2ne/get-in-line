package com.example.getinline.controller;

import com.example.getinline.constant.EventStatus;
import com.example.getinline.constant.PlaceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    private final MockMvc mvc;

    public AdminControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[view][GET] 어드민페이지 - 장소 리스트 뷰")
    @Test
    void givenQueryParams_whenRequestingAdminPlacesPage_thenReturnAdminPlacePage() throws Exception {
        // Given

        // When & Then
        mvc.perform(
                        get("/admin/places")
                                .queryParam("placeType", PlaceType.SPORTS.name())
                                .queryParam("placeName", "밀리배드민턴장")
                                .queryParam("address", "서울시 강남구 청담대로 1234")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("admin/places"));
    }

    @DisplayName("[view][GET] 어드민 페이지 - 장소 세부정보 뷰")
    @Test
    void givenPlaceId_whenRequestingAdminDetailPage_thenReturnAdminDetailPage() throws Exception {
        // Given
        Long placeId = 1L;

        // When & Then
        mvc.perform(get("/admin/places/" + placeId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("admin/place-detail"));

    }

    @DisplayName("[view][GET] 어드민 페이지 - 이벤트 리스트 뷰")
    @Test
    void givenQueryParams_whenRequestAdminEventPage_thenReturnsAdminEventPage() throws Exception {
        // Given

        // When & Then
        mvc.perform(
                        get("/admin/events")
                                .contentType(MediaType.TEXT_HTML)
                                .queryParam("placeId", "1")
                                .queryParam("placeName", "밀리배드민턴장")
                                .queryParam("eventName", "오후 운동")
                                .queryParam("eventStatus", EventStatus.OPENED.name())
                                .queryParam("eventStartDateTime", LocalDateTime.now().minusDays(1).toString())
                                .queryParam("eventEndDateTime", LocalDateTime.now().toString())
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("admin/events"));
    }

    @DisplayName("[view][GET] 어드민 페이지 - 이벤트 세부 정보 뷰")
    @Test
    void givenEventId_whenRequestingEventDetailPage_thenReturnAdminDetailPage() throws Exception {

        // Given
        Long eventId = 1L;

        // When & Then
        mvc.perform(get("/admin/events/" + eventId ))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("admin/event-detail"));
    }
}