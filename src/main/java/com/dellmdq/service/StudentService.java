package com.dellmdq.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dellmdq.model.StudentResponse;

@Service
public class StudentService {
	
	List<StudentResponse> list;

	public List<StudentResponse> getStudents(){
		RestTemplate client = new RestTemplate();
		StudentResponse[] studentResponses = client.getForObject("http://localhost:8081/api/v1/students", StudentResponse[].class);
		
		list = new ArrayList<>();	
		for (StudentResponse studentResponse : studentResponses) {
			list.add(studentResponse);
		}
		
		return list;
	}
	
	/**
	 * Los parametros vienen del setArguments dentro del reader.
	 * @param id
	 * @param name
	 * @return
	 */
	public StudentResponse getStudent(Long id, String name) {
		System.out.println("Id = " + id + " and name = " + name);
		if(list == null) {
			getStudents();
		}
		
		if(list != null && !list.isEmpty()) {
			return list.remove(0);
		}
		return null;
	}
}
