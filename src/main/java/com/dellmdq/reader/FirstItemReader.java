package com.dellmdq.reader;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component
public class FirstItemReader implements ItemReader<Integer>  {
/**
 * Responsable de traer la data desde la fuente.
 */
	List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
	int i = 0;

/**
 * Este metodo sera llamado por spring batch. El metodo read() retornara			
 * de a un valor/registro a la vez. No iterar dentro del read. Recordar
 * que el valor retornado es un Integer. La cantidad de veces que será 
 * llamado el read por pasada, dependerá del tamaño del lote establecido. 
 * (chunck-size)
 *
 */
@Override
public Integer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
	System.out.println("Inside Item Reader.");
	Integer item;
	if(i < list.size()) {
		item = list.get(i);
		i++;
		return item;
	}
	i = 0;//reset variable
	return null;//indica que no hay mas registros para leer de la fuente y frena el flujo
}
	
	
}
