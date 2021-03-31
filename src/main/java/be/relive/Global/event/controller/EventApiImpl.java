package be.relive.Global.event.controller;

import be.relive.Global.event.domain.dto.EventDto;
import be.relive.Global.event.domain.dto.EventDtoConverter;
import be.relive.Global.event.domain.entity.Attendant;
import be.relive.Global.event.domain.entity.Event;
import be.relive.Global.event.domain.entity.EventConverter;
import be.relive.Global.event.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static be.relive.Global.event.domain.dto.EventPage.eventPage;
import static be.relive.Global.event.domain.entity.Attendant.attendant;
import static be.relive.Global.event.domain.entity.EventBuilder.modify;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class EventApiImpl implements EventApi {

    private final EventService eventService;

    private final EventDtoConverter eventDtoConverter;

    private final EventConverter eventConverter;

    @Autowired
    public EventApiImpl(EventService eventService, EventDtoConverter eventDtoConverter, EventConverter eventConverter) {
        this.eventService = eventService;
        this.eventDtoConverter = eventDtoConverter;
        this.eventConverter = eventConverter;
    }

    @Override
    public ResponseEntity update(UUID id, EventDto eventDto) {
        Event oldEvent = eventService.findById(id);
        List<Attendant> oldAttendants = oldEvent.getAttendants();
        List<Attendant> remainingAttendants = new ArrayList<>();
        if (eventDto.getAttendants() != null) {
            remainingAttendants = oldAttendants.stream()
                    .filter(attendant ->
                            eventDto.getAttendants().stream().anyMatch(attendantDto ->
                                    attendant.getUserId().equals(attendantDto.getUserId()) &&
                                            attendant.getCompleteName().equals(attendantDto.getCompleteName()) &&
                                            attendant.getImageUrl().equals(attendantDto.getImageUrl())
                            )
                    )
                    .collect(Collectors.toList());
        }


        Event event = modify(oldEvent)
                .withPlace(eventDto.getPlace())
                .withGroupKey(eventDto.getGroupKey())
                .withDescription(eventDto.getDescription())
                .withTitle(eventDto.getTitle())
                .withType(eventDto.getType())
                .withOccurrenceDate(eventDto.getOccurrenceDate())
                .withAttendants(remainingAttendants)
                .build();

        if (eventDto.getAttendants() != null) {
            List<Attendant> newAttendants = eventDto.getAttendants().stream()
                    .filter(attendantDto ->
                            event.getAttendants().stream().noneMatch(attendant -> attendant.getUserId().equals(attendantDto.getUserId()))
                    )
                    .map(attendantDto -> attendant(attendantDto.getUserId(), event, attendantDto.getCompleteName(), attendantDto.getImageUrl()))
                    .collect(Collectors.toList());

            event.getAttendants().addAll(newAttendants);
        }
        return ok().body(eventDtoConverter.convert(eventService.save(event)));
    }

    @Override
    public void delete(UUID id) {
        eventService.delete(id);
    }

    @Override
    public ResponseEntity save(EventDto eventDto) {
        return ok().body(eventDtoConverter.convert(eventService.save(eventConverter.convert(eventDto))));
    }

    @Override
    public ResponseEntity findByKey(String groupKey, Integer page, Integer size) {

        if (page == null && size == null) {
            List<EventDto> events = eventService.findAllByGroupKey(groupKey).stream()
                    .map(eventDtoConverter::convert)
                    .collect(Collectors.toList());

            return ok().body(eventPage(events));

        } else {
            if (page == null) {
                page = 0;
            }
            if (size == null) {
                size = 10;
            }
        }

        Pageable paging = PageRequest.of(page, size);

        Page<Event> eventPage = eventService.findAllByGroupKey(groupKey, paging);

        List<EventDto> events = eventPage.stream()
                .sorted(Comparator.comparing(Event::getOccurrenceDateTime))
                .map(eventDtoConverter::convert)
                .collect(Collectors.toList());

        return ok().body(eventPage(eventPage.getNumber(), eventPage.getTotalElements(), eventPage.getTotalPages(), events));
    }

    @Override
    public ResponseEntity findByAttendantId(UUID attendantId) {
        return ok().body(eventService.findAllByAttendantId(attendantId).stream()
                .map(eventDtoConverter::convert)
                .collect(Collectors.toList()));

    }
}
