package be.relive.Global.event.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.UUID;

public class AttendantDto implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID userId;

    private String completeName;

    private String imageUrl;


    private AttendantDto() {
    }

    AttendantDto(UUID userId, String completeName, String imageUrl) {
        this.userId = userId;
        this.completeName = completeName;
        this.imageUrl = imageUrl;
    }

    public String getCompleteName() {
        return completeName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UUID getUserId() {
        return userId;
    }
}
