package com.example.getinline.dto;

import com.example.getinline.constant.EventStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

public record EventRequest(
        @NotNull @Positive Long placeId,
        @NotBlank String eventName,
        @NotNull EventStatus eventStatus,
        @NotNull LocalDateTime eventStartDateTime,
        @NotNull LocalDateTime eventEndDateTime,
        @NotNull @PositiveOrZero Integer currentNumberOfPeople,
        @NotNull Integer capacity,
        String memo

) {
    public static EventRequest of (
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo
    ) {
        return new EventRequest(
                placeId, eventName,
                eventStatus, eventStartDateTime,
                eventEndDateTime, currentNumberOfPeople, capacity, memo);
    }

    public EventDTO toDTO() {
        return EventDTO.of(
                null,
                this.placeId,
                this.eventName,
                this.eventStatus,
                this.eventStartDateTime,
                this.eventEndDateTime,
                this.currentNumberOfPeople,
                this.capacity,
                this.memo,
                null,
                null
        );
    }
}
