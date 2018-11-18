package com.honwaii.bigdata.sparklearning.kafkaproduce;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Data
@Component
public class Producer implements ApplicationRunner {
    private final KafkaTemplate kafkaTemplate;

    @Autowired
    public Producer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void sendMsgToKafka() {
        String msg = "hello word!";
        kafkaTemplate.send("10000201", msg);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("start...");
    }

}
