package com.example.getinline.controller.api;

import com.example.getinline.constant.ErrorCode;
import com.example.getinline.constant.EventStatus;
import com.example.getinline.dto.EventDTO;
import com.example.getinline.dto.EventRequest;
import com.example.getinline.dto.EventResponse;
import com.example.getinline.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ApiEventController.class)
class ApiEventControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper mapper;

    @MockBean
    private EventService eventService;

    public ApiEventControllerTest(
            @Autowired MockMvc mvc,
            @Autowired ObjectMapper mapper
            ) {
        this.mvc = mvc;
        this.mapper = mapper;
    }
    
    
    @DisplayName("[API][GET] 이벤트 리스트 조회")
    @Test
    void givenParams_whenRequestingEvents_thenReturnsListOfEventsInStandardResponse() throws Exception {
        // Given
        given(eventService.getEvents(any(),any(),any(),any(),any()))
                .willReturn(List.of(createEventDTO()));
        
        // When & Then
        mvc.perform(get("/api/events")
                        .queryParam("placeId","1")
                        .queryParam("eventName","운동")
                        .queryParam("eventStatus",EventStatus.OPENED.name())
                        .queryParam("eventStartDateTime","2021-01-01T00:00:00")
                        .queryParam("eventEndDateTime","2021-01-02T00:00:00")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].placeId").value(1L))
                .andExpect(jsonPath("$.data[0].eventName").value("오후 운동"))
                .andExpect(jsonPath("$.data[0].eventStatus").value(EventStatus.OPENED.name()))
                .andExpect(jsonPath("$.data[0].eventStartDateTime").value(LocalDateTime
                        .of(2021,1,1,13,0,0)
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(jsonPath("$.data[0].eventEndDateTime").value(LocalDateTime
                        .of(2021,1,1,16,0,0)
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(jsonPath("$.data[0].currentNumberOfPeople").value(0))
                .andExpect(jsonPath("$.data[0].capacity").value(24))
                .andExpect(jsonPath("$.data[0].memo").value("마스크 꼭 착용하세요"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
        then(eventService).should().getEvents(any(),any(),any(),any(),any());
    }

    @DisplayName("[API][GET] 이벤트 리스트 조회 - 잘못된 검색 파라미터")
    @Test
    void givenNothingParams_whenRequestingEvents_thenReturnsFailedStandardResponse() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/api/events")
                        .queryParam("placeId","0")
                        .queryParam("eventName","운")
                        .queryParam("eventStatus",EventStatus.OPENED.name())
                        .queryParam("eventStartDateTime","2021-01-01T00:00:00")
                        .queryParam("eventEndDateTime","2021-01-02T00:00:00")
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.VALIDATION_ERROR.getCode()))
                .andExpect(jsonPath("$.message").value(containsString(ErrorCode.VALIDATION_ERROR.getMessage())));
        then(eventService).shouldHaveNoInteractions();
    }

    @DisplayName("[API][POST] 이벤트 생성")
    @Test
    void givenEvent_whenCreatingAnEvent_thenReturnsSuccessfulStandardResponse() throws Exception {
        // Given
        EventRequest eventRequest = EventRequest.of(
                1L,
                "오후 운동",
                EventStatus.OPENED,
                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                LocalDateTime.of(2021, 1, 1, 16, 0, 0),
                0,
                24,
                "마스크 꼭 착용하세요"
        );
        given(eventService.createEvent(any())).willReturn(true);

        // When & Then
        mvc.perform(
                post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(eventRequest))
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").value(Boolean.TRUE.toString()))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
        then(eventService).should().createEvent(any());
    }

    @DisplayName("[API][POST] 이벤트 생성 - 잘못된 데이터 입력")
    @Test
    void givenWrongEvent_whenCreatingAnEvent_thenReturnsSuccessfulStandardResponse() throws Exception {
        // Given
        EventRequest eventRequest = EventRequest.of(
                -1L,
                "  ",
                null,
                null,
                null,
                -1,
                0,
                "마스크 꼭 착용하세요"
        );

        // When & Then
        mvc.perform(
                        post("/api/events")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(eventRequest))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.SPRING_BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.message").value(containsString(ErrorCode.SPRING_BAD_REQUEST.getMessage())));
        then(eventService).shouldHaveNoInteractions();
    }


    @DisplayName("[API][GET] 단일 이벤트 조회 - 이벤트 존재하는 경우 , 이벤트 데이터를 담은 표준 API 출력")
    @Test
    void givenEventId_whenRequestingExistentEvent_thenReturnEventInStandardResponse() throws Exception {
        // Given
        Long eventId = 1L;
        given(eventService.getEvent(eventId)).willReturn(Optional.of(createEventDTO()));

        // When & Then
        mvc.perform(get("/api/events/" + eventId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.placeId").value(1L))
                .andExpect(jsonPath("$.data.eventName").value("오후 운동"))
                .andExpect(jsonPath("$.data.eventStatus").value(EventStatus.OPENED.name()))
                .andExpect(jsonPath("$.data.eventStartDateTime").value(LocalDateTime
                        .of(2021,1,1,13,0,0)
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(jsonPath("$.data.eventEndDateTime").value(LocalDateTime
                        .of(2021,1,1,16,0,0)
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(jsonPath("$.data.currentNumberOfPeople").value(0))
                .andExpect(jsonPath("$.data.capacity").value(24))
                .andExpect(jsonPath("$.data.memo").value("마스크 꼭 착용하세요"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));

        then(eventService).should().getEvent(eventId);

    }

    @DisplayName("[API][GET] 단일 이벤트 조회 - 이벤트 존재하지 않는 경우 , 빈 표준 API 출력")
    @Test
    void givenEventId_whenRequestingNonExistentEvent_thenReturnEmptyStandardResponse() throws Exception {
        Long eventId = 2L;
        given(eventService.getEvent(eventId)).willReturn(Optional.empty());

        mvc.perform(get("/api/events/" + eventId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
        then(eventService).should().getEvent(eventId);
    }

    @DisplayName("[API][PUT] 이벤트 변경")
    @Test
    void givenEvent_whenModifyAndEvent_thenReturnSuceessfulStandardResponse() throws Exception {
        //given
        Long eventId = 1L;
        EventRequest eventRequest = EventRequest.of(
                1L,
                "오전 운동",
                EventStatus.OPENED,
                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                LocalDateTime.of(2021, 1, 1, 16, 0, 0),
                0,
                24,
                "마스크를 꼭 착용하세요"
        );
        given(eventService.modifyEvent(eq(eventId), any())).willReturn(true);

        // when & then
        mvc.perform(
                put("/api/events/" + eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(eventRequest))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").value(Boolean.TRUE.toString()))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
        then(eventService).should().modifyEvent(eq(eventId),any());
    }

    @DisplayName("[API][DELETE] 이벤트 삭제")
    @Test
    void givenEventId_whenDeletingAnEvent_thenReturnSuccessfulStandardResponse() throws Exception {
        // Given
        Long eventId = 1L;
        given(eventService.removeEvent(eventId)).willReturn(true);

        // When & Then
        mvc.perform(delete("/api/events/" + eventId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));

        then(eventService).should().removeEvent(eventId);
    }


    private EventDTO createEventDTO() {
        return EventDTO.of(
                1L,
                1L,
                "오후 운동",
                EventStatus.OPENED,
                LocalDateTime.of(2021,1,1,13,0,0),
                LocalDateTime.of(2021,1,1,16,0,0),
                0,
                24,
                "마스크 꼭 착용하세요",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

    }


 }