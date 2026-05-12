package com.hyfacademy.exception;

public class CourseFullException extends EnrolmentException{
    private final String courseName;
    private final int maxCapacity;

    public CourseFullException(String courseName, int maxCapacity){
        super(String.format("Course '%s' is full (max: %d students)", courseName, maxCapacity));
        this.courseName = courseName;
        this.maxCapacity = maxCapacity;
    }

    public String getCourseName(){return courseName;}
    public int getMaxCapacity(){return maxCapacity;}
}
