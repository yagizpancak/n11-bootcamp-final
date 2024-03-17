package com.n11.logservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerService {

	@KafkaListener(topics = "errorLog", groupId = "log-consumer-group")
	public void consume(String message){
		log.error(message);
	}

	@KafkaListener(topics = "infoLog", groupId = "log-consumer-group")
	public void consumeInfos(String message){
		log.info(message);

	}
}
