package com.hyfacademy;


import com.hyfacademy.model.Mentor;
import com.hyfacademy.model.Student;

public class Main {
   public static void main(String[] args) {


        Student s1 = new Student("Alex Lirman", "dffd@bvfd.com");
        Mentor m1 = new Mentor("Anna Chernenko", "fvfdvfd@re.com", "docent");


        System.out.println(s1.getRole());
        System.out.println(s1.getName());
        System.out.println(s1.getEmail());
        System.out.println(s1.getSummary());
        System.out.println(m1.getRole());
//        System.out.println(m1.getAssignedCourses());
        System.out.println(m1.getName());
        System.out.println(m1.getSummary());
        System.out.println(m1.getExpertise());



    }
}
