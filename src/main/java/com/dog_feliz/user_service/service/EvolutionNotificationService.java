package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.entity.DonationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class EvolutionNotificationService {

    @Value("${evolution.api.url}")
    private String evolutionApiUrl;

    @Value("${evolution.api.key}")
    private String apiInstanceKey;

    @Value("${evolution.instance.name}")
    private String instanceName;

    @Value("${evolution.admin-number}")
    private String adminNumber;

    @Autowired
    RestTemplate template;

    public String sendMessage(String instanceName, String number, String message){

        String url = evolutionApiUrl  + "/message/sendText/" + instanceName;

        HashMap <String,Object> postResponse = new HashMap<>();

        postResponse.put("number", number);
        postResponse.put("text",message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", apiInstanceKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(postResponse, headers);

        ResponseEntity<String> response = template.postForEntity(url, request, String.class);

        return response.getBody();
    }

    public void sendDonationNotification(DonationEntity donation, String donorName) {
        try {
            String messageText = String.format(
                    "🐶 *Nova Doação Recebida!* 🐶\n\n" +
                            "👤 *Doador:* %s\n" +
                            "📦 *Item:* %s\n" +
                            "📊 *Qtd:* %d\n" +
                            "📝 *Estado:* %s\n" +
                            "🚚 *Envio:* %s\n\n" +
                            "Acesse o sistema para mais detalhes.",
                    donorName,
                    donation.getName(),
                    donation.getAmount(),
                    donation.getState(),
                    donation.getShippingMethod()
            );

            sendMessage(this.instanceName, adminNumber, messageText);

            System.out.println("✅ Notificação de WhatsApp enviada com sucesso!");

        } catch (Exception e) {
            System.err.println("❌ Falha ao enviar WhatsApp: " + e.getMessage());
        }
    }

}
