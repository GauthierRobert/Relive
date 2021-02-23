package be.relive.Global.user.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class UserDto implements Serializable {

    UUID id;

    String completeName;

    private String imageUrl;

    private List<String> groupsKeys;

    private UserDto() {
    }

    public UserDto(UUID id, String completeName, String imageUrl, List<String> groupsKeys) {
        this.id = id;
        this.completeName = completeName;
        this.imageUrl = imageUrl;
        this.groupsKeys = groupsKeys;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getGroupsKeys() {
        return groupsKeys;
    }

    public void setGroupsKeys(List<String> groupsKeys) {
        this.groupsKeys = groupsKeys;
    }

    public UUID getId() {
        return id;
    }

    public String getCompleteName() {
        return completeName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
