package com.company.models;

import java.util.ArrayList;

public class StudentCourseEnrollment {
    private Class studentClass;

    public ArrayList<StudentGrade> getStudentGrades() {
        return studentGrades;
    }

    private ArrayList<StudentGrade> studentGrades = new ArrayList<>();

    //enroll student in course
    public void enrollStudent(Student student) {
        StudentGrade studentGrade = new StudentGrade( student,studentClass, "N/A", false);
        studentGrades.add(studentGrade);
    }
}
