package com.byulsoft.msa.kafka;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class KafkaUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    // private static KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private static KafkaTemplate<String, String> kafkaTemplate;


    @PostConstruct
    public void init() {
        kafkaTemplate = (KafkaTemplate<String, String>) applicationContext.getBean("kafkaTemplate");
    }


    /**
     * @param topic
     * @param payload
     * @Method Name : send
     * @작성일 : 2019. 06. 20.
     * @작성자 : H.S.JANG
     * @변경이력 :
     * @Method 설명 :
     */
    public static void send(String topic, String payload) {

        /*
        kafkaTemplate.executeInTransaction(t -> {
            t.send(topic, payload);
            return true;
        });
         */

        kafkaTemplate.send(topic, payload);
    }

    /**
     * @param topic
     * @param key
     * @param value
     * @Method Name : send
     * @작성일 : 2019. 06. 20.
     * @작성자 : H.S.JANG
     * @변경이력 :
     * @Method 설명 :
     */
    public static void send(String topic, String key, String value) {

        /*
        kafkaTemplate.executeInTransaction(t -> {
            t.send(topic, key, value);
            return true;
        });
         */

        kafkaTemplate.send(topic, key, value);
    }


    /**
     * @param payload
     * @Method Name : send
     * @작성일 : 2019. 06. 20.
     * @작성자 : H.S.JANG
     * @변경이력 :
     * @Method 설명 :
     */
    public static void send(String payload) {

        send(kafkaTemplate.getDefaultTopic(), payload);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }
}
