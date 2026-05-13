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

    @Override
    public String generateReport() {
        StringBuilder sb = new StringBuilder();

        sb.append("══════════════════════════════════════════\n");
        sb.append("  COURSE REPORT — Live Cohort\n");
        sb.append("══════════════════════════════════════════\n");
        sb.append(String.format("  %-12s : %s%n", "ID", getCourseId()));
        sb.append(String.format("  %-12s : %s%n", "Name", getCourseName()));
        sb.append(String.format("  %-12s : %s to %s%n", "Schedule", startDate, endDate));
        sb.append(String.format("  %-12s : %s%n", "Mentor", mentor != null? mentor.getName() : "TBA" ));
        sb.append(String.format("  %-12s : %s%n", "Capacity", capacityStatus()));
        sb.append("──────────────────────────────────────────\n");
        sb.append("  STUDENT PROGRESS\n");
        sb.append("──────────────────────────────────────────\n");
        sb.append(studentReportListProgress());
        sb.append("──────────────────────────────────────────\n");
        sb.append(String.format("  %-10s : %.0f%%%n", "Avg Progress", getAverageCourseProgress()));
        sb.append("══════════════════════════════════════════\n");

        return sb.toString();
    }

}
