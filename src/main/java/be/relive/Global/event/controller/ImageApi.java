package be.relive.Global.event.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@RequestMapping(path = "/event", produces = "application/json")
public interface ImageApi {

    @GetMapping(path = "/{eventId}/image/putRequest")
    ResponseEntity getSignedURLPutRequest(@PathVariable("eventId") UUID id,
                                          @RequestParam("extension") String extension);

    @GetMapping(path = "/{eventId}/image/{imageId}")
    ResponseEntity getSignedURLGetRequest(@PathVariable("eventId") UUID eventId,
                                          @PathVariable("imageId") String imageId);

    @DeleteMapping(path = "/{eventId}/image/{imageId}")
    ResponseEntity delete(@PathVariable("eventId") UUID eventId,
                          @PathVariable("imageId") UUID imageId);

    @GetMapping(path = "/{eventId}/image")
    ResponseEntity getListImages(@PathVariable("eventId") UUID id);
}
