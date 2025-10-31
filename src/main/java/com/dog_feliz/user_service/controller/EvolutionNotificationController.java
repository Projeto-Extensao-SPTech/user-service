package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.NotificationRequestDto;
import com.dog_feliz.user_service.service.EvolutionNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class EvolutionNotificationController {

    @Autowired
    private EvolutionNotificationService service;

    public EvolutionNotificationController(EvolutionNotificationService service){
        this.service = service;
    }

    @PostMapping("/sendText/{instance}")
    private String sendMensage(@PathVariable String instance, @RequestBody NotificationRequestDto message){
        String number = message.getNumber();
        String text = message.getText();
        return service.sendMensage(instance,number,text);
    }

}
