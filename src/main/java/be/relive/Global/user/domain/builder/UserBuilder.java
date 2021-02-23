package be.relive.Global.user.domain.builder;

import be.relive.Global.user.domain.FacebookProfile;
import be.relive.Global.user.domain.Subscription;
import be.relive.Global.user.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public final class UserBuilder {
    private UUID id;
    private FacebookProfile profile;
    private Collection<Subscription> subscriptions = new ArrayList<>();

    private UserBuilder() {
    }

    public static UserBuilder anUser() {
        return new UserBuilder();
    }

    public static UserBuilder modify(User user) {
        return new UserBuilder().withId(user.getId())
                .withSubscriptions(user.getSubscriptions())
                .withProfile(user.getProfile());
    }

    public UserBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public UserBuilder withProfile(FacebookProfile profile) {
        this.profile = profile;
        return this;
    }

    public UserBuilder withSubscriptions(Collection<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
        return this;
    }

    public User build() {
        return new User(id, profile, subscriptions);
    }
}
