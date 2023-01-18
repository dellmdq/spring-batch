package com.dellmdq.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.dellmdq.model.StudentJson;
import com.dellmdq.model.StudentResponse;

@Component
public class FirstItemWriter implements ItemWriter<StudentResponse>{

	//el tamaño de la lista depende del chunk size
	@Override
	public void write(List<? extends StudentResponse> items) throws Exception {
		System.out.println("Inside Item Writer");
		items.stream().forEach(System.out::println);
	}
	
}
