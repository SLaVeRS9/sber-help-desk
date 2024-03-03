package ru.sberbank.edu.ticketservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.internals.Topic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import ru.sberbank.edu.common.aspect.ToLog;
import ru.sberbank.edu.ticketservice.ticket.entity.Ticket;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaCreateTicketNoticeService {
    private final KafkaTemplate<String, Ticket> kafkaTemplate;

    @Value("${spring.kafka.topic-1}")
    private String topic1;

    public void sendCreatedTicketWithCallback(Ticket ticket) {
        var sKey = ticket.getId().toString();
        CompletableFuture<SendResult<String, Ticket>> future = kafkaTemplate.send(topic1, sKey, ticket);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent ticket=[" + ticket +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                log.info("Unable to send ticket=[" +
                        ticket + "] due to : " + ex.getMessage());
            }
        });
    }
}
