package com.hyfacademy.service;


import com.hyfacademy.exception.EnrolmentException;
import com.hyfacademy.model.*;

import java.util.Scanner;

public class PlatformService {
    private static final int MAX_COURSES = 10;
    private static final int MAX_STUDENTS = 20;
    private static final int MAX_MENTORS = 5;

    private final Course[] courses = new Course[MAX_COURSES];
    private final Student[] students = new Student[MAX_STUDENTS];
    private final Mentor[] mentors = new Mentor[MAX_MENTORS];

    private static int courseCount = 0;
    private static int studentCount = 0;
    private static int mentorCount = 0;

    private final Scanner scanner = new Scanner(System.in);

    public void addCourse() {
        if (courseCount == MAX_COURSES) {
            System.out.printf("Cannot add more courses - limit reached(%d).%n", MAX_COURSES);
            return;
        }

        System.out.print("Select course type: \n1. Self paced course.\n2. Live cohort course.\nYour choice: ");
        int type;
        try {
            type = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid input. Select 1 or 2.");
            return;
        } finally {
        scanner.nextLine();
        }


        if (type != 1 && type != 2) {
            System.out.println("Invalid choice. Select 1 or 2.");
            return;
        }

        System.out.print("Please enter course name: ");
        String courseName = scanner.nextLine();


        if (type == 1) {
            System.out.print("Please enter estimate hours: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Hours must be a number.");
                scanner.nextLine();
                return;
            }

            int estimateHours = scanner.nextInt();
            scanner.nextLine();
            SelfPacedCourse spc1 = new SelfPacedCourse(courseName, MAX_STUDENTS, estimateHours);
            courses[courseCount] = spc1;
            courseCount++;
            System.out.println("Self paced course successfully created!");
        }

        if (type == 2) {
            System.out.print("Please enter start date: ");
            String startDate = scanner.nextLine();
            if (!startDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                System.out.println("Invalid date. Use format YYYY-MM-DD.");
                return;
            }

            System.out.print("Please enter end date: ");
            String endDate = scanner.nextLine();
            if (!endDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                System.out.println("Invalid date. Use format YYYY-MM-DD.");
                return;
            }

            LiveCohortCourse lcc1 = new LiveCohortCourse(courseName, MAX_STUDENTS, startDate, endDate);
            courses[courseCount] = lcc1;
            courseCount++;
            System.out.println("Live cohort course successfully created!");
        }

    }

    public void addStudent() {
        if (studentCount == MAX_STUDENTS) {
            System.out.printf("Cannot add more students - limit reached(%d).%n", MAX_STUDENTS);
            return;
        }

        System.out.print("Please enter student name: ");
        String studentName = scanner.nextLine();

        if (!studentName.matches("[A-Za-z ]+")) {
            System.out.println("Invalid name. Use letters only.");
            return;
        }

        System.out.print("Please enter student email: ");
        String studentEmail = scanner.nextLine();

        if (!studentEmail.contains("@")) {
            System.out.println("Invalid email format.");
            return;
        }


        Student s1 = new Student(studentName, studentEmail);
        students[studentCount] = s1;
        studentCount++;
        System.out.println("Student successfully created!");

    }

    public void addMentor() {
        if (mentorCount == MAX_MENTORS) {
            System.out.printf("Cannot add more mentors - limit reached(%d).%n", MAX_MENTORS);
            return;
        }
        System.out.print("Please enter mentor name: ");
        String mentorName = scanner.nextLine();

        if (!mentorName.matches("[A-Za-z ]+")) {
            System.out.println("Invalid name. Use letters only.");
            return;
        }

        System.out.print("Please enter mentor email: ");
        String mentorEmail = scanner.nextLine();

        if (!mentorEmail.contains("@")) {
            System.out.println("Invalid email format.");
            return;
        }

        System.out.print("Please enter mentor expertise: ");
        String mentorExpertise = scanner.nextLine();

        Mentor m1 = new Mentor(mentorName, mentorEmail, mentorExpertise);
        mentors[mentorCount] = m1;
        mentorCount++;
        System.out.println("Mentor successfully created!");
    }

    public void enrollStudent() {
        System.out.print("Please enter student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Please enter course ID: ");
        String courseId = scanner.nextLine();

        Student foundStudent = findStudentById(studentId);
        Course foundCourse = findCourseById(courseId);

        if (isInvalid(foundStudent, foundCourse)) return;

        try {
            foundCourse.enrol(foundStudent);
            System.out.println("Student enrolled successfully!");
        } catch (EnrolmentException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }


    public void updateProgress() {
        System.out.print("Please enter student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Please enter course ID: ");
        String courseId = scanner.nextLine();

        Student foundStudent = findStudentById(studentId);
        Course foundCourse = findCourseById(courseId);

        if (isInvalid(foundStudent, foundCourse)) return;


        System.out.print("Please enter progress value: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Progress must be a number between 0 and 100.");
            scanner.nextLine();
            return;
        }

        int progressValue = scanner.nextInt();
        scanner.nextLine();


        try {
            foundCourse.updateProgress(foundStudent, progressValue);
            System.out.println("Student progress updated successfully!");
        } catch (EnrolmentException e) {
            System.out.println("Error: " + e.getMessage());
        }


    }

    public void assignMentorToCourse() {
        System.out.print("Enter mentor ID: ");
        String mentorId = scanner.nextLine();

        Mentor mentor = findMentorById(mentorId);
        if (mentor == null) {
            System.out.println("Mentor not found");
            return;
        }

        System.out.print("Enter course ID: ");
        String courseId = scanner.nextLine();

        Course course = findCourseById(courseId);
        if (course == null) {
            System.out.println("Course not found");
            return;
        }

        if (!(course instanceof LiveCohortCourse)) {
            System.out.println("Only Live Cohort courses can have mentors.");
            return;
        }

        try {
            ((LiveCohortCourse) course).assignMentor(mentor);
            System.out.println("Mentor assigned successfully!");
        } catch (EnrolmentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public String viewAllCourses(){
        if(courseCount == 0){
            return "No courses available.";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("══════════════════════════════════════════════════════════════════\n");
        sb.append(String.format("  %-10s %-17s %-15s %-15s%n", "ID", "NAME", "TYPE", "CAPACITY"));
        sb.append("══════════════════════════════════════════════════════════════════\n");

        for (int i=0; i < courseCount; i++){
            Course c = courses[i];

            sb.append(String.format("  %-10s %-17s %-15s %-15s%n",
                    c.getCourseId(), c.getCourseName(), c.getCourseType(), c.capacityStatus()));
        }

        sb.append("══════════════════════════════════════════════════════════════════\n");

return sb.toString();

    }

    public String viewCourseReport(){
        System.out.print("Enter course ID: ");
        String courseId = scanner.nextLine();

        Course course = findCourseById(courseId);

        if(course == null){
            return "Course not found";
        }

        if(!(course instanceof Reportable)){
            return "This course type does not support reports.";
        }

        Reportable reportable = (Reportable) course;

        return reportable.generateReport();
    }

    public String viewAllStudents() {
        if (studentCount == 0) {
            return "No students available.";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("══════════════════════════════════════════════════════════════════\n");
        sb.append(String.format("  %-10s %-17s %-15s %-10s%n", "ID", "NAME", "EMAIL", "COURSES"));
        sb.append("══════════════════════════════════════════════════════════════════\n");

        for (int i = 0; i < studentCount; i++) {
            Student s = students[i];
            sb.append(String.format("  %-10s %-17s %-15s %5d%n",
                    s.getUserId(), s.getName(), s.getEmail(), s.getCourseCount()));

        }

        sb.append("══════════════════════════════════════════════════════════════════\n");
        return sb.toString();
    }

    public void run() {
        while (true) {
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║       HYF ACADEMY COURSE PLATFORM        ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.println("  1. Add course");
            System.out.println("  2. Add student");
            System.out.println("  3. Add mentor");
            System.out.println("  4. Enrol student in course");
            System.out.println("  5. Update student progress");
            System.out.println("  6. Assign mentor to course");
            System.out.println("  7. View all courses");
            System.out.println("  8. View course report");
            System.out.println("  9. View all students");
            System.out.println("  10. Exit");
            System.out.println("══════════════════════════════════════════");
            System.out.print("Choose an option: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Choose between 1 and 10.");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice){
                case 1 -> addCourse();
                case 2 -> addStudent();
                case 3 -> addMentor();
                case 4 -> enrollStudent();
                case 5 -> updateProgress();
                case 6 -> assignMentorToCourse();
                case 7 -> System.out.println(viewAllCourses());
                case 8 -> System.out.println(viewCourseReport());
                case 9 -> System.out.println(viewAllStudents());
                case 10 -> {
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid option. Choose between 1 and 10.");
            }
        }
    }


    //helpers

    private Student findStudentById(String id) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getUserId().equals(id)) {
                return students[i];
            }
        }
        return null;
    }

    private Course findCourseById(String id) {
        for (int i = 0; i < courseCount; i++) {
            if (courses[i].getCourseId().equals(id)) {
                return courses[i];
            }
        }
        return null;
    }

    private Mentor findMentorById(String id) {
        for (int i = 0; i < mentorCount; i++) {
            if (mentors[i].getUserId().equals(id)) {
                return mentors[i];
            }
        }
        return null;
    }

    private boolean isInvalid(Student s, Course c){
        if(s == null){
            System.out.println("Student not found");
            return true;
        }
        if(c == null){
            System.out.println("Course not found");
            return true;
        }
        return false;
    }
}
