package com.cst438.domain;

public class StudentDTO {
  
   public int id;
   public String student_email;
   public String student_name;
   public int student_id;
   public int statusCode;
   public String status;


   @Override
   public String toString() {
      return "StudentDTO [student_email=" + student_email + ", student_name= " + student_name + ", student_id=" + student_id + ",  ]";
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      StudentDTO other = (StudentDTO) obj;
      if (student_email == null) {
         if (other.student_email != null)
            return false;
      } else if (!student_email.equals(other.student_email))
         return false;
      if (student_id != other.student_id)
         return false;
      if (student_name != other.student_name)
         return false;
      return true;
   }
   
}
