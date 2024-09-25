package com.colak.springtutorial.consumer;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Message;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class NatsSubscriber {

    private final Connection natsConnection;

    @PostConstruct
    public void subscribe() {
        // Subject to subscribe to
        String subject = "mySubject";

        // Create a Dispatcher to handle incoming messages
        Dispatcher dispatcher = natsConnection.createDispatcher(NatsSubscriber::processMessage);

        // Subscribe to the subject
        dispatcher.subscribe(subject);
    }

    private static void processMessage(Message message) {
        String str = new String(message.getData(), StandardCharsets.UTF_8);
        log.info("Received message: {}", str);
    }
}
