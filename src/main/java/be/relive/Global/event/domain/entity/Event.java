package be.relive.Global.event.domain.entity;

import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "Events")
@Table(name = "Events",
        indexes = {@Index(name = "index_event_groupKey", columnList = "groupKey")})
public class Event implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "pg-uuid")
    private UUID id;

    @Column(name = "groupKey", nullable = false)
    private String groupKey;

    @Column(name = "occurrenceDateTime")
    private LocalDateTime occurrenceDateTime;

    private String place;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attendant> attendants = new ArrayList<>();


    private Event() {
    }

    Event(UUID id, String groupKey, LocalDateTime localDateTime, String place, String description, String title, String type, List<Attendant> attendants) {
        this.id = id;
        this.groupKey = groupKey;
        this.occurrenceDateTime = localDateTime;
        this.place = place;
        this.description = description;
        this.title = title;
        this.type = type;
        this.attendants = attendants;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public LocalDateTime getOccurrenceDateTime() {
        return occurrenceDateTime;
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

    public UUID getId() {
        return id;
    }

    public List<Attendant> getAttendants() {
        return attendants;
    }

    public String getType() {
        return type;
    }
}
