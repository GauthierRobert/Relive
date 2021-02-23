package be.relive.Global.event.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.UUID;

@Service
public class ImageService {

    private static final Logger log = LoggerFactory.getLogger(ImageService.class);

    private final AmazonClientService amazonClientService;

    @Autowired
    public ImageService(AmazonClientService amazonClientService) {
        this.amazonClientService = amazonClientService;
    }

    public ObjectListing getListObject(UUID eventId) {
        return amazonClientService.getClient().listObjects(amazonClientService.getBucketName(), eventId.toString());
    }

    public URL getPreSignedUrlPutRequest(UUID eventId, String fileName) {
        return getPreSignedUrl(eventId, fileName, HttpMethod.PUT);
    }

    public URL getPreSignedUrlGetRequest(UUID eventId, String fileName) {
        return getPreSignedUrl(eventId, fileName, HttpMethod.GET);
    }


    private URL getPreSignedUrl(UUID eventId, String fileName, HttpMethod httpMethod) {
        AmazonS3 amazonS3 = amazonClientService.getClient();

        // Set the pre-signed URL to expire after one hour.
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60;
        expiration.setTime(expTimeMillis);

        // Generate the pre-signed URL.
        log.info("Generating pre-signed URL.");

        StringBuilder objectKeyBuilder = new StringBuilder().append(eventId);
        if (fileName != null && !fileName.isBlank()) {
            objectKeyBuilder = objectKeyBuilder.append("/").append(fileName);
        }


        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(amazonClientService.getBucketName(), objectKeyBuilder.toString())
                .withMethod(httpMethod)
                .withExpiration(expiration);

        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
    }
}
