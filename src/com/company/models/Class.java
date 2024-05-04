package com.company.models;

public class Class {

    private Course course;
    private int sectionNumber;
    private Semester semester;
    private Professor professor;
    private Schedule schedule;
    private StudentCourseEnrollment studentCourseEnrollment;
    private int maxClassCapacity;
    private int currentClassCapacity = 0;

    public Class(Course course, int sectionNumber, Semester semester, Professor professor, int maxClassCapacity, Schedule schedule) {
        this.course = course;
        this.sectionNumber = sectionNumber;
        this.professor = professor;
        this.schedule = schedule;
        this.maxClassCapacity = maxClassCapacity;
        this.studentCourseEnrollment = new StudentCourseEnrollment();
        this.semester = semester;
        course.addOfferedInSemester(semester);
    }

    public void enrollStudent(Student student) throws ClassFullException {
        if (currentClassCapacity >= maxClassCapacity) {
            throw new ClassFullException("Class is already full. Cannot enroll more students.");
        }
        studentCourseEnrollment.enrollStudent(student);
        currentClassCapacity++;
    }

    public Course getCourse() {
        return course;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public Semester getSemester() {
        return semester;
    }

    public Professor getProfessor() {
        return professor;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public StudentCourseEnrollment getStudentCourseEnrollment() {
        return studentCourseEnrollment;
    }

    public int getMaxClassCapacity() {
        return maxClassCapacity;
    }

    public int getCurrentClassCapacity() {
        return currentClassCapacity;
    }

    public void setMaxClassCapacity(int maxClassCapacity) {
        this.maxClassCapacity = maxClassCapacity;
    }

    public boolean isFull() {
        return currentClassCapacity >= maxClassCapacity;
    }

    public class ClassFullException extends Exception {
        public ClassFullException(String message) {
            super(message);
        }
    }
}

