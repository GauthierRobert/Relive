package be.relive.Global.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import be.relive.Global.user.domain.facebook.FacebookProfile;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

@Service
public class FacebookService {

    private static final String HTTPS_GRAPH_FACEBOOK = "https://graph.facebook.com/me?fields=id,name,picture&access_token=";

    public FacebookProfile getFacebookProfile(String accessToken) throws IOException {

        HttpsURLConnection connection = (HttpsURLConnection) new URL(HTTPS_GRAPH_FACEBOOK + accessToken).openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String inputLine;
        StringBuilder stringBuilder = new StringBuilder();
        while ((inputLine = reader.readLine()) != null)
            stringBuilder.append(inputLine);
        reader.close();

        return new ObjectMapper().readValue(stringBuilder.toString(), FacebookProfile.class);
    }
}
