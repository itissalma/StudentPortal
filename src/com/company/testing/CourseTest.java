package com.company.testing;

import com.company.models.*;
import com.company.models.Class;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    private Course course;

    @BeforeEach
    public void setUp() {
        // Create a new Course object before each test
        course = new Course("Test Course", "Test Department", "Test Description", 3);
    }
    @Test
    public void testAddOfferedInSemester() {
        Semester semester = new Semester("Spring 2023");
        course.addOfferedInSemester(semester);
        ArrayList<Semester> offeredInSemesters = course.getOfferedInSemesters();
        assertEquals(1, offeredInSemesters.size());
        assertEquals("Spring 2023", offeredInSemesters.get(0).getName());
    }

    @Test
    public void testFindSemesterByName() {
        Semester semester1 = new Semester("Spring 2023");
        Semester semester2 = new Semester("Fall 2023");
        course.addOfferedInSemester(semester1);
        course.addOfferedInSemester(semester2);

        assertEquals(semester1, course.findSemesterByName("Spring 2023"));
        assertEquals(semester2, course.findSemesterByName("Fall 2023"));
        assertNull(course.findSemesterByName("Summer 2023"));
    }

    @Test
    public void testFindClassByStudent() {
        Student student = new Student("John Doe", "AUC", 8001);
        Student student2 = new Student("Jane Smith", "AUC",8002 );
        Semester semester = new Semester("Spring 2023");
        Class class1 = new Class(course, 1, semester, new Professor("Dr. Wafik", "Math", "AUC", 9001), 30, new Schedule(new ClassTiming("MWF", "10:00-11:00"), "Test Location"));
        Class class2 = new Class(course, 2, semester, new Professor("Dr. Haifa", "Film", "AUC", 9002), 30, new Schedule(new ClassTiming("TTh", "14:00-15:00"), "Test Location2"));
        class1.getStudentCourseEnrollment().enrollStudent(student);
        class2.getStudentCourseEnrollment().enrollStudent(student2);
        course.addClass(class1);
        course.addClass(class2);

        assertEquals(class1, course.findClassByStudent(student));
        assertEquals(class2, course.findClassByStudent(student2));
        assertNull(course.findClassByStudent(new Student("Salma Aly", "AUC", 8003)));
    }
}