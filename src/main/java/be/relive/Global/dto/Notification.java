package be.relive.Global.dto;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Notification implements Serializable {

    private String title;

    private String message;

    private Map<String, Object> additionalProperties = new ConcurrentHashMap<>();

    public Notification(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }
}
