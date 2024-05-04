package com.company.models;

public class Professor{
    private String name;
    private int professorID;
    private String password; //to be implemented later
    private String universityName;
    private String department;

    public String getDepartment() {
        return department;
    }
    public int getProfessorID() {
        return professorID;
    }
    public String getName() {
        return name;
    }

    public Professor(String name, String department, String universityName, int professorID) {
        this.name = name;
        this.department = department;
        this.universityName = universityName;
        this.professorID = professorID;
    }

    //Professors can input grades for their students in the courses they teach.
    public void inputGrades(Student student, Class class1, String grade) {
        class1.getStudentCourseEnrollment().getStudentGrades().stream()
                .filter(studentGrade -> studentGrade.getStudent().getName().equals(student.getName()))
                .findFirst()
                .ifPresent(studentGrade -> {
                    studentGrade.setGrade(studentGrade.getStudent(), grade, class1, true);
                    student.getAcademicTranscript().addStudentGrade(studentGrade);
                });
    }

    public void takeAttendance(Student student, Class class1, Boolean attendance) {
        class1.getStudentCourseEnrollment().getStudentGrades().stream()
                .filter(studentGrade -> studentGrade.getStudent().getName().equals(student.getName()))
                .findFirst()
                .ifPresent(studentGrade -> {
                    if (attendance) {
                        studentGrade.addAttendance();
                    } else {
                        studentGrade.addAbsence();
                    }
                });
    }

}
