package co.in.techLakhan.Employee_Cache_demo.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class KafkaTest {

    private static final Logger logger = LoggerFactory.getLogger(KafkaTest.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;
    public KafkaTest(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public CompletableFuture<Void> sentMail(final Long employeeId) {
        return kafkaTemplate.send("employee-events", String.valueOf(employeeId), "Employee updated")
                .thenAccept(stringObjectSendResult -> {
                    logger.info("Kafka event published successfully");
                    return ;
                } )
                .exceptionally(throwable -> {
                    logger.error("Failed to publish Kafka event");
                    return null;
                });
    }
}
