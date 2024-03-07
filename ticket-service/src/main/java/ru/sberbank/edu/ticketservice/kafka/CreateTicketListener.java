package ru.sberbank.edu.ticketservice.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.sberbank.edu.common.aspect.ToLog;

@Component
@Slf4j
public class CreateTicketListener {
    @Value("${spring.kafka.topic-1}")
    private String topic1;

    @KafkaListener(topics = "topic-1", groupId = "group1")
    void listener(String data) {
        log.info("Received message [{}] in group1", data);
    }
}
