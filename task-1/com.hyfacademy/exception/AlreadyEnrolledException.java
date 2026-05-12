package com.hyfacademy.exception;

public class AlreadyEnrolledException extends EnrolmentException {
    private String studentName;
    private String courseName;

    public AlreadyEnrolledException(String studentName, String courseName){
        super(String.format("%s is already enrolled in '%s'", studentName, courseName));
        this.studentName = studentName;
        this.courseName = courseName;
    }

    public String getStudentName(){return studentName;}
    public String getCourseName(){return courseName;}
}
