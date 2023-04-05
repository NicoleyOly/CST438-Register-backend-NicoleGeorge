package com.cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.cst438.domain.StudentDTO;
import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;
import com.cst438.service.GradebookService;
import com.cst438.service.GradebookService;

@RestController
@CrossOrigin(origins= {"http://localhost:3000"})
public class StudentController {
   
   @Autowired
   CourseRepository courseRepository;
   
   @Autowired
   StudentRepository studentRepository;
   
   @Autowired
   EnrollmentRepository enrollmentRepository;
   
   @Autowired(required=false)
   GradebookService gradebookService;
   /*
    * AS ADMIN
    * add a student to the system by inputting student email and name
    * The email must not already exist in the system
    * POST method
    */
   
   @PostMapping("/addStudent")

   public StudentDTO addStudent(@RequestBody StudentDTO dto) {
      Student student = studentRepository.findByEmail(dto.student_email);
 
      // student.status
      // = 0  ok to register
      // != 0 hold on registration.  student.status may have reason for hold.
         if (student== null) {
          
            Student students = new Student();
            students.setName(dto.student_name);
            students.setEmail(dto.student_email);
            students.setStatus(dto.status);
            students.setStatusCode(dto.statusCode);
            students = studentRepository.save(students);
            dto.id=students.getStudent_id();
            return dto;
            
            //StudentDTO result = createStudentDTO(savedStudent);
            //return result;
         } else {
            throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student email already exists  ");
         }
         
   }
//   @PostMapping("/addStudent/{studentEmail}")
//   @Transactional
//   public StudentDTO addStudent2(@PathVariable("student_email") String student_email) {
//      Student s = studentRepository.findByEmail(student_email);
//      if(s==null) {
//         Student ss = new Student();
//         ss.setName(student_name);
//         ss.setEmail(student_email);
//         ss.setStatus(student_email)
//      }
//   }
//  
//
   private StudentDTO createStudentDTO(Student s) {
      StudentDTO studentDTO = new StudentDTO();
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
   @PutMapping("/addStudent/{studentEmail}")
   @Transactional
   public StudentDTO addHold( @PathVariable("student_email") String student_email  ) { 
      Student student = studentRepository.findByEmail(student_email);
      
      // student.status
      // = 0  ok to register
      // != 0 hold on registration.  student.status may have reason for hold.
      
         
      // Enrollment enrollment = new Enrollment();
      // enrollment.setStudent(student);
    //   Student st = new Student();
         student.setStatusCode(0);
         Student savedStudent = studentRepository.save(student);
         
         //gradebookService.enrollStudent(studentEmail, student.getName(), student.getStatusCode());
         
      
      StudentDTO result = createStudentDTO(savedStudent);
      return result;
   
   }
   /*
    * AS ADMIN
    * release the HOLD on student registration
    * PUT/PATCH
    */
   @PutMapping("/addStudent/{studentEmail")
   @Transactional
   public StudentDTO changeHold( @PathVariable("studentEmail") String studentEmail ) {
      Student student = studentRepository.findByEmail(studentEmail);
      student.setStatusCode(1);
      Student savedStudent = studentRepository.save(student);
      
      StudentDTO result = createStudentDTO(savedStudent);
      return result;
   }
   //delete's student
   @DeleteMapping("/addStudent/{studentEmail}")
   @Transactional
   public void dropStudent( @PathVariable("studentEmail") String studentEmail  ) {
      
      
      
      // TODO  check that today's date is not past deadline to drop course.
      
      Student student = studentRepository.findByEmail(studentEmail);
      
      // verify that student is enrolled in the school
      if (student!=null) {
         // OK.  drop the hold
          studentRepository.delete(student);
      } else {
         // something is not right with the enrollment.  
         throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student not found. ");
      }
   }
   //create new method put for updating the hold
}
