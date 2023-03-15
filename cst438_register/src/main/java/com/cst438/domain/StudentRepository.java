package com.cst438.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends CrudRepository <Student, Integer> {
	
	// declare the following method to return a single Student object
	// default JPA behavior that findBy methods return List<Student> except for findById.
	public Student findByEmail(String email);
   @Query("select e from Student e where e.student.email=:email and e.student_id=:id")
   public List<Student> findStudent(
         @Param("email") String email, 
         @Param("id") int student_id);
   public int findById(
         @Param("id") int student_id);
   
   @Query("select e from Student e where e.student.email=:email and e.student.student_id=:student_id")
   Student findByEmailAndStudentId(@Param("email") String email, @Param("student_id") int student_id);
   
   @SuppressWarnings("unchecked")
   Student save(Student e);
}
