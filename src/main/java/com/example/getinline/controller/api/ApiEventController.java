package com.example.getinline.controller.api;

import com.example.getinline.constant.EventStatus;
import com.example.getinline.dto.ApiDataResponse;
import com.example.getinline.dto.EventRequest;
import com.example.getinline.dto.EventResponse;
import com.example.getinline.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Deprecated
//@Validated
@RequiredArgsConstructor
//@RequestMapping("/api")
//@RestController
public class ApiEventController {

    private final EventService eventService;

    @GetMapping("/events")
    public ApiDataResponse<List<EventResponse>> getEvents(
            @Positive Long placeId,
            @Size(min = 2) String eventName,
            EventStatus eventStatus,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventStartDateTime,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventEndDateTime
    ) {
        List<EventResponse> response = eventService
                .getEvents(placeId, eventName, eventStatus, eventStartDateTime, eventEndDateTime)
                .stream().map(EventResponse::from).toList();


        return ApiDataResponse.of(response);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/events")
    public ApiDataResponse<String> createEvent(@Valid @RequestBody EventRequest eventRequest) {
        boolean result = eventService.createEvent(eventRequest.toDTO());

        return ApiDataResponse.of(Boolean.toString(result));
    }

    @GetMapping("/events/{eventId}")
    public ApiDataResponse<EventResponse> getEvent(@PathVariable Long eventId) {
        EventResponse eventResponse = EventResponse.from(eventService.getEvent(eventId).orElse(null));
        return ApiDataResponse.of(eventResponse);
    }

    @PutMapping("/events/{eventId}")
    public ApiDataResponse<String> modifyEvent(
            @PathVariable Long eventId,
            @RequestBody EventRequest eventRequest
    ) {
        boolean result = eventService.modifyEvent(eventId, eventRequest.toDTO());
        return ApiDataResponse.of(Boolean.toString(result));
    }

    @DeleteMapping("/events/{eventId}")
    public ApiDataResponse<String> removeEvent(@PathVariable Long eventId) {
        boolean result = eventService.removeEvent(eventId);
        return ApiDataResponse.of(Boolean.toString(result));
    }
}
