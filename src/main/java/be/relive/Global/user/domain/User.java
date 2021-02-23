package be.relive.Global.user.domain;

import org.hibernate.annotations.Type;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
}
