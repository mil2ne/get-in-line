package com.example.getinline.dto;

import com.example.getinline.constant.EventStatus;

import java.time.LocalDateTime;

public record EventResponse(
        Long id,
        Long placeId,
        String eventName,
        EventStatus eventStatus,
        LocalDateTime eventStartDateTime,
        LocalDateTime eventEndDateTime,
        Integer currentNumberOfPeople,
        Integer capacity,
        String memo

) {
    public static EventResponse of (
            Long id,
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo
    ) {
        return new EventResponse(
                id,placeId, eventName,
                eventStatus, eventStartDateTime,
                eventEndDateTime, currentNumberOfPeople, capacity, memo);
    }

    public static EventResponse from(EventDTO eventDTO) {
        if (eventDTO == null) {return null;}
        return EventResponse.of(
                eventDTO.id(),
                eventDTO.placeId(),
                eventDTO.eventName(),
                eventDTO.eventStatus(),
                eventDTO.eventStartDateTime(),
                eventDTO.eventEndDateTime(),
                eventDTO.currentNumberOfPeople(),
                eventDTO.capacity(),
                eventDTO.memo()
        );
    }
}
