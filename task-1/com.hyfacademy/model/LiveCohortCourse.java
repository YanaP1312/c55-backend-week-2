package com.hyfacademy.model;

import com.hyfacademy.service.Reportable;

public class LiveCohortCourse extends Course implements Reportable {
    private final String startDate;
    private final String endDate;
    private Mentor mentor;

    public LiveCohortCourse(String courseName, int maxStudents, String startDate, String endDate){
        super(courseName, maxStudents);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartDate(){return startDate;}
    public String getEndDate(){return endDate;}
    public Mentor getMentor(){return mentor;}

    @Override
    public String getCourseType() {
        return "Live Cohort";
    }

    @Override
    public String getScheduleInfo(){
        return String.format("%s to %s | Mentor: %s",
                startDate, endDate, mentor != null? mentor.getName() : "TBA");
    }

    public void assignMentor(Mentor mentor){
        this.mentor = mentor;
        mentor.assignToCourse(this);
    }

    public String generateReport() {
    }

}
