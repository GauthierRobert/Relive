package be.relive.Global.user.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FacebookProfile implements Serializable {

    @Column(name = "facebook_id", nullable = true, unique = true)
    String id;

    @Column(name = "completeName", nullable = true)
    String name;

    private String imageUrl;

    private FacebookProfile() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacebookProfile that = (FacebookProfile) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, imageUrl);
    }
}
