package be.relive.Global.user.domain;

import be.relive.Global.user.domain.facebook.FacebookProfile;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity(name = "Users")
@Table(name = "Users",
        indexes = {@Index(name = "user_key",  columnList="completeName", unique = true)})
public class User {

    @Id
    @Type(type="pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "notification_token", nullable = true)
    private String notificationToken;

    @Embedded
    private FacebookProfile profile;

    @OneToMany(mappedBy="user", fetch = FetchType.EAGER)
    private Collection<Subscription> subscriptions  = new ArrayList<>();


    private User() {
    }

    public User(UUID id, FacebookProfile profile, Collection<Subscription> subscriptions) {
        this.id = id;
        this.profile = profile;
        this.subscriptions = subscriptions;
    }

    public UUID getId() {
        return id;
    }

    public FacebookProfile getProfile() {
        return profile;
    }

    public Collection<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public String getNotificationToken() {
        return notificationToken;
    }
}
