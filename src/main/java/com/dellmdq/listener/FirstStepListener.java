package com.dellmdq.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;


@Component
public class FirstStepListener implements StepExecutionListener {
/**
 * Listener intercepta el step para ejecutar codigo antes y después del mismo.
 */
	@Override
	public void beforeStep(StepExecution stepExecution) {
		System.out.println("Before First Step " + stepExecution.getStepName());
		System.out.println("Job Execution Context " + stepExecution.getJobExecution().getExecutionContext());
		System.out.println("First Step Execution Context " + stepExecution.getExecutionContext());
		
		stepExecution.getExecutionContext().put("Step Execution Context", "Context Value");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println("After First Step " + stepExecution.getStepName());
		System.out.println("Job Execution Context " + stepExecution.getJobExecution().getExecutionContext());
		System.out.println("First Step Execution Context " + stepExecution.getExecutionContext());
		return null;
	}

	
}
