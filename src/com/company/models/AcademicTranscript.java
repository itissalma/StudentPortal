package com.company.models;

import java.util.ArrayList;

public class AcademicTranscript {
    private ArrayList<StudentGrade> studentGrades = new ArrayList<>();

    public ArrayList<StudentGrade> getStudentGrades() {
        return studentGrades;
    }

    // Method to add a student's grade to the transcript
    public void addStudentGrade(StudentGrade studentGrade) {
        studentGrades.add(studentGrade);
    }
}
