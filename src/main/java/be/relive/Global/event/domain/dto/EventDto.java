package be.relive.Global.event.domain.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class EventDto implements Serializable {

    private UUID id;

    private String groupKey;

    private LocalDateTime occurrenceDate;

    private String place;

    private String description;

    private String title;

    private List<AttendantDto> attendants;

    private EventDto() {
    }

    public EventDto(UUID id, String groupKey, LocalDateTime occurrenceDate, String place, String description, String title, List<AttendantDto> attendants) {
        this.id = id;
        this.groupKey = groupKey;
        this.occurrenceDate = occurrenceDate;
        this.place = place;
        this.description = description;
        this.title = title;
        this.attendants = attendants;
    }

    public UUID getId() {
        return id;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public LocalDateTime getOccurrenceDate() {
        return occurrenceDate;
    }

    public String getPlace() {
        return place;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public List<AttendantDto> getAttendants() {
        return attendants;
    }
}
