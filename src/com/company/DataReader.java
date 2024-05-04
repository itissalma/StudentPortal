// DataReader.java
package com.company;

import com.company.models.*;
import com.company.models.Class;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class DataReader {
    public static void readData(University university, String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Assuming the file name contains information about its content type
            String fileType = getFileType(filePath);

            // Dispatch the processing based on the file type
            switch (fileType) {
                case "administrator":
                    readAdministratorData(university, reader);
                    break;
                case "student":
                    readStudentData(university, reader);
                    break;
                case "professor":
                    readProfessorData(university, reader);
                    break;
                case "courses":
                    readCourseData(university, reader);
                    break;
                case "classes":
                    System.out.println("Reading classes data...");
                    readClassData(university, reader);
                    break;
                case "semesters":
                    readSemesterData(university, reader);
                    break;
                case "classtimings":
                    readClassTimings(university, reader);
                    break;
                case "schedule":
                    readScheduleData(university, reader);
                    break;
                case "studentenrollment":
                    readStudentEnrollmentData(university, reader);
                    break;
                case "grades":
                    readInputGradesData(university, reader);
                    break;
                case "takeattendance":
                    readInputAttendanceData(university, reader);
                    break;
                case "favoritecourses":
                    readFavoriteCoursesData(university, reader);
                    break;
                default:
                    System.out.println("Unsupported file type: " + fileType);
                    break;
            }
        }
    }

    private static String getFileType(String filePath) {
        // Extract the file name from the path
        Path path = FileSystems.getDefault().getPath(filePath);
        String fileName = path.getFileName().toString();

        // Assuming a naming convention like "type_data.csv"
        String[] parts = fileName.split("_");

        if (parts.length > 0) {
            return parts[0].toLowerCase(); // The first part is the type
        } else {
            // Handle the case where the file name doesn't follow the expected convention
            throw new IllegalArgumentException("Invalid file name format: " + fileName);
        }
    }

    private static void readAdministratorData(University university, BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("\\p{C}", "");

            String[] adminData = line.split(",");

            if (adminData.length == 3) {
                String uniName = adminData[0].trim();
                int adminId = Integer.parseInt(adminData[1].trim());
                String adminPassword = adminData[2].trim();

                Administrator admin = new Administrator(uniName, adminId, adminPassword);
                university.addAdmin(admin);

                System.out.println("Administrator data read successfully.");
            } else {
                System.err.println("Invalid administrator data: " + line);
            }
        }
    }

    private static void readStudentData(University university, BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("\\p{C}", "");

            String[] studentData = line.split(",");

            if (studentData.length == 3) {
                String studentName = studentData[0].trim();
                String uniName = studentData[1].trim();
                int studentId = Integer.parseInt(studentData[2].trim());

                Student student = new Student(studentName, uniName, studentId);
                university.addStudent(student);

                System.out.println("Student data read successfully.");
            } else {
                System.err.println("Invalid student data: " + line);
            }
        }
    }

    private static void readProfessorData(University university, BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("\\p{C}", "");

            String[] professorData = line.split(",");

            if (professorData.length == 4) {
                String professorName = professorData[0].trim();
                String department = professorData[1].trim();
                String uniName = professorData[2].trim();
                int employeeId = Integer.parseInt(professorData[3].trim());

                Professor professor = new Professor(professorName, department, uniName, employeeId);
                university.addProfessor(professor);

                System.out.println("Professor data read successfully.");
            } else {
                System.err.println("Invalid professor data: " + line);
            }
        }
    }

    private static void readSemesterData(University university, BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("\\p{C}", "");

            String semesterName = line.trim();

            Semester semester = new Semester(semesterName);
            university.getSemesters().add(semester);

            System.out.println("Semester data read successfully.");
        }
    }

    private static void readClassTimings(University university, BufferedReader reader) throws IOException {
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("\\p{C}", "");

            String[] timingData = line.split(",");

            if (timingData.length == 2) {
                String timingKey = timingData[0].trim();
                String timingDescription = timingData[1].trim();

                // Create ClassTiming object
                ClassTiming classTiming = new ClassTiming(timingKey, timingDescription);

                // Add ClassTiming to the university
                university.addClassTiming(classTiming);
                System.out.println("Class timing data read successfully.");
            } else {
                System.err.println("Invalid class timing data: " + line);
            }
        }
    }


    private static void readCourseData(University university, BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("\\p{C}", "");

            String[] courseData = line.split(",");

            if (courseData.length == 4) {
                String courseTitle = courseData[0].trim();
                String department = courseData[1].trim();
                String description = courseData[2].trim();
                int creditHours = Integer.parseInt(courseData[3].trim());

                // Create Course
                Course course = new Course(courseTitle, department, description, creditHours);

                // Add the course to the university
                university.addCourse(course);

                System.out.println("Course data read successfully.");
            } else {
                System.err.println("Invalid course data: " + line);
            }
        }
    }

    private static void readScheduleData(University university, BufferedReader reader) throws IOException {
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("\\p{C}", "");

            String[] scheduleData = line.split(",");

            if (scheduleData.length == 3) {
                String day = scheduleData[0].trim();
                String timing = scheduleData[1].trim();
                String location = scheduleData[2].trim();

                // Find ClassTiming by key
                ClassTiming classTiming = university.getClassTiming(day, timing);

                if (classTiming != null) {
                    // Create Schedule object
                    Schedule schedule = new Schedule(classTiming, location);

                    // Add Schedule to the university
                    university.addSchedule(schedule);

                    System.out.println("Schedule data read successfully.");
                } else {
                    System.err.println("Class timing not found for key: " + day + " " + timing);
                }
            } else {
                System.err.println("Invalid schedule data: " + line);
            }
        }
    }


    private static void readClassData(University university, BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("\\p{C}", "");
            String[] classData = line.split(",");

            if (classData.length == 8) {
                String courseTitle = classData[0].trim();
                int classNumber = Integer.parseInt(classData[1].trim());
                String semester = classData[2].trim();
                int professorID = Integer.parseInt(classData[3].trim());
                int maxStudents = Integer.parseInt(classData[4].trim());
                String day = classData[5].trim();
                String time = classData[6].trim();
                String room = classData[7].trim();

                // Find or create the Course
                Course course = university.getCourseByName(courseTitle);
                if (course == null) {
                    System.err.println("Invalid course title for class: " + line);
                    continue;
                }

                // Find or create the Professor
                Professor professor = university.findProfessorByID(professorID);
                if (professor == null) {
                    System.out.println("Professor not found for ID: " + professorID);
                    System.err.println("Invalid professor name for class: " + line);
                    continue;
                }

                // Get the class timing using the key
                ClassTiming classTiming = university.getClassTiming(day, time);
                if (classTiming == null) {
                    System.err.println("Invalid class timing key for class: " + line);
                    System.out.println("Valid class timings are: " + day + " " + time);
                    continue;
                }

                // Create Schedule
                Schedule schedule = university.getSchedule(classTiming, room);
                if(schedule == null) {
                    System.err.println("Invalid schedule for class: " + line);
                    continue;
                }

                Semester semester1 = university.findSemesterByName(semester);

                // Create Class
                Class newClass = new Class(course, classNumber, semester1, professor, maxStudents, schedule);

                // Add the class to the university
                university.addClass(newClass, course);

                System.out.println("Class data read successfully.");
            } else {
                System.err.println("Invalid class data: " + line);
            }
        }
    }

    private static void readStudentEnrollmentData(University university, BufferedReader reader) throws IOException {
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("\\p{C}", "");

            String[] enrollmentData = line.split(",");

            if (enrollmentData.length == 4) {
                int studentId = Integer.parseInt(enrollmentData[0].trim());
                String uniName = enrollmentData[1].trim();
                String courseName = enrollmentData[2].trim();
                String semesterName = enrollmentData[3].trim();

                // Find Student by ID
                Student student = university.findStudentByID(studentId);

                if (student != null) {
                    // Create StudentEnrollment object
                    student.enrollInCourse(university, courseName, semesterName);

                    System.out.println("Student enrollment data read successfully.");
                } else {
                    System.err.println("Invalid data or student/course/semester not found: " + line);
                }
            } else {
                System.err.println("Invalid enrollment data: " + line);
            }
        }
    }

    private static void readInputGradesData(University university, BufferedReader reader) throws IOException {
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("\\p{C}", "");

            String[] gradesData = line.split(",");

            if (gradesData.length == 5) {
                int professorId = Integer.parseInt(gradesData[0].trim());
                int studentId = Integer.parseInt(gradesData[1].trim());
                String courseName = gradesData[2].trim();
                int classNumber = Integer.parseInt(gradesData[3].trim());
                String grade = gradesData[4].trim();

                // Find Professor by ID
                Professor professor = university.findProfessorByID(professorId);

                // Find Student by ID
                Student student = university.findStudentByID(studentId);

                Course course = university.getCourseByName(courseName);

                // Find Class by course name and number
                Class enrolledClass = course.findClass(classNumber);

                if (professor != null && student != null && enrolledClass != null) {
                    // Invoke inputGrades method for the professor
                    professor.inputGrades(student, enrolledClass, grade);

                    System.out.println("Input grades data read successfully.");
                } else {
                    System.err.println("Invalid data or professor/student/class not found: " + line);
                }
            } else {
                System.err.println("Invalid input grades data: " + line);
            }
        }
    }

    private static void readInputAttendanceData(University university, BufferedReader reader) throws IOException {
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("\\p{C}", "");

            String[] attendanceData = line.split(",");

            if (attendanceData.length == 5) {
                int professorId = Integer.parseInt(attendanceData[0].trim());
                int studentId = Integer.parseInt(attendanceData[1].trim());
                String courseName = attendanceData[2].trim();
                int classNumber = Integer.parseInt(attendanceData[3].trim());
                int attendanceStatus = Integer.parseInt(attendanceData[4].trim());

                // Find Professor by ID
                Professor professor = university.findProfessorByID(professorId);

                // Find Student by ID
                Student student = university.findStudentByID(studentId);

                Course course = university.getCourseByName(courseName);

                // Find Class by course name and number
                Class enrolledClass = course.findClass(classNumber);

                if (professor != null && student != null && enrolledClass != null) {
                    // Invoke takeAttendance method for the professor
                    boolean isPresent = (attendanceStatus == 1);
                    professor.takeAttendance(student, enrolledClass, isPresent);

                    System.out.println("Input attendance data read successfully.");
                } else {
                    System.err.println("Invalid data or professor/student/class not found: " + line);
                }
            } else {
                System.err.println("Invalid input attendance data: " + line);
            }
        }
    }

    private static void readFavoriteCoursesData(University university, BufferedReader reader) throws IOException {
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("\\p{C}", "");

            String[] favoriteCoursesData = line.split(",");

            if (favoriteCoursesData.length == 2) {
                int studentId = Integer.parseInt(favoriteCoursesData[0].trim());
                String courseName = favoriteCoursesData[1].trim();

                // Find Student by ID
                Student student = university.findStudentByID(studentId);

                Course course = university.getCourseByName(courseName);

                if (student != null && course != null) {
                    // Invoke addFavoriteCourse method for the student
                    student.addCourseToFavorites(university, course.getName());

                    System.out.println("Favorite courses data read successfully.");
                } else {
                    System.err.println("Invalid data or student/course not found: " + line);
                }
            } else {
                System.err.println("Invalid favorite courses data: " + line);
            }
        }
    }
}
