package be.relive.Global.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.UUID;

@RequestMapping(path = "/user", produces = "application/json")
public interface UserApi {

    @PostMapping(path = "/{token}")
    ResponseEntity login(@PathVariable("token") String token) throws IOException;

    @PostMapping(path = "/{userId}/subscribe/{key}")
    ResponseEntity Subscribe(@PathVariable("userId") UUID userId, @PathVariable("key") String key) throws JsonProcessingException;

    @PostMapping(path = "/{userId}/unsubscribe/{key}")
    ResponseEntity unSubscribe(@PathVariable("userId") UUID userId, @PathVariable("key") String key) throws JsonProcessingException;
}
