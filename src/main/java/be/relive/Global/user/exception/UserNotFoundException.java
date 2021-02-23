package be.relive.Global.user.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(UUID userId) {
        super("User not found with Id : " + userId);
    }
}
