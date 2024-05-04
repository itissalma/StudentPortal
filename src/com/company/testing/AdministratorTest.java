package com.company.testing;

import static org.junit.jupiter.api.Assertions.*;
import com.company.models.*;
import com.company.models.Class;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

class AdministratorTest {
    private Administrator admin;
    private University university;

    @BeforeEach
    public void setup() {
        university = new University("Test University", "Test Location");
            admin = new Administrator("Test University", 1001, "admin1234");
    }

    @AfterEach
    public void tearDown() {
        System.setOut(System.out);
    }

    @Test
    public void testCreateCourse() {
        admin.createCourse(university, "Test Course", "Test Department", "Test Description", 3);
        assertEquals(1, university.getCourses().size());
        assertEquals("Test Course", university.getCourses().get(0).getName());
    }


    @Test
    public void testUpdateCourse() {
        Course course = new Course("Test Course", "Test Department", "Test Description", 3);
        university.addCourse(course);

        admin.updateCourse(university, "Test Course", "Updated Course", "Updated Department", "Updated Description");

        assertEquals("Updated Course", course.getName());
        assertEquals("Updated Department", course.getDepartment());
        assertEquals("Updated Description", course.getDescription());
    }

    @Test
    public void testAddProfessor() {
        admin.addProfessor(university, "Test Professor", "Test Department");
        assertEquals(1, university.getProfessors().size());
        assertEquals("Test Professor", university.getProfessors().get(0).getName());
    }

    @Test
    public void testRemoveProfessor() {
        // Add a professor to the university
        Professor professor = new Professor("Test Professor", "Test Department", "Test University", 8001);
        university.addProfessor(professor);

        admin.removeProfessor(university, 1);

        assertEquals(0, university.getProfessors().size());
    }

    @Test
    public void testEnrollStudent() {
        // Create and add a student to the university
        Student student = new Student("Test Student", "AUC", 1008);
        university.addStudent(student);

        Course course = new Course("Test Course", "Test Department", "Test Description", 3);
        university.addCourse(course);

        ClassTiming classTiming = new ClassTiming("UW", "11:00-12:00");
        String location = "LOCATION TEST";
        Schedule schedule = new Schedule(classTiming, location);

        com.company.models.Class myClass = new com.company.models.Class(course, 1, new Semester("Test Semester"), new Professor("Test Professor", "Test Department", "Test University", 8001), 30, schedule);
        university.addCourse(course);
        university.getCourses().get(0).addClass(myClass);

        // Ensure there are no students initially enrolled in the class
        assertEquals(0, myClass.getStudentCourseEnrollment().getStudentGrades().size());

        // Enroll the student in the class
        admin.enrollStudent(university, "Test Student", "Test Course", "Test Semester");

        // Get the class's student enrollment and grades
        StudentCourseEnrollment enrollment = myClass.getStudentCourseEnrollment();

        // Check if the student is enrolled
        assertEquals(1, enrollment.getStudentGrades().size());
        assertEquals("Test Student", enrollment.getStudentGrades().get(0).getStudent().getName());
    }



    @Test
    public void testEditCapacity() {
        // Add a course and a class to the university
        Course course = new Course("Test Course", "Test Department", "Test Description", 3);
        com.company.models.Class aClass = new com.company.models.Class(course, 1, new Semester("Test Semester"), new Professor("Test Professor", "Test Department", "Test University", 8001), 30, new Schedule(new ClassTiming("UW", "11:00-12:00"), "LOCATION TEST"));
        university.addCourse(course);
        university.getCourses().get(0).addClass(aClass);

        // Edit the class capacity
        admin.editCapacity(university, "Test Course", "Test Semester", 40, 1);

        // Check if the capacity is updated
        assertEquals(40, aClass.getMaxClassCapacity());
    }
}