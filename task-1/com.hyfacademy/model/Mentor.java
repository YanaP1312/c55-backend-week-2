package com.hyfacademy.model;

import com.hyfacademy.exception.EnrolmentException;

public class Mentor extends User {
    private final String expertise;
    private final Course[] assignedCourses = new Course[3];
    private int courseCounter = 0;

    private static int mentorCounter = 0;

    public Mentor(String name, String email, String expertise){
        super(name, email, generateMentorId());
        this.expertise = expertise;
    }

    public String getExpertise(){return expertise;}
    public Course[] getAssignedCourses(){return assignedCourses;}

    @Override
    public String getRole(){return "MENTOR";}

   public void assignToCourse(Course course){
        if(courseCounter == 3){
            throw new EnrolmentException(
                    String.format("Mentor %s cannot be assigned to more than 3 courses", getName())
            );
        }

        for(Course c : assignedCourses){
            if(c!=null && c.equals(course)){
                throw new EnrolmentException(
                        String.format("Mentor %s has already been assigned to %s course", getName(), course.getCourseName()));
            }
        }

        assignedCourses[courseCounter] = course;
        courseCounter++;
   }

    public static String generateMentorId(){
        mentorCounter ++;
        return String.format("COA-%03d", mentorCounter);
    }
}
