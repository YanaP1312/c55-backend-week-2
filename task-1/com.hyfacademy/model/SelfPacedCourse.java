package com.hyfacademy.model;

import com.hyfacademy.service.Enrollable;
import com.hyfacademy.service.Reportable;

public class SelfPacedCourse extends Course implements Reportable {
    private final int estimatedHours;

    public SelfPacedCourse(String courseName, int maxStudents, int estimateHours){
        super(courseName, maxStudents);
        this.estimatedHours = estimateHours;
    }

    public int getEstimateHours(){return estimatedHours;}

    @Override
    public String getCourseType(){
        return "Self-Paced";
    };

   @Override
    public String getScheduleInfo(){
        return String.format("Estimated: %d hours — complete at your own pace", estimatedHours);
    };

   @Override
    public String generateReport() {
       StringBuilder sb = new StringBuilder();

        sb.append("══════════════════════════════════════════\n");
        sb.append("  COURSE REPORT — Self-Paced\n");
        sb.append("══════════════════════════════════════════\n");
        sb.append(String.format("  %-12s : %s%n", "ID", getCourseId()));
        sb.append(String.format("  %-12s : %s%n", "Name", getCourseName()));
        sb.append(String.format("  %-12s : %s%n", "Capacity", capacityStatus()));
        sb.append(String.format("  %-12s : %s%n", "Est. Hours", estimatedHours));
        sb.append("──────────────────────────────────────────");
        sb.append("  STUDENT PROGRESS\n");
        sb.append("──────────────────────────────────────────");

        for (int i=0; i < getEnrolledCount(); i++){
            Student s = getEnrolledStudent()[i];
            int progress = getStudentProgress(s);

            sb.append(String.format("  %-8s %-18s %3d%%   %s%n",
                    s.getUserId(), s.getName(), progress, getProgressBar(s)));
        }

        sb.append("──────────────────────────────────────────");

        int totalStudentsProgress = 0;
        for(int i=0; i < getEnrolledCount(); i++){
            Student[] students = getEnrolledStudent();
            totalStudentsProgress += getStudentProgress(students[i]);
        }
        double avgProgress = (double) totalStudentsProgress / getEnrolledCount();
        sb.append(String.format("  %-10s : %.2f%n", "Avg Progress", avgProgress));
        sb.append("══════════════════════════════════════════\n");

        return sb.toString();
    }
}
