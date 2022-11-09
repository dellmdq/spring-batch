package com.dellmdq.controller;

import java.util.List;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dellmdq.request.JobParamsRequest;
import com.dellmdq.service.JobService;

@RestController
@RequestMapping("/api/job")
public class JobController {
	
	@Autowired
	private JobService jobService;

	
	@GetMapping("start/{jobName}")
	public String startJob(@PathVariable String jobName,
			@RequestBody List<JobParamsRequest> jobParamsRequestsList) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		
		//@async, el controlador llamara al servicio pero como este esta anotado con @async, el controller no 
		//esperara a que este termine. Continuara con la ejecucion y mostrara "Job Started..."
		jobService.startJob(jobName, jobParamsRequestsList);
		

		return "Job started...";
	}
}
