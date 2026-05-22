package com.dog_feliz.user_service.support;

import com.dog_feliz.user_service.client.NotificationClient;
import com.dog_feliz.user_service.queue.producer.NotificationProducer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class IntegrationTestBase {

    @MockitoBean
    protected NotificationClient notificationClient;

    @MockitoBean
    protected NotificationProducer notificationProducer;
}
