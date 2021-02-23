package be.relive.Global.group.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class UserDto implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;

    private String completeName;

    private String imageUrl;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> groupsKeys;

    private UserDto() {
    }

    public UserDto(String completeName, String imageUrl) {
        this.completeName = completeName;
        this.imageUrl = imageUrl;
    }

    public UserDto(UUID id, String completeName, String imageUrl, List<String> groupsKeys) {
        this.id = id;
        this.completeName = completeName;
        this.imageUrl = imageUrl;
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

    public List<String> getGroupsKeys() {
        return groupsKeys;
    }
}
