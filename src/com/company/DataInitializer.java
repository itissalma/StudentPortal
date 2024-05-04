package com.company;

import com.company.models.*;
import com.company.models.Class;

public class DataInitializer {
    public static void initializeData(University university) {
        // Create the university
        String uniName = "The American University in Cairo";

        // Create sample students, professors, and courses
        Administrator admin = new Administrator(uniName, 1001, "admin123");

        Student student1 = new Student("Salma", uniName, 901);
        Student student2 = new Student("Ahmed", uniName, 902);
        Student student3 = new Student("Youssef", uniName, 903);
        Student student4 = new Student("Yasmine", uniName, 904);
        Student student5 = new Student("Ali", uniName, 905);
        Student student6 = new Student("Sarah", uniName, 906);

        Professor professor1 = new Professor("Dr. Sherif", "Computer Science", uniName, 8001);
        Professor professor2 = new Professor("Dr. Wafik", "Mathematics", uniName, 8002);
        Professor professor3 = new Professor("Dr. Emilio", "Sociology", uniName, 8003);
        Professor professor4 = new Professor("Dr. Seif", "Computer Science", uniName, 8004);

        Semester fall2023 = new Semester("Fall 2023");
        university.getSemesters().add(fall2023);
        Semester spring2023 = new Semester("Spring 2023");
        university.getSemesters().add(spring2023);
        Semester fall2022 = new Semester("Fall 2022");
        university.getSemesters().add(fall2022);

        ClassTiming classTiming1 = new ClassTiming("UW", "10:00-11:15");
        ClassTiming classTiming2 = new ClassTiming("MR", "10:00-11:15");
        ClassTiming classTiming3 = new ClassTiming("MR", "11:30-11:45");
        Schedule schedule1 = new Schedule(classTiming1, "CP31");
        Schedule schedule2 = new Schedule(classTiming2, "C101");
        Schedule schedule3 = new Schedule(classTiming3, "CP72");

        Course course1 = new Course("Computer Science 101", "Computer Science", "This class is an intro to CS", 2);
        Class class1 = new Class(course1, 1, fall2023, professor1, 30, schedule1);
        Class class2 = new Class(course1, 2, fall2023, professor1, 20, schedule2);
        Course course2 = new Course("Math 101", "Mathematics", "This class is an intro to Math", 3);
        Class class3 = new Class(course2, 1, fall2023, professor2, 30, schedule3);
        Course course3 = new Course("Sociology for Mathematicians", "Sociology", "This is a sociology courses for mathematicians.", 3);
        Class class4 = new Class(course3, 1, spring2023, professor3, 25, schedule1);
        Course course4 = new Course("Fundamentals of Computing II", "Computer Science", "This is a secondary computer science course", 2);
        Class class5 = new Class(course4, 1, fall2022, professor1, 30, schedule1);
        Class class6 = new Class(course4, 2, fall2022, professor1, 30, schedule2);
        Class class7 = new Class(course4, 3, fall2022, professor4, 30, schedule3);

        // Add students, professors, and courses to the university
        university.addStudent(student1);
        university.addStudent(student2);
        university.addStudent(student3);
        university.addStudent(student4);
        university.addStudent(student5);
        university.addStudent(student6);

        university.addProfessor(professor1);
        university.addProfessor(professor2);
        university.addProfessor(professor3);
        university.addProfessor(professor4);

        university.addCourse(course1);
        university.addCourse(course2);
        university.addCourse(course3);
        university.addCourse(course4);

        university.addClass(class1, course1);
        university.addClass(class2, course1);
        university.addClass(class3, course2);
        university.addClass(class4, course3);
        university.addClass(class5, course4);
        university.addClass(class6, course4);
        university.addClass(class7, course4);

        // Enroll students in courses
        student1.enrollInCourse(university, course1.getName(), "Fall 2023");
        student1.enrollInCourse(university, course2.getName(), "Fall 2023");
        student1.enrollInCourse(university, course3.getName(), "Spring 2023");
        student1.enrollInCourse(university, course4.getName(), "Fall 2022");
        student2.enrollInCourse(university, course2.getName(), "Fall 2023");
        student3.enrollInCourse(university, course3.getName(), "Spring 2023");
        student4.enrollInCourse(university, course1.getName(), "Fall 2023");
        student5.enrollInCourse(university, course2.getName(), "Fall 2023");
        student6.enrollInCourse(university, course3.getName(), "Spring 2023");

        // Input student grades and attendance data
        professor1.inputGrades(student1, class1, "A");
        professor1.inputGrades(student1, class4, "B");
        professor1.inputGrades(student1, class5, "A");
        professor2.inputGrades(student1, class3, "A");
        professor1.inputGrades(student4, class1, "B");

        professor1.takeAttendance(student1, class1, true);
        professor1.takeAttendance(student1, class1, true);
        professor1.takeAttendance(student1, class1, true);
        professor1.takeAttendance(student1, class1, false);
        professor1.takeAttendance(student4, class1, true);
    }
}
