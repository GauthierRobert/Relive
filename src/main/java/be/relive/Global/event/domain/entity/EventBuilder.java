package be.relive.Global.event.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class EventBuilder {
    private String groupKey;
    private LocalDateTime occurrenceDate = LocalDateTime.now();
    private String place;
    private String description;
    private String title;
    private UUID id;
    private List<Attendant> attendants = new ArrayList<>();

    private EventBuilder() {
    }

    public static EventBuilder anEvent() {
        return new EventBuilder();
    }

    public static EventBuilder modify(Event event) {
        return new EventBuilder()
                .withId(event.getId())
                .withGroupKey(event.getGroupKey())
                .withDescription(event.getDescription())
                .withOccurrenceDate(event.getOccurrenceDateTime())
                .withTitle(event.getTitle())
                .withPlace(event.getPlace());
    }

    public EventBuilder withId(UUID id) {
        if (id != null) {
            this.id = id;
        }
        return this;
    }


    public EventBuilder withGroupKey(String groupKey) {
        if (groupKey != null) {
            this.groupKey = groupKey;
        }
        return this;
    }

    public EventBuilder withOccurrenceDate(LocalDateTime occurrenceDate) {
        if (occurrenceDate != null) {
            this.occurrenceDate = occurrenceDate;
        }
        return this;
    }

    public EventBuilder withPlace(String place) {
        if (place != null) {
            this.place = place;
        }
        return this;
    }

    public EventBuilder withDescription(String description) {
        if (description != null) {
            this.description = description;
        }
        return this;
    }

    public EventBuilder withTitle(String title) {
        if (title != null) {
            this.title = title;
        }
        return this;
    }

    public EventBuilder withAttendants(List<Attendant> attendants) {
        if (attendants != null) {
            this.attendants = new ArrayList<>(attendants);
        }
        return this;
    }


    public Event build() {
        return new Event(id, groupKey, occurrenceDate, place, description, title, attendants);
    }
}
