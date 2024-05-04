package com.company.testing;

import static org.junit.jupiter.api.Assertions.*;

import com.company.UniversityManager;
import com.company.models.*;
import com.company.models.Class;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StudentTest {
    private Student student;
    private University university;
    private Class class1; // Declare class1 as a field
    private  Class class2; // Declare class2 as a field

    @BeforeEach
    public void setUp() {
        // Create a new Student object and a University object for testing
        university = new University("Test University", "Test Location");
        student = new Student("John Doe", "Test University", 12345);

        // Add sample data to the University
        Course course1 = new Course("Course 1", "Department 1", "Description 1", 3);
        Course course2 = new Course("Course 2", "Department 2", "Description 2", 4);
        Semester semester1 = new Semester("Spring 2023");
        Semester semester2 = new Semester("Fall 2023");
        university.addCourse(course1);
        university.addCourse(course2);
        course1.addOfferedInSemester(semester1);
        course1.addOfferedInSemester(semester2);

        // Enroll the student in some classes
        class1 = new Class(course1, 1, semester1, new Professor("Professor 1", "CS", "AUC", 9002), 30, new Schedule(new ClassTiming("Monday", "8:00 AM"), "Location 1"));
        class2 = new Class(course1, 2, semester1, new Professor("Professor 2", "Math", "AUC", 9001), 30, new Schedule(new ClassTiming("Tuesday", "10:00 AM"), "Location 2"));
        university.addClass(class1, course1);
        university.addClass(class2, course1);
        student.enrollInCourse(university, "Course 1", "Spring 2023");
        student.enrollInCourse(university, "Course 1", "Spring 2023");
    }

    @Test
    public void testGetAvailableCourses() {
        ArrayList<Course> availableCourses = university.getAvailableCourses("Spring 2023");
        assertEquals(1, availableCourses.size());
    }

    @Test
    public void testSearchCourseByDepartment() {
        ArrayList<Course> matchingCourses = university.searchCourseByDepartment( "Department 1");
        assertEquals(1, matchingCourses.size());
    }

    @Test
    public void testSearchCourseByProfessor() {
        ArrayList<Course> matchingCourses = university.searchCourseByProfessor( "Professor 1");
        assertEquals(1, matchingCourses.size());
    }

    @Test
    public void testAddCourseToFavorites() {
        student.addCourseToFavorites(university, "Course 2");
        assertEquals(1, student.getFavoriteCourses().size());
    }

    @Test
    public void testRemoveCourseFromFavorites() {
        student.addCourseToFavorites(university, "Course 2");
        student.removeCourseFromFavorites(university, "Course 2");
        assertEquals(0, student.getFavoriteCourses().size());
    }

    @Test
    public void testGetCourseDetails() {
        // Redirect console output to capture printed details
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        university.getCourseDetails( "Course 1");
        String output = outContent.toString();
        assertTrue(output.contains("The course name is Course 1"));
        assertTrue(output.contains("and the department is Department 1"));
        assertTrue(output.contains("The course description is Description 1"));

        // Reset console output
        System.setOut(System.out);
    }

    @Test
    public void testGetCourseSchedule() {
        // Redirect console output to capture printed schedule
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        //System.out.println("The course name is " + course.getName() + ": \n");
        // System.out.println("The semester is " + class1.getSemester().getName() + ", the class timings are " + class1.getSchedule().getClassTimings() + ", and the location is " + class1.getSchedule().getLocation() + "\n");

        university.getCourseSchedule("Spring 2023");
        String output = outContent.toString();
        assertTrue(output.contains("The course name is Course 1"));
        assertTrue(output.contains("The semester is Spring 2023"));
        assertTrue(output.contains("class timings are Monday 8:00 AM"));
        assertTrue(output.contains("and the location is Location 1"));

        // Reset console output
        System.setOut(System.out);
    }

    @Test
    public void testEnrollInCourse() {
        // Redirect console output to capture printed enrollment status
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        student.enrollInCourse(university, "Course 1", "Spring 2023");
        student.enrollInCourse(university, "Course 1", "Spring 2023");
        student.enrollInCourse(university, "Course 1", "Spring 2023"); // Attempt to enroll in the same class multiple times

        String output = outContent.toString();
        assertTrue(output.contains(""));
        // You might need to add additional assertions here based on your requirements

        // Reset console output
        System.setOut(System.out);
    }

    @Test
    public void testGetTranscript() {
        // Enroll the student in a class and assign a grade
        student.enrollInCourse(university, "Course 1", "Spring 2023");
        StudentGrade grade = new StudentGrade(student, class1,"A", true);
        student.getAcademicTranscript().addStudentGrade(grade);

        // Redirect console output to capture printed transcript
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        student.getTranscript(university);
        String output = outContent.toString();
        assertTrue(output.contains("The course name is Course 1"));
        assertTrue(output.contains("the semester is Spring 2023"));
        assertTrue(output.contains("and the grade is A"));

        // Reset console output
        System.setOut(System.out);
    }


    @Test
    public void testGetGPAWithMultipleCoursesAndGrades() {
        // Enroll the student in two classes and assign grades
        student.enrollInCourse(university, "Course 1", "Spring 2023");
        student.enrollInCourse(university, "Course 2", "Spring 2023");

        // Assign grades for both classes
        Class class1 = university.getCourses().get(0).getClasses().get(0);
        Class class2 = university.getCourses().get(0).getClasses().get(1);

        StudentGrade grade1 = new StudentGrade(student, class1, "A", true);
        StudentGrade grade2 = new StudentGrade(student, class2, "B", true);

        student.getAcademicTranscript().addStudentGrade(grade1);
        student.getAcademicTranscript().addStudentGrade(grade2);

        // Redirect console output to capture printed GPA
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        student.getGPA(university);
        String output = outContent.toString();

        // Check if the expected GPA is printed
        assertTrue(output.contains("The semester is Spring 2023"));
        assertTrue(output.contains("and the GPA is 3.5")); // Assuming (4.0 + 3.0) / 2 = 3.5 GPA

        // Reset console output
        System.setOut(System.out);
    }


    @Test
    public void testGetHistoricalCourseSchedule() {
        // Enroll the student in a class, assign a grade, and complete the class
        student.enrollInCourse(university, "Course 1", "Spring 2023");
        Class class1 = university.getCourses().get(0).getClasses().get(0);
        StudentGrade grade = new StudentGrade(student, class1, "A", true);
        grade.setCompleted(true);
        student.getAcademicTranscript().addStudentGrade(grade);

        // Redirect console output to capture historical course schedule and performance
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        student.getHistoricalCourseSchedule(university);
        String output = outContent.toString();
        assertTrue(output.contains("The course name is Course 1"));
        assertTrue(output.contains("the semester is Spring 2023"));
        assertTrue(output.contains("and the grade is A"));
        assertTrue(output.contains("The course schedule is Monday 8:00 AM"));
        assertTrue(output.contains("and the location is Location 1"));

        // Reset console output
        System.setOut(System.out);
    }
}