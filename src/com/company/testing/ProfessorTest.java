package com.company.testing;

import com.company.models.*;
import com.company.models.Class;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProfessorTest {
    private Professor professor;
    private University university;
    private Student student;
    private Class class1;

    @BeforeEach
    public void setUp() {
        // Create a new Professor object and a University object for testing
        university = new University("Test University", "Test Location");
        professor = new Professor("Professor 1", "CS", "Test University", 12345);

        // Add sample data to the University
        Course course1 = new Course("Course 1", "Department 1", "Description 1", 3);
        Semester semester1 = new Semester("Spring 2023");
        university.addCourse(course1);
        course1.addOfferedInSemester(semester1);

        // Create a student and enroll them in the class taught by the professor
        student = new Student("John Doe", "Test University", 54321);
        class1 = new Class(course1, 1, semester1, professor, 30, new Schedule(new ClassTiming("Monday", "8:00 AM"), "Location 1"));
        student.enrollInCourse(university, "Course 1", "Spring 2023");
    }

    @Test
    public void testInputGrades() {
        // Enroll the student first
        class1.getStudentCourseEnrollment().enrollStudent(student);

        // Check if the professor can input grades for the enrolled student
        professor.inputGrades(student, class1, "A");

        // Check if StudentGrades list is not empty
        ArrayList<StudentGrade> studentGrades = class1.getStudentCourseEnrollment().getStudentGrades();
        assertFalse(studentGrades.isEmpty());

        // Check the grade for the student
        if (!studentGrades.isEmpty()) {
            StudentGrade studentGrade = studentGrades.get(0);
            assertEquals("A", studentGrade.getGrade());
        }
    }


    @Test
    public void testInputGradesStudentNotFound() {
        // Create a student who is not enrolled
        Student otherStudent = new Student("Jane Smith", "Test University", 67890);

        // Attempt to input grades for a student who is not enrolled
        professor.inputGrades(otherStudent, class1, "B");

        // Check if there are no student grades because the student was not found
        ArrayList<StudentGrade> studentGrades = class1.getStudentCourseEnrollment().getStudentGrades();
        assertTrue(studentGrades.isEmpty());
    }


    @Test
    public void testTakeAttendance() {
        // Enroll the student first
        class1.getStudentCourseEnrollment().enrollStudent(student);

        // Check if the professor can take attendance for the enrolled student
        professor.takeAttendance(student, class1, true); // Marking attendance as 'true'

        // Check if StudentGrades list is not empty
        ArrayList<StudentGrade> studentGrades = class1.getStudentCourseEnrollment().getStudentGrades();
        assertFalse(studentGrades.isEmpty());

        // Check the attendance for the student
        if (!studentGrades.isEmpty()) {
            StudentGrade studentGrade = studentGrades.get(0);
            assertEquals(1, studentGrade.getAttendance());
        }
    }



    @Test
    public void testTakeAttendanceStudentNotFound() {
        // Attempt to take attendance for a student who is not enrolled
        Student otherStudent = new Student("Jane Smith", "Test University", 67890);
        professor.takeAttendance(otherStudent, class1, true);

        // Check if the StudentCourseEnrollment and StudentGrades are empty
        assertTrue(class1.getStudentCourseEnrollment().getStudentGrades().isEmpty());
    }
}