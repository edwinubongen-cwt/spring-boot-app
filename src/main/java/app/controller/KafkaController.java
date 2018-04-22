package app.controller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

	public static Logger logger = LoggerFactory.getLogger(KafkaController.class);
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@RequestMapping("/send-message")
	public String sendMessage() {
		kafkaTemplate.sendDefault("default-topic-message-1");
		return "success";
	}

	@RequestMapping("/send-message-topic-1")
	public String sendMessageForTopic1() {
		kafkaTemplate.send("spring-boot-topic-1", "topic-1-message-1");
		return "success";
	}
	
	@RequestMapping("/send-message-topic-2")
	public String sendMessageForTopic2() {
		kafkaTemplate.send("spring-boot-topic-2", "topic-2-message-1");
		return "success";
	}
	
	@RequestMapping("/send-message-non-blocking/{topic}/{message}")
	public void sendMessageNonBlocking(@PathVariable String topic, @PathVariable String message) {
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, message);
	    ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(record);
	    logger.info("Message Sent.");
	    future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

	        @Override
	        public void onSuccess(SendResult<String, String> result) {
	        	logger.info("Message Successful.");
	        	logger.info("Metadata: {} ", result.getRecordMetadata());
	        	logger.info("Record: {} ", result.getProducerRecord());
	        }

	        @Override
	        public void onFailure(Throwable ex) {
	        	logger.error("Exception occured.", ex);
	        }

	    });
	}
	
	@RequestMapping("/send-message-blocking/{topic}/{message}")
	public void sendToKafka(@PathVariable String topic, @PathVariable String message) {
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, message);
	    try {
	    	kafkaTemplate.send(record).get(10, TimeUnit.SECONDS);
	    	logger.info("Message Sent.");
	    }
	    catch (ExecutionException e) {
	    	logger.error("ExecutionException occured.", e);
	    }
	    catch (TimeoutException | InterruptedException e) {
	    	logger.error("TimeoutException occured.", e);
	    }
	}
}
