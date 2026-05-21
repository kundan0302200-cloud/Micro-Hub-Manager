package mth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mth.model.Student;
import mth.service.StudentService;

@RestController
@RequestMapping("/")
public class StudentController {
	@Autowired
	StudentService studentService;
	
	@PostMapping
public Student register(Student student) {
		return studentService.register(student);
	}
}
