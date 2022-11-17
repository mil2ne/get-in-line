package com.example.getinline.service;

import com.example.getinline.constant.ErrorCode;
import com.example.getinline.constant.EventStatus;
import com.example.getinline.dto.EventDTO;
import com.example.getinline.exception.GeneralException;
import com.example.getinline.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;

    public List<EventDTO> getEvents(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime
    ) {
        try {
            return eventRepository.findEvents(placeId, eventName, eventStatus, eventStartDateTime, eventEndDateTime);
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR,e);
        }
    }

    public Optional<EventDTO> getEvent(Long eventId) {
        try {
            return eventRepository.findEvent(eventId);
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR,e);
        }
    }

    public boolean createEvent(EventDTO eventDTO) {
        try {
            return eventRepository.insertEvent(eventDTO);
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR,e);
        }
    }

    public boolean modifyEvent(Long eventId, EventDTO eventDTO) {
        try {
            return eventRepository.updateEvent(eventId, eventDTO);
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR,e);
        }
    }

    public boolean removeEvent(Long eventId) {
        try {
            return eventRepository.deleteEvent(eventId);
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR,e);
        }
    }
}
