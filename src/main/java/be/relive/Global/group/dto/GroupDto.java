package be.relive.Global.group.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

public class GroupDto implements Serializable {

    private String key;

    private String description;

    private String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<UserDto> users;

    private GroupDto() {
    }

    public GroupDto(String key, String description, String title, List<UserDto> users) {
        this.key = key;
        this.description = description;
        this.title = title;
        this.users = users;
    }

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public List<UserDto> getUsers() {
        return users;
    }
}
