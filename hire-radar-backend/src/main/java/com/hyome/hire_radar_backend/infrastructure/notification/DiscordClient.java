package com.hyome.hire_radar_backend.infrastructure.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class DiscordClient {
    private final RestClient restClient;
    private final String webhookUrl;

    public DiscordClient(RestClient.Builder builder,
                         @Value("${notification.discord.webhook-url:}") String webhookUrl) {
        this.restClient = builder.build();
        this.webhookUrl = webhookUrl;
    }

    public boolean isEnabled() {
        return webhookUrl != null && !webhookUrl.isBlank();
    }

    public void send(String content) {
        if (!isEnabled()) {
            return;
        }
        restClient.post()
                .uri(webhookUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("content", content))
                .retrieve()
                .toBodilessEntity();
    }
}
