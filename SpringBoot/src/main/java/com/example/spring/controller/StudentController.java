package com.example.spring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.exception.ResourceNotFoundException;
import com.example.spring.model.Student;
import com.example.spring.repository.StudentRepository;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/")
public class StudentController {
	
	@Autowired
	private StudentRepository studentRepository;
	
	// get all details of students 
	@GetMapping("/students")
	public List<Student> getAllStudent(){
		return studentRepository.findAll();
	}
	
	//create student rest API 
	@PostMapping("/students")
	public Student createStudent(@RequestBody Student student) {
		return studentRepository.save(student);
	}
	
	//get student by id 
	@GetMapping("/students/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable Long id){
		Student student = studentRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("Student does not exist with id :"+id));
		return ResponseEntity.ok(student);
	}
	
	//update students
	@PutMapping("/students/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable Long id,@RequestBody Student studentDetails){
		Student student = studentRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("Student does not exist with id:"+id));
		
		student.setName(studentDetails.getName());
		student.setRollNo(studentDetails.getRollNo());
		student.setEmailId(studentDetails.getEmailId());
		
		Student updatedStudent = studentRepository.save(student);
		return ResponseEntity.ok(updatedStudent);			
	}
	
	//delete students
	@DeleteMapping("/students/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteStudent(@PathVariable Long id){
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student does not exist with id:"+id));
		
		studentRepository.delete(student);
		Map<String,Boolean> response = new HashMap<>();
		response.put("deleted",Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	

}
