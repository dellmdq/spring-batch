package com.dellmdq.demo;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan({"com.dellmdq.config", "com.dellmdq.service", "com.dellmdq.listener",
	"com.dellmdq.reader", "com.dellmdq.processor", "com.dellmdq.writer",
	"com.dellmdq.controller"})//de estos paquetes leeran los jobs y steps
@EnableAsync
//@EnableScheduling//permite agendar runs
public class SpringBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchApplication.class, args);
	}

}
