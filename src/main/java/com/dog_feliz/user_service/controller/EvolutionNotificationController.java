package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.NotificationRequestDto;
import com.dog_feliz.user_service.service.EvolutionNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class EvolutionNotificationController {

    @Autowired
    private final EvolutionNotificationService service;

    public EvolutionNotificationController(EvolutionNotificationService service){
        this.service = service;
    }

    @PostMapping("/sendText/{instance}")
    public ResponseEntity<String> sendMessage(@PathVariable String instance, @RequestBody NotificationRequestDto message){
        try {
            String response = service.sendMessage(instance, message.getNumber(), message.getText());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao enviar mensagem: " + e.getMessage());
        }
    }
}