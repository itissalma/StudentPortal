package com.company.testing;

import com.company.models.*;
import com.company.models.Class;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UniversityTest {
    private University university;

    @BeforeEach
    void setUp() {
        university = new University("Test University", "Test Location");
    }

    @Test
    void testAddStudent() {
        //    public Student(String name, String universityName, int studentID) {
        Student student = new Student("Test Student", "Test University", 8001);
        university.addStudent(student);
        assertEquals(1, university.getStudents().size());
    }

    @Test
    void testAddCourse() {
        Course course = new Course("Test Course", "Test Department", "Test Description", 3);
        university.addCourse(course);
        assertEquals(1, university.getCourses().size());
    }

    @Test
    void testFindCourseByName() {
        Course course = new Course("Test Course", "Test Department", "Test Description", 3);
        university.addCourse(course);
        Course foundCourse = university.getCourseByName("Test Course");
        assertNotNull(foundCourse);
        assertEquals("Test Course", foundCourse.getName());
    }

    @Test
    void testAddClass() {
        Course course = new Course("Test Course", "Test Department", "Test Description", 3);
        university.addCourse(course);
        Class aClass = new Class(course, 1, new Semester("Test Semester"), new Professor("Test Professor", "Test Department", "Test University", 8001), 30, new Schedule(new ClassTiming("MR", "10:00-12:00"), "LOCATION TEST UNI"));
        university.addClass(aClass, course);
        assertEquals(1, university.getClasses().size());
    }

    @Test
    void testAddProfessor() {
        Professor professor = new Professor("Test Professor", "Test Department", "Test University", 8001);
        university.addProfessor(professor);
        assertEquals(1, university.getProfessors().size());
    }

    @Test
    void testFindProfessorByName() {
        Professor professor = new Professor("Test Professor", "Test Department", "Test University", 8001);
        university.addProfessor(professor);
        Professor foundProfessor = university.findProfessorByName("Test Professor");
        assertNotNull(foundProfessor);
        assertEquals("Test Professor", foundProfessor.getName());
    }

    @Test
    void testFindStudentByID() {
        Student student = new Student("Test Student", "Test University", 1);
        university.addStudent(student);

        Student foundStudent = university.findStudentByID(1);
        if (foundStudent != null) {
            assertEquals("Test Student", foundStudent.getName());
        } else {
            fail("Student not found");
        }
    }


}