package com.cst438.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.cst438.domain.EnrollmentDTO;


public class GradebookServiceREST extends GradebookService {

	private RestTemplate restTemplate = new RestTemplate();

	@Value("${gradebook.url}")
	String gradebook_url;
	
	public GradebookServiceREST() {
		System.out.println("REST grade book service");
	}

	@Override
	public void enrollStudent(String student_email, String student_name, int course_id) {
		
		//TODO  When a student adds a class, send POST message to Gradebook backend
	   //using EnrollmentDTO 
	   
	  //use postForObject method on restTemplate 
	   
	   //restTemplate.postForObject(URL, Object, EnrollmentDTO.class);
	   
//	   EnrollmentDTO enrollment = new EnrollmentDTO();
//	   enrollment.course_id = course_id;
//	   enrollment.studentEmail = student_email;
//	   enrollment.studentName = student_name;
//	   
//	   System.out.println("Post to gradebook "+enrollment);
//	   EnrollmentDTO response = restTemplate.postForObject(gradebook_url+"/enrollment", enrollment, EnrollmentDTO.class);
//	   System.out.print("Response from gradebook "+response);
	   
	   EnrollmentDTO result = restTemplate.postForObject(gradebook_url + "/enrollment", new EnrollmentDTO(student_email, student_name, course_id), EnrollmentDTO.class);
	   System.out.println("POST student enrollment to gradebook." + result.id + " " + result.studentEmail + " " + result.course_id);
	}

}
