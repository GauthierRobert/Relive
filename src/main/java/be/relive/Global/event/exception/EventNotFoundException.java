package be.relive.Global.event.exception;

import java.util.UUID;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(UUID userId) {
        super("Event not found with Id : " + userId);
    }
}
