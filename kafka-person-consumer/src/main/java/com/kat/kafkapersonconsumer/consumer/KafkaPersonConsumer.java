package com.kat.kafkapersonconsumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import static com.kat.kafkapersonconsumer.KafkaPersonConsumerApplicationConstants.PERSON_AGE_GROUP_ID;
import static com.kat.kafkapersonconsumer.KafkaPersonConsumerApplicationConstants.PERSON_GROUP_ID;

@Service
@Slf4j
public class KafkaPersonConsumer {

    @KafkaListener(topics = "${kafka-person-consumer.person-topic}", groupId = PERSON_GROUP_ID)
    public void listenToAll(ConsumerRecord<String, String> message, @Header(KafkaHeaders.GROUP_ID) String groupId) {
        log.info("Group id: {}, Partition : {}, Offset : {}, Message : {}", groupId, message.partition(), message.offset(), message.value());
    }

    @KafkaListener(topics = "${kafka-person-consumer.person-topic}", groupId = PERSON_AGE_GROUP_ID, containerFactory = "ageContainerFactory")
    public void listenToAgeOlderThan(ConsumerRecord<String, String> message, @Header(KafkaHeaders.GROUP_ID) String groupId) {
        log.info("Group id: {}, Partition : {}, Offset : {}, Message : {}", groupId, message.partition(), message.offset(), message.value());
    }
}
