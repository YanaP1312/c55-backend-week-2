package com.hyfacademy.model;

import com.hyfacademy.exception.AlreadyEnrolledException;
import com.hyfacademy.exception.CourseFullException;
import com.hyfacademy.exception.EnrolmentException;
import com.hyfacademy.exception.InvalidProgressException;

public abstract class Course {
    private final String courseName;
    private final String courseId;
    private int maxStudents;

    private int enrolledCount = 0;

    private int[] studentProgress;
    private Student[] enrolledStudent;

    static int courseCounter = 0;

    public Course(String courseName, int maxStudents){
        this.courseName = courseName;
        this.maxStudents = maxStudents;
        this.courseId = generateCourseId();

        this.studentProgress = new int[maxStudents];
        this.enrolledStudent = new Student[maxStudents];
    }

    public String getCourseName(){return courseName;}
    public int getMaxStudents(){return maxStudents;}
    public String getCourseId(){return courseId;}

    public abstract String getCourseType();
    public abstract String getScheduleInfo();

    @Override
    public String toString(){
        return String.format("[%s] %s (%s) | Enrolled: %d/%d",
                courseId, courseName, getCourseType(), enrolledCount, maxStudents );
    }

    public void enrol(Student student){
        if(isFull()){
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

    public void updateProgress(Student student, int progress){
        if(progress < 0 || progress > 100){
            throw new InvalidProgressException(progress);
        }

        for ( int i = 0; i < enrolledCount; i++){
            if(enrolledStudent[i].getUserId().equals(student.getUserId())){
                studentProgress[i] = progress;
                return;
            }
        }
        throw new EnrolmentException(String.format(
                "%s doesn't enroll in course %s", student.getName(), courseName));
    }

    public int getStudentProgress(Student student) {
        for (int i = 0; i < enrolledCount; i++) {
            if (enrolledStudent[i].getUserId().equals(student.getUserId())) {
                return studentProgress[i];
            }
        }
        throw new EnrolmentException(String.format(
                "%s doesn't enroll in course %s", student.getName(), courseName));
    }

    public boolean isFull(){
        return enrolledCount == maxStudents;
    }

    public static String generateCourseId(){
        courseCounter ++;
        return String.format("CRS-%03d", courseCounter);
    };
}
