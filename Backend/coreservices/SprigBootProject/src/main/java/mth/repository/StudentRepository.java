package mth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mth.model.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
	
	
}
