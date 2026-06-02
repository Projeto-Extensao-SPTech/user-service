package com.dog_feliz.user_service;

import com.dog_feliz.user_service.client.NotificationClient;
import com.dog_feliz.user_service.queue.producer.NotificationProducer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceApplicationTests {

    @MockitoBean
    private NotificationClient notificationClient;

    @MockitoBean
    private NotificationProducer notificationProducer;

    @Test
    void contextLoads() {
    }
}
