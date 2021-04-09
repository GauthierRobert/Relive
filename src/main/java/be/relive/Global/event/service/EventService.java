package be.relive.Global.event.service;

import be.relive.Global.dto.Notification;
import be.relive.Global.event.domain.entity.Attendant;
import be.relive.Global.event.domain.entity.Event;
import be.relive.Global.event.exception.EventNotFoundException;
import be.relive.Global.event.repository.EventRepository;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    @Qualifier("notificationExchange")
    private final TopicExchange notificationExchange;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public EventService(EventRepository eventRepository, TopicExchange notificationExchange, RabbitTemplate rabbitTemplate) {
        this.eventRepository = eventRepository;
        this.notificationExchange = notificationExchange;
        this.rabbitTemplate = rabbitTemplate;
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
        return eventRepository.findAllByAttendantId(attendantId).stream()
                .sorted(Comparator.comparing(event -> -event.getOccurrenceDateTime().toInstant(ZoneOffset.UTC).toEpochMilli()))
                .collect(Collectors.toList());
    }

    @Transactional
    public Event save(Event event) {
        Notification notification = new Notification("A new event has been created", event.getTitle());
        notification.getAdditionalProperties().put("attendants", event.getAttendants());
        Event savedEvent = eventRepository.save(event);
        String routingKey = "event.creation." + savedEvent.getGroupKey();
        if(!event.getAttendants().isEmpty()){
            routingKey = routingKey + event.getAttendants().stream()
                    .map(Attendant::getUserId)
                    .map(UUID::toString)
                    .collect(Collectors.joining("."));
        }
        rabbitTemplate.convertAndSend(notificationExchange.getName(), routingKey, notification);
        return savedEvent;
    }

    public Event findById(UUID id) {
        return eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
    }

    public void delete(UUID id) {
        eventRepository.deleteById(id);
    }


}
