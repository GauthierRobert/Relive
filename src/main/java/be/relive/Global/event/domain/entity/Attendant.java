package be.relive.Global.event.domain.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "Attendants")
@Table(name = "Attendants",
        indexes = {@Index(name = "attendant_index", columnList = "eventId")},
        uniqueConstraints = {@UniqueConstraint(columnNames = {"eventId", "userId", "completeName", "imageUrl"})})
public class Attendant {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "pg-uuid")
    private UUID id;

    @Column(name = "userId", nullable = false)
    @Type(type = "pg-uuid")
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventId")
    private Event event;

    @Column(name = "completeName", nullable = false)
    private String completeName;

    private String imageUrl;


    public Attendant() {
    }

    private Attendant(UUID userId, Event event, String completeName, String imageUrl) {
        this.userId = userId;
        this.event = event;
        this.completeName = completeName;
        this.imageUrl = imageUrl;
    }

    public static Attendant attendant(UUID userId, Event event, String completeName, String imageUrl) {
        if (userId == null) {
            userId = UUID.randomUUID();
        }
        if(imageUrl==null){
            imageUrl = "";
        }

        return new Attendant(userId, event, completeName, imageUrl);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCompleteName() {
        return completeName;
    }

    public UUID getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attendant attendant = (Attendant) o;
        return Objects.equals(userId, attendant.userId) &&
                Objects.equals(event, attendant.event) &&
                Objects.equals(completeName, attendant.completeName) &&
                Objects.equals(imageUrl, attendant.imageUrl);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, event, completeName, imageUrl);
    }

    public UUID getId() {
        return id;
    }
}
