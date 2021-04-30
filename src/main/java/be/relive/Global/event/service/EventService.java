package be.relive.Global.event.service;

import be.relive.Global.dto.Notification;
import be.relive.Global.event.domain.entity.Attendant;
import be.relive.Global.event.domain.entity.Event;
import be.relive.Global.event.exception.EventNotFoundException;
import be.relive.Global.event.repository.EventRepository;
import be.relive.Global.event.service.notification.ExpoNotificationService;
import be.relive.Global.user.repository.UserRepository;
import io.github.jav.exposerversdk.PushClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class EventService {

    private final EventRepository eventRepository;

    private final UserRepository userRepository;

    private final ExpoNotificationService expoNotificationService;
    @Autowired
    public EventService(EventRepository eventRepository, UserRepository userRepository, ExpoNotificationService expoNotificationService) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.expoNotificationService = expoNotificationService;
    }


    public Page<Event> findAllByGroupKey(String key, Pageable pageable) {
        return eventRepository.findAllByGroupKey(key, pageable);
    }

    public List<Event> findAllByGroupKey(String key) {
        return eventRepository.findAllByGroupKey(key).stream()
                .sorted(Comparator.comparing(Event::getOccurrenceDateTime))
                .collect(toList());
    }

    public List<Event> findAllByAttendantId(UUID attendantId) {
        return eventRepository.findAllByAttendantId(attendantId).stream()
                .sorted(Comparator.comparing(event -> -event.getOccurrenceDateTime().toInstant(ZoneOffset.UTC).toEpochMilli()))
                .collect(toList());
    }

    @Transactional
    public Event save(Event event) throws PushClientException {

        Event savedEvent = eventRepository.save(event);

        if(event.getId()!=null) {
            List<String> tokens = userRepository.getNotificationTokens(event.getAttendants().stream()
                    .map(Attendant::getUserId)
                    .collect(toList()));
            if(tokens!=null) {
                List<String> notificationTokens = tokens.stream().filter(Objects::nonNull).collect(toList());
                expoNotificationService.sendPushNotification(notificationTokens, "A new event has been created", event.getTitle());
            }
        }

        return savedEvent;
    }

    public Event findById(UUID id) {
        return eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
    }

    public void delete(UUID id) {
        eventRepository.deleteById(id);
    }


}
