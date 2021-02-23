package be.relive.Global.user.domain;


import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.UUID;

@Entity(name = "Subscriptions")
@Table(name = "Subscriptions",
        uniqueConstraints={
                @UniqueConstraint(columnNames = {"key", "userId"})})
public class Subscription {

    @Id
    @Column(name = "group_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type="pg-uuid")
    private UUID id;

    @Column(name = "key")
    private String key;

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Subscription() {
    }

    public String getKey() {
        return key;
    }

    public Subscription(String key, User user) {
        this.key = key;
        this.user = user;
    }
}
