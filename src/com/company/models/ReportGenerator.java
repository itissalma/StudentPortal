package com.company.models;

import java.util.ArrayList;

public class ReportGenerator {
    public static void generateStudentPerformanceReport(ArrayList<Student> students) {
        try {
            // Generate a report header
            System.out.println("=== Student Performance Report ===");
            if (students == null) {
                System.err.println("Error: Student list is null.");
                return;
            }
            System.out.println("Total Students: " + students.size());
            System.out.println("Student Name\t\tCourse\t\t\tGrade\tAttendance\tAbsence");

            // Iterate through each student and their grades
            students.stream()
                    .filter(student -> student != null && student.getAcademicTranscript() != null)
                    .forEach(student -> student.getAcademicTranscript().getStudentGrades().forEach(studentGrade -> {
                        String studentName = student.getName();
                        String courseName = studentGrade.getStudentClass().getCourse().getName();
                        String grade = studentGrade.getGrade();
                        int attendance = studentGrade.getAttendance();
                        int absence = studentGrade.getAbsence();

                        // Print student performance information with fixed column widths
                        System.out.printf("%-15s %-25s %-10s %-10d %-10d%n", studentName, courseName, grade, attendance, absence);
                    }));
        } catch (NullPointerException e) {
            // Handle any unexpected NullPointerExceptions
            System.err.println("Error: An unexpected NullPointerException occurred.");
        }
    }

}