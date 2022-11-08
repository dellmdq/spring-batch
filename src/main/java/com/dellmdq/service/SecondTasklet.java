package com.dellmdq.service;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

/**
 * Implementación de la interfaz tasklet para crear una tarea en una clase aparte y 
 * tener todo más ordenado. Esta clase será llamada desde el step. +
 * Este tasklet podemos definir la logica de negocio que será ejecutada con spring batch.
 * @author dellerik
 *
 */
@Service
public class SecondTasklet implements Tasklet{

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		System.out.println("This is second tasklet step.");
		
		//cada step tiene su contexto y a su vez tiene el contexto del job al que pertenece
		System.out.println(chunkContext.getStepContext().getJobExecutionContext());
		
		return RepeatStatus.FINISHED;
	}
	
	

}
