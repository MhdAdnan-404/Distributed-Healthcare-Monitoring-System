package com.mhd.CallManagement.controller;

import com.mhd.CallManagement.domain.DTO.CreateTokenRequest;
import com.mhd.CallManagement.service.TokenService;
import io.livekit.server.WebhookReceiver;
import jakarta.validation.Valid;
import livekit.LivekitWebhook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@CrossOrigin(origins = "*")
@RestController
public class CallController {

    @Autowired
    TokenService tokenService;

    @PostMapping(value = "/token")
    public ResponseEntity<Map<String, String>> createToken(@RequestBody @Valid  CreateTokenRequest request) {

        return ResponseEntity.ok(tokenService.createToken(request));
    }

    @PostMapping(value = "/livekit/webhook", consumes = "application/webhook+json")
    public ResponseEntity<String> receiveWebhook(@RequestHeader("Authorization") String authHeader, @RequestBody String body) {
        return ResponseEntity.ok(tokenService.receiveWebhook(authHeader,body));
    }



}
