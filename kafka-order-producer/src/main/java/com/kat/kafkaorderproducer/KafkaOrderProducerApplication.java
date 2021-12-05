package com.kat.kafkaorderproducer;

import com.kat.kafkaorderproducer.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
@EnableSwagger2
public class KafkaOrderProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaOrderProducerApplication.class, args);
	}

}
