package be.relive.Global.event.domain.dto;

import be.relive.Global.event.domain.entity.Event;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class EventDtoConverter implements Converter<Event, EventDto> {
    @Override
    public EventDto convert(Event event) {
        return new EventDto(event.getId(),
                event.getGroupKey(),
                event.getOccurrenceDateTime(),
                event.getPlace(),
                event.getDescription(),
                event.getTitle(),
                event.getType(), event.getAttendants().stream()
                        .map(attendant -> new AttendantDto(attendant.getUserId(), attendant.getCompleteName(), attendant.getImageUrl()))
                        .collect(Collectors.toList())
        );
    }
}
