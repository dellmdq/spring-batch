package com.dellmdq.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.dellmdq.model.StudentJson;
import com.dellmdq.model.StudentResponse;
import com.dellmdq.processor.FirstItemProcessor;
import com.dellmdq.reader.FirstItemReader;
import com.dellmdq.service.StudentService;
import com.dellmdq.writer.FirstItemWriter;


@Configuration
public class SampleJob {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
//	@Autowired
//	private SecondTasklet secondTasklet;
//	
//	@Autowired
//	private FirstJobListener firstJobListener;
//	
//	@Autowired
//	private FirstStepListener firstStepListener;
	
	@Autowired
	private FirstItemReader firstItemReader;
	
	@Autowired
	private FirstItemProcessor firstItemProcessor;
	
	@Autowired
	private FirstItemWriter firstItemWriter;
	
	@Autowired
	private StudentService studentService;

	/**
	 * Job -> Steps -> tasklet
	 */
	
//	@Bean
//	public Job firstJob() {
//		return jobBuilderFactory.get("First Job")
//				.incrementer(new RunIdIncrementer())
//				.start(firstStep())
//				.next(secondStep())
//				.listener(firstJobListener)
//				.build();
//	}
//
//	private Step firstStep() {
//		return stepBuilderFactory.get("First Step")
//				.tasklet(firstTask())
//				.listener(firstStepListener)
//				.build();
//	}

//	private Tasklet firstTask() {
//		return new Tasklet() {
//
//			@Override
//			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//				System.out.println("This is first tasklet step.");
//				System.out.println("Step Execution Context" + 
//						chunkContext.getStepContext().getStepExecutionContext());
//				return RepeatStatus.FINISHED;
//			}
//		};
//
//	}
//	//first step config end
//	
//	//second step config start
//	private Step secondStep() {
//		return stepBuilderFactory.get("Second Step")
//				.tasklet(secondTasklet).build();
//	}
	
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
	
//	@Bean
//	public Job secondJob() {
//		return jobBuilderFactory.get("Second Job")//job name
//				.incrementer(new RunIdIncrementer())
//				.start(firstChunkStep())//chunk step
//				.next(secondStep())//tasklet step
//				.build();
//	}
	
	@Bean
	public Job chunkJob() {
		return jobBuilderFactory.get("Chunk Job")
				.incrementer(new RunIdIncrementer())
				.start(firstChunkStep())
				.build();
	}
	
	private Step firstChunkStep() {
		return stepBuilderFactory.get("First Chunk Step")//step name
				.<StudentResponse, StudentResponse>chunk(3)//tamaño del lote a procesar cada vez a través del flujo item reader, processor, writer.
				.reader(itemReaderAdapter())
				//.processor(firstItemProcessor)//es posible no usar processor, es opcional
				.writer(firstItemWriter)
				.build();
	}
	
	@StepScope
	@Bean
	public JsonItemReader<StudentJson> jsonItemReader(@Value("#{jobParameters['inputFile']}") FileSystemResource jsonFile){
		
		JsonItemReader<StudentJson> jsonItemReader = new JsonItemReader<StudentJson>(); 
		
		jsonItemReader.setResource(jsonFile);
		jsonItemReader.setJsonObjectReader(new JacksonJsonObjectReader<>(StudentJson.class));
		
		//configs para elegir datos de la respuesta
		//jsonItemReader.setMaxItemCount(8);//cantidad máxima de items
		//jsonItemReader.setCurrentItemCount(2);//skipea los dos primeros items
		
		return jsonItemReader;
	}
	
	public ItemReaderAdapter<StudentResponse> itemReaderAdapter(){
		ItemReaderAdapter<StudentResponse> itemReaderAdapter =
				new ItemReaderAdapter<StudentResponse>();
		
		//seteamos el servicio que vamos a usar para llamar al cliente
		itemReaderAdapter.setTargetObject(studentService);
		
		//no se puede llamar directamente al servicio que trae TODOS los estudiantes
		//Se debe armar un método aparte que retorne de a uno
		itemReaderAdapter.setTargetMethod("getStudent");
		
		//seteando parametros al reader. Para ser usados en el targetMethod
		itemReaderAdapter.setArguments(new Object[] {1L, "Test123"} );
		
		return itemReaderAdapter;
	}
}
