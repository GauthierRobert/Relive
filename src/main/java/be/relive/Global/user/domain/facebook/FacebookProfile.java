package be.relive.Global.user.domain.facebook;

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

    private FacebookPicture picture;

    private FacebookProfile() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FacebookPicture getPicture() {
        return picture;
    }

    public void setPicture(FacebookPicture picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacebookProfile that = (FacebookProfile) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(picture, that.picture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, picture);
    }
}
