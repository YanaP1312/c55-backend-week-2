package com.hyfacademy.model;

import com.hyfacademy.exception.AlreadyEnrolledException;
import com.hyfacademy.exception.CourseFullException;

public abstract class Course {
    private final String courseName;
    private final String courseId;
    private int maxStudents;
    private int enrolledCount = 0;
    private int[] studentProgress = new int[maxStudents];
    private Student[] enrolledStudent = new Student[maxStudents];
    static int courseCounter = 0;

    public Course(String courseName, int maxStudents){
        this.courseName = courseName;
        this.maxStudents = maxStudents;
        this.courseId = generateCourseId();
    }

    public String getCourseName(){return courseName;}
    public int getMaxStudents(){return maxStudents;}
    public String getCourseId(){return courseId;}
    public abstract String getCourseType();
    public abstract String getScheduleInfo();

    @Override
    public String toString(){
        return String.format("[%s] %s (%s) | Enrolled: %d/%d", courseId, courseName, getCourseType(), enrolledCount, maxStudents );
    }

    public void enrol(Student student){
        if(enrolledCount == maxStudents){
            throw new CourseFullException(courseName, maxStudents);
        }

        for(Course c : student.getCourses()){
            if(c !=null && c.equals(this)){
                throw new AlreadyEnrolledException(student.getName(), courseName);
            }
        }
            student.enrol(this);
        enrolledStudent[enrolledCount] = student;
        studentProgress[enrolledCount] = 0;
        enrolledCount ++;
    }



    public static String generateCourseId(){
        courseCounter ++;
        return String.format("CRS-%03d", courseCounter);
    };


}
