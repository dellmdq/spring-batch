package com.dellmdq.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/**
 * Intercepta el job. Con codigo que se ejecuta antes y despues de 
 * terminados todos los pasos del Job.
 * @author dellerik
 *
 */
@Component
public class FirstJobListener implements JobExecutionListener{

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("Before Job " + jobExecution.getJobInstance().getJobName());
		System.out.println("Job Params " + jobExecution.getJobParameters());
		System.out.println("Job Execution Context " + jobExecution.getExecutionContext());
		
		jobExecution.getExecutionContext().put("Testing JEC", "JEC Value");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		System.out.println("After Job " + jobExecution.getJobInstance().getJobName());
		System.out.println("Job Params " + jobExecution.getJobParameters());
		System.out.println("Job Execution Context " + jobExecution.getExecutionContext());
		
	}

}
