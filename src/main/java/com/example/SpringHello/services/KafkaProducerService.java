package com.example.SpringHello.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class KafkaProducerService {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
    private final String topic = "my-topic";

    public void sendMessage(String message) {
        CompletableFuture<SendResult<String, String>> completableFuture = kafkaTemplate.send(topic, message);

        // Use whenComplete to handle the result
        completableFuture.whenComplete((result, exception) -> {
            if (exception != null) {
                System.out.println("Unable to send message [" + message + "] due to: " + exception.getMessage());
            } else {
                // Handle success
                System.out.println("Sent message [" + message + "] with offset [" + result.getRecordMetadata().offset() + "]");
            }
        });
        System.out.println("Message sent to topic " + topic + ": " + message);
    }
}

