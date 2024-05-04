package com.company;
import java.util.ArrayList;
import java.util.Scanner;

import com.company.models.*;
import com.company.models.Class;

public class Main {

    public static void main(String[] args) {

        String uniName = "The American University in Cairo";
        String uniLocation = "New Cairo, Egypt";
        University university = new University(uniName, uniLocation);

        // Add the university to the UniversityManager
        UniversityManager universityManager = UniversityManager.getInstance();
        universityManager.addUniversity(university);


        // Create sample students, professors, and courses
        Student student1 = new Student("Salma", uniName, 1);
        Student student2 = new Student("Ahmed", uniName, 2);
        Student student3 = new Student("Youssef", uniName, 3);
        Student student4 = new Student("Yasmine", uniName, 4);
        Student student5 = new Student("Ali", uniName, 5);
        Student student6 = new Student("Sarah", uniName, 6);

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

        //    public void inputGrades(Student student, Class class1, String grade){
        //enter some student grades
        professor1.inputGrades(student1, class1, "A");
        professor1.inputGrades(student1, class4, "B");
        professor1.inputGrades(student1, class5, "A");
        professor2.inputGrades(student1, class3, "A");
        professor1.inputGrades(student4, class1, "B");

        //add attendance and absences
        professor1.takeAttendance(student1, class1, true);
        professor1.takeAttendance(student1, class1, true);
        professor1.takeAttendance(student1, class1, true);
        professor1.takeAttendance(student1, class1, false);
        professor1.takeAttendance(student4, class1, true);

        Scanner uniScanner = new Scanner(System.in);
        boolean repeatUniName = false;

        String uniName1;

        do {
            System.out.println("What university do you want to access?");

            uniName1 = uniScanner.nextLine();

            //check if university exists
            if (universityManager.findUniversityByName(uniName1) == null) {
                repeatUniName = true;
                System.out.println("University does not exist");
            }else
                	repeatUniName = false;
        }while (repeatUniName);

        Scanner scanner = new Scanner(System.in);

        // Prompt the user to choose their user type
        System.out.println("Choose your user type:");
        System.out.println("1. Administrator");
        System.out.println("2. Student");
        System.out.println("3. Professor");

        int userTypeChoice = scanner.nextInt();

        University findUni = universityManager.findUniversityByName(uniName1);

        switch (userTypeChoice) {
            case 1:
                // Administrator
                int adminAction;
                do {
                    System.out.println("Choose your action:");
                    System.out.println("1. Add a new course");
                    System.out.println("2. Add a new class");
                    System.out.println("3. Change course details");
                    System.out.println("4. Add Professor");
                    System.out.println("5. Remove Professor");
                    System.out.println("6. Enroll Student");
                    System.out.println("7. Edit Class Capacity");
                    System.out.println("8. Generate Report on student performance and enrollment statistics");
                    System.out.println("9. Exit");

                    adminAction = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    Administrator admin = new Administrator(uniName1, 1001, "admin1234");

                    switch (adminAction) {
                        case 1:
                            System.out.println("Enter the name of the course: ");
                            String courseName = scanner.nextLine();

                            System.out.println("Enter the department of the course: ");
                            String department = scanner.nextLine();

                            System.out.println("Enter the description of the course: ");
                            String description = scanner.nextLine();

                            System.out.println("Enter the number of credits for the course: ");
                            int numOfCredits = scanner.nextInt();

                            admin.createCourse(findUni, courseName, department, description, numOfCredits);
                            break;
                        case 2:
                            // Add a new class - implement this logic
                            System.out.println("Enter the name of the course: ");
                            String courseName1 = scanner.nextLine();

                            System.out.println("Enter the semester: ");
                            String semesterName = scanner.nextLine();

                            System.out.println("Enter the professor's ID: ");
                            int professorID = scanner.nextInt();
                            scanner.nextLine(); // Consume the newline character

                            System.out.println("Enter the max class capacity: ");
                            int maxClassCapacity = scanner.nextInt();
                            scanner.nextLine(); // Consume the newline character

                            System.out.println("Enter the location: ");
                            String location = scanner.nextLine();

                            System.out.println("Enter the days: ");
                            String days = scanner.nextLine();

                            System.out.println("Enter the time: ");
                            String time = scanner.nextLine();

                            ClassTiming classTiming = new ClassTiming(days, time);
                            Schedule schedule = new Schedule(classTiming, location);

                            admin.addClass(findUni, courseName1, semesterName, professorID, maxClassCapacity, schedule);

                            break;
                        case 3:
                            // Change course details - implement this logic
                            System.out.println("Enter the name of the course you want to update: ");
                            String oldCourseName = scanner.nextLine();

                            System.out.println("Enter the new name of the course: ");
                            String newCourseName = scanner.nextLine();

                            System.out.println("Enter the new department of the course: ");
                            String newDepartment = scanner.nextLine();

                            System.out.println("Enter the new description of the course: ");
                            String newDescription = scanner.nextLine();

                            admin.updateCourse(findUni, oldCourseName, newCourseName, newDepartment, newDescription);
                            break;
                        case 4:
                            // Add Professor - implement this logic
                            System.out.println("Enter the name of the professor: ");
                            String professorName1 = scanner.nextLine();

                            System.out.println("Enter the department of the professor: ");
                            String professorDepartment = scanner.nextLine();

                            admin.addProfessor(findUni, professorName1, professorDepartment);

                            break;
                        case 5:
                            // Remove Professor - implement this logic
                            System.out.println("Enter professor ID: ");
                            int professorID1 = scanner.nextInt();

                            admin.removeProfessor(findUni, professorID1);
                            break;
                        case 6:
                            // Enroll Student - implement this logic
                            System.out.println("Enter the name of the student: ");
                            String studentName = scanner.nextLine();

                            System.out.println("Enter the name of the course: ");
                            String courseName2 = scanner.nextLine();

                            System.out.println("Enter the semester: ");
                            String semesterName2 = scanner.nextLine();

                            admin.enrollStudent(findUni, studentName, courseName2, semesterName2);
                            break;
                        case 7:
                            // Edit Class Capacity - implement this logic
                            System.out.println("Enter the name of the course: ");
                            String courseName3 = scanner.nextLine();

                            System.out.println("Enter the semester: ");
                            String semesterName3 = scanner.nextLine();

                            System.out.println("Enter the new capacity: ");
                            int newCapacity = scanner.nextInt();

                            System.out.println("Enter the section number: ");
                            int sectionNumber = scanner.nextInt();

                            admin.editCapacity(findUni, courseName3, semesterName3, newCapacity, sectionNumber);
                            break;
                        case 8:
                            // Generate Report - implement this logic
                            ReportGenerator reportGenerator = new ReportGenerator();
                            reportGenerator.generateStudentPerformanceReport(findUni.getStudents());
                            break;
                        case 9:
                            // Exit
                            break;
                        default:
                            System.out.println("Invalid action for an administrator.");
                            break;
                    }
                } while (adminAction != 9);
                break;
            case 2:
                // Student
                System.out.println("Enter the student ID: ");
                int studentID = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                Student student = university.findStudentByID(studentID);

                if (student == null) {
                    System.out.println("Student not found.");
                    break;
                }

                int studentAction;
                do {
                    System.out.println("Choose your action:");
                    System.out.println("1. View available courses");
                    System.out.println("2. View course by department");
                    System.out.println("3. View course by professor");
                    System.out.println("4. Get course details");
                    System.out.println("5. Get course schedule");
                    System.out.println("6. Favorite courses");
                    System.out.println("7. Remove course from favorites");
                    System.out.println("8. View favorite courses");
                    System.out.println("9. Enroll in a course");
                    System.out.println("10. View academic transcript");
                    System.out.println("11. View historical course schedule");
                    System.out.println("12. View past semester performance");
                    System.out.println("13. Exit");

                    studentAction = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    switch (studentAction) {
                        case 1:
                            // View available courses - implement this logic
                            System.out.println("Enter the semester: ");
                            String semesterName4 = scanner.nextLine();

                            ArrayList<Course> availableCourses = university.getAvailableCourses(semesterName4);
                            break;
                        case 2:
                            // View course by department - implement this logic
                            System.out.println("Enter the department: ");
                            String departmentName = scanner.nextLine();

                            ArrayList<Course> coursesByDepartment = university.searchCourseByDepartment(departmentName);
                            break;
                        case 3:
                            // View course by professor - implement this logic
                            System.out.println("Enter the name of the professor: ");
                            String professorName3 = scanner.nextLine();

                            ArrayList<Course> coursesByProfessor = university.searchCourseByProfessor(professorName3);
                            break;
                        case 4:
                            // Get course details - implement this logic
                            System.out.println("Enter the name of the course: ");
                            String courseName4 = scanner.nextLine();

                            university.getCourseDetails(courseName4);
                            break;
                        case 5:
                            // Get course schedule - implement this logic
                            System.out.println("Enter the semester: ");
                            String semesterName5 = scanner.nextLine();

                            university.getCourseSchedule(semesterName5);
                            break;
                        case 6:
                            // Favorite courses - implement this logic
                            System.out.println("Enter the name of the course: ");
                            String courseName5 = scanner.nextLine();

                            student.addCourseToFavorites(findUni, courseName5);
                            break;
                        case 7:
                            //Remove course from favorites - implement this logic
                            System.out.println("Enter the name of the course: ");
                            String courseName6 = scanner.nextLine();

                            student.removeCourseFromFavorites(findUni, courseName6);
                            break;
                        case 8:
                            // View favorite courses - implement this logic
                            ArrayList<Course> favoritedCourses = student.getFavoriteCourses();
                            break;
                        case 9:
                            // Enroll in a course - implement this logic
                            System.out.println("Enter the name of the course: ");
                            String courseName7 = scanner.nextLine();

                            System.out.println("Enter the semester: ");
                            String semesterName6 = scanner.nextLine();

                            student.enrollInCourse(findUni, courseName7, semesterName6);
                            break;
                        case 10:
                            // View academic transcript - implement this logic
                            student.getTranscript(findUni);
                            break;
                        case 11:
                            // View historical course schedule - implement this logic
                            student.getHistoricalCourseSchedule(findUni);
                            break;
                        case 12:
                            student.getGPA(findUni);
                            break;
                        case 13:
                            // Exit
                            break;
                        default:
                            System.out.println("Invalid action for a student.");
                            break;
                    }
                } while (studentAction != 13);
                break;
            case 3:
                // Professor
                System.out.println("Enter the professor ID: ");
                int professorID = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                Professor professor = university.findProfessorByID(professorID);

                if (professor == null) {
                    System.out.println("Professor not found.");
                    break;
                }

                int professorAction;

                do {
                System.out.println("Choose your action:");
                System.out.println("1. Input student grade");
                System.out.println("2. Enter student attendance");
                System.out.println("3. Exit");

                professorAction = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                    switch (professorAction) {
                    case 1:
                        // Input student grade - implement this logic
                        System.out.println("Enter the student's ID: ");
                        int studentID2 = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        System.out.println("Enter the course name: ");
                        String courseName8 = scanner.nextLine();

                        System.out.println("Enter the grade: ");
                        String grade = scanner.nextLine();

                        Student student7 = university.findStudentByID(studentID2); //find student by ID in

                        Course course5 = university.getCourseByName(courseName8); //find course by name in
                        Class class8 = course5.findClassByStudent(student7); //find class by student in course
                        if(class8 == null){
                            System.out.println("Class not found.");
                            break;
                        }

                        professor.inputGrades(student7, class8, grade);
                        break;
                    case 2:
                        // Enter student attendance - implement this logic
                        System.out.println("Enter the student's ID: ");
                        int studentID3 = scanner.nextInt();

                        System.out.println("Enter the course name: ");
                        String courseName9 = scanner.nextLine();

                        boolean repeat = false;
                        boolean attend = false;

                        do {
                            System.out.println("Do you want to increment the attendance or absence?");
                            String attendance = scanner.nextLine();

                            if (attendance.equals("attendance")) {
                                attend = true;
                            } else if (attendance.equals("absence")) {
                                attend = false;
                            } else {
                                System.out.println("Invalid input.");
                                repeat = true;
                            }
                        }while (repeat);

                        Student student8 = university.findStudentByID(studentID3); //find student by ID in

                        Course course6 = university.getCourseByName(courseName9); //find course by name in

                        Class class9 = course6.findClassByStudent(student8); //find class by student in course

                        professor.takeAttendance(student8, class9, attend);

                        break;
                    case 3:
                        // Exit
                        break;
                    default:
                        System.out.println("Invalid action for a professor.");
                        break;
                }
                } while (professorAction != 3);
                break;
            default:
                System.out.println("Invalid user type choice.");
                break;
        }

        // Close the scanner
        scanner.close();

    }
}
