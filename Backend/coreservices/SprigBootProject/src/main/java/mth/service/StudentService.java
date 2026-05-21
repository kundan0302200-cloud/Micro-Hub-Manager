package mth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mth.model.Student;
import mth.repository.StudentRepository;

@Service
public class StudentService {
	@Autowired
StudentRepository studentRepo;
	
	public Student register(Student student) {
		return studentRepo.save(student);
	}
}
