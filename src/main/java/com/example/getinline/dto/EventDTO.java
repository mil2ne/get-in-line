package com.example.getinline.dto;

import com.example.getinline.constant.EventStatus;
import com.example.getinline.constant.PlaceType;

import java.time.LocalDateTime;

public record EventDTO(
        Long id,
        Long placeId,
        String eventName,
        EventStatus eventStatus,
        LocalDateTime eventStartDateTime,
        LocalDateTime eventEndDateTime,
        Integer currentNumberOfPeople,
        Integer capacity,
        String memo,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt

) {
    public static EventDTO of (
            Long id,
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        return new EventDTO(
                id,
                placeId, eventName,eventStatus,
                eventStartDateTime, eventEndDateTime,
                currentNumberOfPeople,
                capacity, memo, createdAt, modifiedAt);
    }
}
