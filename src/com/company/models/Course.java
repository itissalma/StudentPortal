package com.company.models;

import java.util.ArrayList;

public class Course {
    private int numOfCredits;
    private String name;
    private String department;
    private String description;
    private ArrayList<Semester> offeredInSemesters;
    private ArrayList<Class> classes;

    public Course(String courseName, String department, String description, int numOfCredits) {
        this.name = courseName;
        this.department = department;
        this.description = description;
        this.numOfCredits = numOfCredits;
        this.offeredInSemesters = new ArrayList<>();
        this.classes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Semester> getOfferedInSemesters() {
        return offeredInSemesters;
    }

    public void setOfferedInSemesters(ArrayList<Semester> offeredInSemesters) {
        this.offeredInSemesters = offeredInSemesters;
    }

    public void addOfferedInSemester(Semester semester) {
        offeredInSemesters.add(semester);
    }

    public ArrayList<Class> getClasses() {
        return classes;
    }

    public void addClass(Class newClass) {
        classes.add(newClass);
    }

    public int getNumOfCredits() {
        return numOfCredits;
    }

    public void setNumOfCredits(int numOfCredits) {
        this.numOfCredits = numOfCredits;
    }

    public Semester findSemesterByName(String semesterName) {
        return offeredInSemesters.stream()
                .filter(semester -> semester.getName().equals(semesterName))
                .findFirst()
                .orElse(null);
    }

    public Class findClassByStudent(Student student) {
        return classes.stream()
                .filter(class1 -> class1.getStudentCourseEnrollment().getStudentGrades().stream()
                        .anyMatch(studentGrade -> studentGrade.getStudent().equals(student)))
                .findFirst()
                .orElse(null);
    }

    public Class findClass(int sectionNumber) {
        return classes.stream()
                .filter(class1 -> class1.getSectionNumber() == sectionNumber)
                .findFirst()
                .orElse(null);
    }

}
