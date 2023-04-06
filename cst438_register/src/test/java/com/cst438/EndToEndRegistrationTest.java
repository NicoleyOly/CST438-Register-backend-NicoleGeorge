package com.cst438;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.concurrent.TimeUnit;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;

@SpringBootTest
public class EndToEndRegistrationTest {
   //as admin add new student
   //use JUNIT and Test 
  //update application properties
   //run mysql
   //eclipse, start spring server, start node.js, end-test run as - junit test
   
   //a to find all <a> tags
   //input to find inputs and fun stuff
   //driver.findElement() XPATH return DOM element
   public static final String CHROME_DRIVER_FILE_LOCATION = "/Users/potatofamily/Desktop/chromedriver_mac64/chromedriver";
   public static final String URL = "http://localhost:3000";
   public static final String ALIAS_NAME = "test";
   public static final int SLEEP_DURATION = 1000;
   public static final String TEST_USER_EMAIL = "test@csumb.edu";
   
   @Autowired
   EnrollmentRepository enrollmentRepository;

   @Autowired
   CourseRepository courseRepository;
   
   @Autowired
   StudentRepository studentRepository;
   
   @Test
   public void addStudentTest() throws Exception {
      
    Student s = null;
    do {
       s = studentRepository.findByEmail(TEST_USER_EMAIL);
       if (s != null)
          studentRepository.delete(s);
    } while (s != null);
  System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
  WebDriver driver = new ChromeDriver();
  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

try {

   driver.get(URL);
   Thread.sleep(SLEEP_DURATION);
   
//   // select the last of the radio buttons on the list of semesters page.
//   
//   WebElement we = driver.findElement(By.xpath("(//input[@type='radio'])[last()]"));
 //  we.click();
   
 WebElement we;
// don't need web element
 driver.get(URL);
// //must allow wait time for page to download
 Thread.sleep(SLEEP_DURATION);
// 


//   // Locate and click "Add Student" button
//   
 //  driver.findElement(By.xpath("//a")).click();
   Thread.sleep(SLEEP_DURATION);
//
//   // Locate and click "Add Course" button which is the first and only button on the page.
//   driver.findElement(By.xpath("//button")).click();
//   Thread.sleep(SLEEP_DURATION);
//
//   // enter course no and click Add button
//   
   driver.findElement(By.id("name!")).sendKeys(ALIAS_NAME);
   driver.findElement(By.id("email!")).sendKeys(TEST_USER_EMAIL);
   driver.findElement(By.id("Add!")).click();
   Thread.sleep(SLEEP_DURATION);
//
//   /*
//   * verify that new course shows in schedule.
//   * get the title of all courses listed in schedule
//   */ 
//
//   Course course = courseRepository.findById(TEST_COURSE_ID).get();
      Student student = studentRepository.findByEmail(TEST_USER_EMAIL);

   //check student is not null
   //assert not null student, not null email, alias name, student
     
      
      ALIAS_NAME.equals(student.getName());
      TEST_USER_EMAIL.equals(student.getEmail());
         
 
  assertEquals(student, "Student added but not listed in database.");
//   
//   // verify that enrollment row has been inserted to database.
//   
   Student ss = studentRepository.findByEmail(TEST_USER_EMAIL);
   assertNotNull(ss, "Student enrollment not found in database.");
//
} catch (Exception ex) {
   throw ex;
} finally {
//
//   // clean up database.
//   
   Student st = studentRepository.findByEmail(TEST_USER_EMAIL);
   if (st != null)
      studentRepository.delete(st);
//
   driver.close();
  driver.quit();
}
  

   }
   @Test
   public void addDuplicateStudent() throws Exception{
      System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
      WebDriver driver = new ChromeDriver();
      
      try {
         WebElement we;
         
         driver.get(URL);
         Thread.sleep(SLEEP_DURATION);
         
         
         driver.findElement(By.id("name!")).sendKeys(ALIAS_NAME);
         driver.findElement(By.id("email!")).sendKeys(TEST_USER_EMAIL);
         driver.findElement(By.id("Add!")).click();
         Thread.sleep(SLEEP_DURATION);
         

         Student students = studentRepository.findByEmail(TEST_USER_EMAIL);
         assertNotNull(students, "It is not null");
         
         ALIAS_NAME.equals(students.getName());
         TEST_USER_EMAIL.equals(students.getEmail());
         assertEquals(students,"The student already exists in the database" );
         
        
         
      }
      catch (Exception ex) {
         ex.printStackTrace();
         throw ex;
      }
      finally {
         driver.quit();
      }
   }
   
}



