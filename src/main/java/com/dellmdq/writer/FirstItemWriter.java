package com.dellmdq.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class FirstItemWriter implements ItemWriter<Long>{

	//el tamaño de la lista depende del chunk size
	@Override
	public void write(List<? extends Long> items) throws Exception {
		System.out.println("Inside Item Writer");
		items.stream().forEach(System.out::println);
	}
	
	

}
