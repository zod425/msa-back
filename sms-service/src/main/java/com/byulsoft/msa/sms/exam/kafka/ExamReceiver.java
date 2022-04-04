package com.byulsoft.msa.sms.exam.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExamReceiver {


    @KafkaListener(topics = "exam.kafka", groupId = "exam.kafka.group")
    public void examTest(ConsumerRecord<String, String> cr, String params) throws Exception {
        log.info("Kafka Exam Test : {}" , params);
        log.info("ConsumerRecord : {}" , cr.toString());
    }

}
