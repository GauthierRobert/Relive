package be.relive.Global.event.domain.entity;

import be.relive.Global.event.domain.dto.EventDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

import static be.relive.Global.event.domain.entity.Attendant.attendant;

@Service
public class EventConverter implements Converter<EventDto, Event> {

    @Override
    public Event convert(EventDto event) {
        EventBuilder eventBuilder = EventBuilder.anEvent()
                .withTitle(event.getTitle())
                .withOccurrenceDate(event.getOccurrenceDate())
                .withDescription(event.getDescription())
                .withGroupKey(event.getGroupKey())
                .withPlace(event.getPlace());

        if (event.getId() != null) {
            eventBuilder.withId(event.getId());
        }
        Event eventEntity = eventBuilder.build();

        eventEntity.getAttendants().addAll(event.getAttendants().stream()
                .map(attendantDto -> attendant(attendantDto.getUserId(), eventEntity, attendantDto.getCompleteName(), attendantDto.getImageUrl()))
                .collect(Collectors.toSet()));
        return eventEntity;
    }

    public Event convert(EventDto event, UUID id) {
        EventBuilder eventBuilder = EventBuilder.anEvent()
                .withTitle(event.getTitle())
                .withOccurrenceDate(event.getOccurrenceDate())
                .withDescription(event.getDescription())
                .withGroupKey(event.getGroupKey())
                .withPlace(event.getPlace())
                .withId(id);

        Event eventEntity = eventBuilder.build();

        eventEntity.getAttendants().addAll(event.getAttendants().stream()
                .map(attendantDto -> attendant(attendantDto.getUserId(), eventEntity, attendantDto.getCompleteName(), attendantDto.getImageUrl()))
                .collect(Collectors.toSet()));
        return eventEntity;
    }
}
