package com.cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentDTO;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.ScheduleDTO;
import com.cst438.domain.ScheduleDTO.StudentDTO;
import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;
import com.cst438.service.GradebookService;

@RestController
public class StudentController {
   
   @Autowired
   CourseRepository courseRepository;
   
   @Autowired
   StudentRepository studentRepository;
   
   @Autowired
   EnrollmentRepository enrollmentRepository;
   
   @Autowired
   GradebookService gradebookService;
   /*
    * AS ADMIN
    * add a student to the system by inputting student email and name
    * The email must not already exist in the system
    * POST method
    */
   
   @PostMapping("/addStudent")
   @Transactional
   public StudentDTO addStudent( @RequestParam("studentEmail") String studentEmail, @RequestParam("studentName") String studentName ) {
      Student student = studentRepository.findByEmail(studentEmail);
 
      // student.status
      // = 0  ok to register
      // != 0 hold on registration.  student.status may have reason for hold.
         if (student!= null && student != student) {
          
            Student students = new Student();
            students.setName(studentName);
            students.setEmail(studentEmail);
            Student savedStudent = studentRepository.save(students);
            
            gradebookService.enrollStudent(studentEmail, students.getName(), students.getStudent_id());
            
            ScheduleDTO.StudentDTO result = createStudentDTO(savedStudent);
            return result;
         } else {
            throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student_id invalid or student not allowed to register in the system.  ");
         }
         
   }

   private ScheduleDTO.StudentDTO createStudentDTO(Student s) {
      ScheduleDTO.StudentDTO studentDTO = new ScheduleDTO.StudentDTO();
      //Student sS = s.Enrollment.getStudent();
      studentDTO.student_id = s.getStudent_id();
      studentDTO.student_email = s.getEmail();
      studentDTO.student_name = s.getName();
      return studentDTO;
   }
   
   /*
    * AS ADMIN
    * Put student registration on HOLD
    * POST OR PUT
    */
   @PutMapping("/addStudent/{statusCode}")
   @Transactional
   public StudentDTO addHold( @RequestParam("studentEmail") String studentEmail  ) { 
      Student student = studentRepository.findByEmail(studentEmail);
      
      // student.status
      // = 0  ok to register
      // != 0 hold on registration.  student.status may have reason for hold.
      
         
         Enrollment enrollment = new Enrollment();
         enrollment.setStudent(student);
         Student st = new Student();
         st.setStatusCode(1);
         Student savedStudent = studentRepository.save(st);
         
         gradebookService.enrollStudent(studentEmail, student.getName(), student.getStatusCode());
         
      
      ScheduleDTO.StudentDTO result = createStudentDTO(savedStudent);
      return result;
   
   }
   /*
    * AS ADMIN
    * release the HOLD on student registration
    * PUT/PATCH
    */
   @DeleteMapping("/addStudent/{statusCode}")
   @Transactional
   public void dropHold(  @PathVariable int statusCode, @RequestParam("studentEmail") String studentEmail  ) {
      
      
      
      // TODO  check that today's date is not past deadline to drop course.
      
      Student student = studentRepository.findByEmail(studentEmail);
      
      // verify that student is enrolled in the school
      if (student!=null && student.getEmail().equals(studentEmail)) {
         // OK.  drop the hold
          studentRepository.delete(student);
      } else {
         // something is not right with the enrollment.  
         throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "StatusCode invalid. "+statusCode);
      }
   }
}
