package com.mhd.CallManagement.service;

import com.mhd.CallManagement.domain.DTO.CreateTokenRequest;
import com.mhd.CallManagement.exceptions.TokenRequestParametersEmptyException;
import io.livekit.server.AccessToken;
import io.livekit.server.RoomJoin;
import io.livekit.server.RoomName;
import io.livekit.server.WebhookReceiver;
import livekit.LivekitWebhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TokenService {

    @Value("${livekit.api.key}")
    private String LIVEKIT_API_KEY;

    @Value("${livekit.api.secret}")
    private String LIVEKIT_API_SECRET;

    public Map<String, String> createToken(CreateTokenRequest request){
        String roomName = request.roomName();
        String participantName = request.participantName();

        if (roomName == null || participantName == null) {
            throw new TokenRequestParametersEmptyException("Room name or Participant name empty");
        }

        AccessToken token = new AccessToken(LIVEKIT_API_KEY, LIVEKIT_API_SECRET);
        token.setName(participantName);
        token.setIdentity(participantName);
        token.addGrants(new RoomJoin(true), new RoomName(roomName));

        return Map.of("token", token.toJwt());

    }

    public String receiveWebhook(String authHeader, String body){
        WebhookReceiver webhookReceiver = new WebhookReceiver(LIVEKIT_API_KEY, LIVEKIT_API_SECRET);
        try {
            LivekitWebhook.WebhookEvent event = webhookReceiver.receive(body, authHeader);
            System.out.println("LiveKit Webhook: " + event.toString());
        } catch (Exception e) {
            System.err.println("Error validating webhook event: " + e.getMessage());
        }
        return "ok";
    }
}
