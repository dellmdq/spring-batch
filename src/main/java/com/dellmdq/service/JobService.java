package com.dellmdq.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.dellmdq.request.JobParamsRequest;

@Service
public class JobService {
	
	@Autowired
	private JobLauncher jobLauncher;

	@Qualifier("firstJob")
	@Autowired
	private Job firstJob;
	
	@Qualifier("secondJob")
	@Autowired
	private Job secondJob;
	
	@Async//our api wont wait for the job to be completed
	public void startJob(String jobName, List<JobParamsRequest> jobParamsRequestsList) {
		
		//seteamos los parametros en un mapa
		Map<String, JobParameter> params = new HashMap<>();
		params.put("current time", new JobParameter(System.currentTimeMillis()));
		
		//agregamos cada uno de los elementos de la lista enviada en el request body
		//al mapa de jobparameters
		jobParamsRequestsList.stream().forEach(jobParamRequest -> {
			params.put(jobParamRequest.getParamKey(), new JobParameter(jobParamRequest.getParamValue()));
		});
		
		//seteamos el mapa en la lista de parametros
		JobParameters jobParameters = new JobParameters(params);
		
		try {
			JobExecution jobExecution = null;
			if(jobName.equals("First Job")) {
				jobExecution = jobLauncher.run(firstJob, jobParameters);
			}else if(jobName.equals("Second Job")) {
				jobExecution = jobLauncher.run(secondJob, jobParameters);
			}
			System.out.println("Job Execution ID = " + jobExecution);
		} catch (Exception e) {
			System.out.println("Exception while starting job: " + e.getMessage());
		}

	}
}
