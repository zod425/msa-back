package com.byulsoft.msa.kafka;


import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Data
@EnableKafka
@Configuration
public class KafkaConfiguration {


    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;


    @Value("${kafka.producer.transactional-id}")
    private String transactionalId;

    /* Consumer Config start */

    @Bean
    ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<Integer, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        // 시간 문제가 계속 발생되어 1시간으로 변경
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "3600000"); // default 300,000 (5분)
        // props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "15"); // default 500

        // SESSION 시간을  30초로 조정
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30 * 1000 ); // default 10 (10초)


        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false); // 자동 커밋 여부
        props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");

        return props;
    }


    // kafkaListener Transaction
    /*
    @Bean("kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<?, ?> containerFactory(ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
                                                                          ConsumerFactory kafkaConsumerFactory,
                                                                          KafkaTransactionManager kafkaTransactionManager) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, kafkaConsumerFactory);
        factory.getContainerProperties().setTransactionManager(kafkaTransactionManager);
        return factory;
    }
     */


    /* Consumer Config end */


    /* Producer Config start */
    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        // list of host:port pairs used for establishing the initial connections to the Kakfa cluster
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        // props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG , false);
        // props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, transactionalId);

        return props;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {

        /* 트랜잭션 처리용  주석처리
        DefaultKafkaProducerFactory<String, String> factory = new DefaultKafkaProducerFactory<>(producerConfigs());
        factory.setTransactionIdPrefix(transactionalId);
        return factory;
         */
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }


    // 아래의 방식은 @Transactions 사용시
    /*
    @Bean
    public KafkaTransactionManager<?, ?> kafkaTransactionManager(ProducerFactory<?, ?> kafkaProducerFactory) {
        KafkaTransactionManager<?, ?> kafkaTransactionManager = new KafkaTransactionManager<>(kafkaProducerFactory);
        return kafkaTransactionManager;
    }
     */



    /*
     *  특정 토픽에 대해서 파티션을 늘려야될 경우 다음과 같은 방법으로 파티션을 조정한다.
     * @return
     */
    /*
    @Bean
    public NewTopic createTopicAndPartions() {
      return TopicBuilder.name(KafkaConstants.AdminTopicConst.STOCK_SEND_SCHEDULER) // 토픽 이름
        .partitions(2) // 파티션 수
        .replicas(1)
        .config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd") // 압축 방법
        .build();
    }
     */


    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    /* Producer Config end */
}
