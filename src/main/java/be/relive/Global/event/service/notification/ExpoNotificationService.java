package be.relive.Global.event.service.notification;

import io.github.jav.exposerversdk.ExpoPushMessage;
import io.github.jav.exposerversdk.PushClient;
import io.github.jav.exposerversdk.PushClientException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpoNotificationService {

    public void sendPushNotification(List<String> recipients, String title, String message) throws PushClientException {

        if (!recipients.stream().allMatch(PushClient::isExponentPushToken))
            throw new RuntimeException("Expo Tokens not valid.");

        List<ExpoPushMessage> expoPushMessages = recipients.stream().map(recipient -> {
            ExpoPushMessage expoPushMessage = new ExpoPushMessage();
            expoPushMessage.getTo().add(recipient);
            expoPushMessage.setTitle(title);
            expoPushMessage.setBody(message);
            return expoPushMessage;
        }).collect(Collectors.toList());

        PushClient client = new PushClient();
        // The Expo push notification service accepts batches of notifications so
        // that you don't need to send 1000 requests to send 1000 notifications. We
        // recommend you batch your notifications to reduce the number of requests
        // and to compress them (notifications with similar content will get
        // compressed).
        List<List<ExpoPushMessage>> chunks = client.chunkPushNotifications(expoPushMessages);

        // Send the chunks to the Expo push notification service. There are
        // different strategies you could use. A simple one is to send one chunk at a
        // time, which nicely spreads the load out over time:
        for (List<ExpoPushMessage> chunk : chunks) {
            client.sendPushNotificationsAsync(chunk);
        }
    }
}
