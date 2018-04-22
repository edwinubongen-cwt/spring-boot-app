package app.kafka.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
	
	public static Logger logger = LoggerFactory.getLogger(KafkaListeners.class);
	
	@KafkaListener(topics = "spring-boot-topic-1")
	public void listen1(String message) {
	    logger.info("Received string message in group testGroup: {}", message);
	}

	@KafkaListener(topics = "spring-boot-topic-2")
	public void listen2(String message) {
	    logger.info("Received string message in group testGroup: {}", message);
	}

}
