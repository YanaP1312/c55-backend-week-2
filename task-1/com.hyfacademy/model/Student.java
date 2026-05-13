package com.hyfacademy.model;

import com.hyfacademy.exception.AlreadyEnrolledException;
import com.hyfacademy.exception.EnrolmentException;

public class Student extends User {
    private final Course[] enrolledCourses = new Course[5];
    private int courseCounter = 0;

    private static int studentCounter = 0;

    public Student(String name, String email){
        super(name, email, generateStudentId());
    }

    @Override
    public String getRole(){
        return "STUDENT";
    }

    public Course[] getCourses(){return enrolledCourses;}
    public int getCourseCount(){return courseCounter;}

    public void enrol(Course course){
        if(courseCounter == 5){
            throw new EnrolmentException(
                    String.format("%s own course list is full (5 courses max)", getName()));
        }

        for(Course c : enrolledCourses){
            if(c != null && c.equals(course)){
                throw new AlreadyEnrolledException(getName(), c.getCourseName());
            }
        }

        enrolledCourses[courseCounter] = course;
        courseCounter ++;
    }

    public int getProgress(String courseName){
        for(Course c : enrolledCourses) {
            if (c != null && c.getCourseName().equals(courseName)) {
                return c.getStudentProgress(this);
            }
        }
        throw new EnrolmentException(String.format(
                "%s doesn't enroll in '%s' course.", getName(), courseName));
    }

    public static String generateStudentId(){
        studentCounter ++;
        return String.format("STU-%03d", studentCounter);
    }



}
