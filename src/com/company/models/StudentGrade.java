package com.company.models;

public class StudentGrade {
    public String getGrade() {
        return grade;
    }

    public Student getStudent() {
        return student;
    }

    public void setGrade(Student student, String grade, Class studentClass, Boolean isCompleted) {
        this.studentClass = studentClass;
        this.grade = grade;
        this.isCompleted = isCompleted;
        this.student = student;
    }

    public Class getStudentClass() {
        return studentClass;
    }

    private Class studentClass;
    private String grade;
    private Student student;

    public int getAttendance() {
        return attendance;
    }

    private int attendance = 0;

    public int getAbsence() {
        return absence;
    }

    private int absence = 0;

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    private boolean isCompleted;

    public void addAttendance() {
        attendance++;
    }

    public void addAbsence() {
        absence++;
    }

    public StudentGrade(Student student, Class class1, String grade, boolean isCompleted) {
        this.grade = grade;
        this.student = student;
        this.isCompleted = isCompleted;
        this.studentClass = class1;
    }

    public double getNumericalGrade() {
        switch (grade) {
            case "A": return 4.0;
            case "A-": return 3.7;
            case "B+": return 3.3;
            case "B": return 3.0;
            case "B-": return 2.7;
            case "C+": return 2.3;
            case "C": return 2.0;
            case "C-": return 1.7;
            case "D+": return 1.3;
            case "D": return 1.0;
            case "F": return 0.0;
            default:
                return 0.01; // Default value for an unknown grade
        }
    }
    //private Class studentClass;
}
