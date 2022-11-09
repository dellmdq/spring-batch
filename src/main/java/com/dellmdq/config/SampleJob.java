package com.dellmdq.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dellmdq.listener.FirstJobListener;
import com.dellmdq.listener.FirstStepListener;
import com.dellmdq.processor.FirstItemProcessor;
import com.dellmdq.reader.FirstItemReader;
import com.dellmdq.service.SecondTasklet;
import com.dellmdq.writer.FirstItemWriter;

@Configuration
public class SampleJob {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private SecondTasklet secondTasklet;
	
	@Autowired
	private FirstJobListener firstJobListener;
	
	@Autowired
	private FirstStepListener firstStepListener;
	
	@Autowired
	private FirstItemReader firstItemReader;
	
	@Autowired
	private FirstItemProcessor firstItemProcessor;
	
	@Autowired
	private FirstItemWriter firstItemWriter;

	/**
	 * Job -> Steps -> tasklet
	 */
	
	@Bean
	public Job firstJob() {
		return jobBuilderFactory.get("First Job")
				.incrementer(new RunIdIncrementer())
				.start(firstStep())
				.next(secondStep())
				.listener(firstJobListener)
				.build();

	}

	private Step firstStep() {
		return stepBuilderFactory.get("First Step")
				.tasklet(firstTask())
				.listener(firstStepListener)
				.build();

	}

	private Tasklet firstTask() {
		return new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("This is first tasklet step.");
				System.out.println("Step Execution Context" + 
						chunkContext.getStepContext().getStepExecutionContext());
				return RepeatStatus.FINISHED;
			}
		};

	}
	//first step config end
	
	//second step config start
	private Step secondStep() {
		return stepBuilderFactory.get("Second Step")
				.tasklet(secondTasklet).build();

	}
	
//	private Tasklet secondTask() {
//		return new Tasklet() {
//			@Override
//			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//				System.out.println("This is second tasklet step.");
//				return RepeatStatus.FINISHED;
//			}
//		};
//
//	}
	
	//second step config end

	//====================== Chunck Oriented Step config
	
	@Bean
	public Job secondJob() {
		return jobBuilderFactory.get("Second Job")//job name
				.incrementer(new RunIdIncrementer())
				.start(firstChunkStep())//chunk step
				.next(secondStep())//tasklet step
				.build();

	}
	
	private Step firstChunkStep() {
		return stepBuilderFactory.get("First Chunk Step")//step name
				.<Integer,Long>chunk(3)//tamaño del lote a procesar cada vez a través del flujo item reader, processor, writer.
				.reader(firstItemReader)
				.processor(firstItemProcessor)//es posible no usar processor, es opcional
				.writer(firstItemWriter)
				.build();
	}
}
