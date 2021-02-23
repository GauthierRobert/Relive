package be.relive.Global.event.controller;

import be.relive.Global.event.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class ImageApiImpl implements ImageApi {

    private final ImageService imageService;

    public ImageApiImpl(ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    public ResponseEntity getSignedURLPutRequest(UUID id, String extension) {
        return ok(imageService.getPreSignedUrlPutRequest(id, UUID.randomUUID() + "." + extension));
    }

    @Override
    public ResponseEntity getSignedURLGetRequest(UUID id, String imageId) {
        return ok(imageService.getPreSignedUrlGetRequest(id, imageId));
    }


    @Override
    public ResponseEntity delete(UUID eventId, UUID imageId) {
        return null;
    }

    @Override
    public ResponseEntity getListImages(UUID id) {
        return ok(imageService.getListObject(id));
    }


}
