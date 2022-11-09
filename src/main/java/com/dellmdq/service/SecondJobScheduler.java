package com.dellmdq.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SecondJobScheduler {

	@Autowired
	private JobLauncher jobLauncher;

	@Qualifier("secondJob")
	@Autowired
	private Job secondJob;

	@Scheduled(cron = "0 0/1 * 1/1 * ?")//cuando se ejecutara el job? ver: cronmaker.com
	private void secondJobStarter() {

		//seteamos los parametros en un mapa
		Map<String, JobParameter> params = new HashMap<>();
		params.put("current time", new JobParameter(System.currentTimeMillis()));

		//seteamos el mapa en la lista de parametros
		JobParameters jobParameters = new JobParameters(params);

		try {
			JobExecution jobExecution = jobLauncher.run(secondJob, jobParameters);
			System.out.println("Job Execution ID = " + jobExecution);
		} catch (Exception e) {
			System.out.println("Exception while starting job: " + e.getMessage());
		}	
	}
}
