package com.hyfacademy;


import com.hyfacademy.model.Mentor;
import com.hyfacademy.model.SelfPacedCourse;
import com.hyfacademy.model.Student;

public class Main {
   public static void main(String[] args) {


        Student s1 = new Student("Alex Lirman", "dffd@bvfd.com");
        Mentor m1 = new Mentor("Anna Chernenko", "fvfdvfd@re.com", "docent");
        SelfPacedCourse c1 = new SelfPacedCourse("Java Script", 20, 60);
        s1.enrol(c1);

//        System.out.println(s1.getRole());
//        System.out.println(s1.getName());
//        System.out.println(s1.getEmail());
//        System.out.println(s1.getSummary());
//        System.out.println(m1.getRole());
//        System.out.println(m1.getAssignedCourses());
//        System.out.println(m1.getName());
//        System.out.println(m1.getSummary());
//        System.out.println(m1.getExpertise());
        System.out.println(c1.getCourseType());



    }
}
