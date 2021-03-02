package be.relive.Global.group.domain;

import be.relive.Global.dto.UserDto;
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
import java.util.UUID;

@Entity(name = "Subscribers")
@Table(name = "Subscribers",
        indexes = {@Index(name = "subscriber_index", columnList = "userId")},
        uniqueConstraints = {@UniqueConstraint(columnNames = {"groupId", "userId"})})
public class Subscriber {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "pg-uuid")
    private UUID id;

    @Column(name = "userId", nullable = false)
    @Type(type = "pg-uuid")
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId")
    private Group group;

    private String imageUrl;

    private String completeName;

    public Subscriber() {
    }

    private Subscriber(UUID userId, Group group, String imageUrl, String completeName) {
        this.userId = userId;
        this.group = group;
        this.imageUrl = imageUrl;
        this.completeName = completeName;
    }

    public static Subscriber newSubscriber(Group group, UserDto userDto) {
        return new Subscriber(userDto.getId(), group, userDto.getImageUrl(), userDto.getCompleteName());
    }

    public Group getGroup() {
        return group;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCompleteName() {
        return completeName;
    }
}
