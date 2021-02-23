package be.relive.Global.event.service;

import be.relive.Global.event.domain.entity.Event;
import be.relive.Global.event.exception.EventNotFoundException;
import be.relive.Global.event.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


    public Page<Event> findAllByGroupKey(String key, Pageable pageable) {
        return eventRepository.findAllByGroupKey(key, pageable);
    }

    public List<Event> findAllByGroupKey(String key) {
        return eventRepository.findAllByGroupKey(key).stream()
                .sorted(Comparator.comparing(Event::getOccurrenceDateTime))
                .collect(Collectors.toList());
    }

    public List<Event> findAllByAttendantId(UUID attendantId) {
        return eventRepository.findAllByAttendantId(attendantId);
    }

    @Transactional
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public Event findById(UUID id){
        return eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
    }

    public void delete(UUID id){
        eventRepository.deleteById(id);
    }






}
