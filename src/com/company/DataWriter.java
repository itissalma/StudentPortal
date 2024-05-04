package com.company;
import com.company.models.*;
import com.company.models.Class;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataWriter {

    private final BufferedWriter writer;

    public DataWriter(BufferedWriter writer) {
        this.writer = writer;
    }

    //Data Writer for Admin Panels
    public static boolean writeCourseEntry(University university, BufferedWriter writer, String courseName, String department, String description, int numOfCredits) {
        try {
            //check if course already exists
            if (university.getCourseByName(courseName) != null) {
                return false;
            }
            // Create Course
            Course course = new Course(courseName, department, description, numOfCredits);

            // Add the course to the university
            university.addCourse(course);

            // Write the new course entry to the file
            writer.newLine();
            writer.write(course.getName() + "," + course.getDepartment() + "," +
                    course.getDescription() + "," + course.getNumOfCredits());
            writer.newLine();

            // Return true to indicate success
            return true;
        } catch (IOException e) {
            // Handle IOException if needed
            e.printStackTrace();
            return false;
        }
    }

    public static String updateCourseEntry(University university, String filePath, String oldCourseName, String newCourseName, String department, String description) {
        try {
            boolean[] foundCourse = {false};
            List<String> updatedLines = readAndUpdateLines(university, filePath, oldCourseName, newCourseName, department, description, foundCourse);

            if (!foundCourse[0]) {
                return "Course not found";
            }

            // Update the file with the modified lines
            updateFileWithModifiedLines(filePath, updatedLines);

            return "Course updated";
        } catch (IOException e) {
            // Handle IOException if needed
            e.printStackTrace();
            return "An error occurred: " + e.getMessage();
        }
    }

    private static List<String> readAndUpdateLines(University university, String filePath, String oldCourseName, String newCourseName, String department, String description, boolean[] foundCourse) throws IOException {
        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("\\p{C}", "");

                String[] courseData = line.split(",");

                if (courseData.length == 4) {
                    String courseTitle = courseData[0].trim();
                    String currentDepartment = courseData[1].trim();
                    String currentDescription = courseData[2].trim();
                    int creditHours = Integer.parseInt(courseData[3].trim());

                    if (courseTitle.equals(oldCourseName)) {
                        // Update course information
                        Course updatedCourse = new Course(newCourseName, department, description, creditHours);
                        university.removeCourse(university.getCourseByName(oldCourseName));
                        university.addCourse(updatedCourse);
                        foundCourse[0] = true;
                        System.out.println("Course updated");

                        // Build the updated line
                        String updatedLine = updatedCourse.getName() + "," + updatedCourse.getDepartment() + "," +
                                updatedCourse.getDescription() + "," + updatedCourse.getNumOfCredits();
                        updatedLines.add(updatedLine);
                    } else {
                        // If the course is not the one to be updated, add the existing line to the list
                        updatedLines.add(line);
                    }
                }
            }
        }

        return updatedLines;
    }

    private static void updateFileWithModifiedLines(String filePath, List<String> updatedLines) throws IOException {
        // Use try-with-resources to automatically close the writer
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            // Write the updated lines to the file
            updatedLines.stream()
                    .forEach(updatedLine -> {
                        try {
                            writer.write(updatedLine);
                            writer.newLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    public static boolean writeClassEntry(University university, BufferedWriter writer, String courseName, String semesterName, int professorID, int maxClassCapacity, String location, String time, String day) {
        try {
            Course course = university.getCourseByName(courseName);
            Semester semester = university.findSemesterByName(semesterName);
            Professor professor = university.findProfessorByID(professorID);

            if (course == null || semester == null || professor == null) {
                return false; // Course, semester, or professor does not exist
            }

            int sectionNumber = course.getClasses().stream()
                    .filter(class1 -> class1.getSemester().getName().equals(semesterName))
                    .mapToInt(Class::getSectionNumber)
                    .findFirst()
                    .orElse(0);

            sectionNumber++;

            Schedule schedule;
            // Check if class timing already exists
            if (university.getClassTiming(time, day) == null) {
                ClassTiming classTiming = new ClassTiming(time, day);
                schedule = new Schedule(classTiming, location);
                // Add class timing to university
                university.addClassTiming(classTiming);
                // Add class timing to file (classtiming_data.csv)
                String filePath = "src/com/company/data/classTimings_data.csv";

                // Create an instance of BufferedWriter for writing to the file
                try (BufferedWriter writer2 = new BufferedWriter(new FileWriter(filePath, true))) {
                    // Write class timing entry using DataWriter
                    DataWriter dataWriter = new DataWriter(writer2);
                    DataWriter.writeClassTimingEntry(classTiming, writer2);
                }
            } else {
                schedule = new Schedule(university.getClassTiming(time, day), location);
            }

            Class newClass = new Class(course, sectionNumber, semester, professor, maxClassCapacity, schedule);

            // Add the class to the university
            university.addClass(newClass, course);

            // Build the class entry
            String classEntry = courseName + "," + sectionNumber + "," + semesterName + "," + professorID + "," +
                    maxClassCapacity + "," + day + "," + time + "," + location;

            // Write the new class entry to the file
            writer.newLine();
            writer.write(classEntry);
            writer.newLine();

            return true; // Class added successfully
        } catch (Exception e) {
            // Handle exceptions if needed
            e.printStackTrace();
            return false; // An error occurred
        }
    }


    public static void writeClassTimingEntry(ClassTiming classTiming, BufferedWriter writer) throws IOException {
        // Build the class timing entry
        String classTimingEntry = classTiming.getDayOfWeek() + "," + classTiming.getTime();

        // Write the new class timing entry to the file
        writer.newLine();
        writer.write(classTimingEntry);
        writer.newLine();
    }

    public static String editCapacityEntry(String filePath, String courseName, String semester, int capacity, int sectionNumber) {
        try {
            boolean foundSection = false;
            List<String> updatedLines = new ArrayList<>();

            // Read and update lines
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    line = line.replaceAll("\\p{C}", "");

                    String[] classData = line.split(",");

                    if (classData.length == 8) {
                        String currentCourseName = classData[0].trim();
                        int currentSectionNumber = Integer.parseInt(classData[1].trim());
                        String currentSemester = classData[2].trim();
                        int currentProfessorID = Integer.parseInt(classData[3].trim());
                        int currentMaxStudents = Integer.parseInt(classData[4].trim());
                        String currentDay = classData[5].trim();
                        String currentTime = classData[6].trim();
                        String currentRoom = classData[7].trim();

                        if (currentCourseName.equals(courseName) && currentSectionNumber == sectionNumber && currentSemester.equals(semester)) {
                            // Update class capacity
                            currentMaxStudents = capacity;
                            foundSection = true;
                        }

                        // Build the updated line
                        String updatedLine = currentCourseName + "," + currentSectionNumber + "," + currentSemester + ","
                                + currentProfessorID + "," + currentMaxStudents + "," + currentDay + ","
                                + currentTime + "," + currentRoom;

                        // Add the updated line to the list
                        updatedLines.add(updatedLine);
                    }
                }
            }

            if (!foundSection) {
                return "Section not found";
            }

            // Update the file with the modified lines
            updateFileWithModifiedLines(filePath, updatedLines);

            return "Class capacity updated";
        } catch (IOException e) {
            // Handle IOException if needed
            e.printStackTrace();
            return "An error occurred: " + e.getMessage();
        }
    }


    public static String addProfessorEntry(String filePath, String professorName, String department, University university) {
        try {
            // Check if the professor already exists
            if (professorExists(filePath, professorName)) {
                return "Professor already exists";
            }

            // Append the new professor entry to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.newLine();
                int id = university.getProfessors().size() + 8001;
                writer.write(professorName + "," + department + "," + "The American University in Cairo," + id);
            }

            return "Professor added successfully";
        } catch (IOException e) {
            // Handle IOException if needed
            e.printStackTrace();
            return "An error occurred: " + e.getMessage();
        }
    }

    // Helper method to check if a professor with the given name already exists
    private static boolean professorExists(String filePath, String professorName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("\\p{C}", "");
                String[] professorData = line.split(",");

                if (professorData.length == 2) {
                    String currentProfessorName = professorData[0].trim();
                    if (currentProfessorName.equals(professorName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static String removeProfessorEntry(String filePath, int professorID) {
        try {
            // Read the existing data from the file
            List<String> professorLines = new ArrayList<>();
            boolean professorFound = false;

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    line = line.replaceAll("\\p{C}", "");

                    String[] professorData = line.split(",");
                    if (professorData.length == 4) {
                        int currentProfessorID = Integer.parseInt(professorData[3].trim());

                        if (currentProfessorID == professorID) {
                            System.out.println("Found professor");
                            // Skip the line if it matches the professor ID to be removed
                            professorFound = true;
                        } else {
                            // Add the line to the list if it doesn't match
                            professorLines.add(line);
                        }
                    }
                }
            }

            if (!professorFound) {
                return "Professor not found";
            }

            // Update the file with the modified lines
            updateFileWithModifiedLines(filePath, professorLines);

            return "Professor removed successfully";
        } catch (IOException e) {
            // Handle IOException if needed
            e.printStackTrace();
            return "An error occurred: " + e.getMessage();
        }
    }

    public static String writeEnrollmentEntry(String filePath, University university, String studentName, String courseName, String semester) {
        try {
            int studentID = university.getNewStudentID();

            Student student = new Student(studentName, university.getName(), studentID);
            university.addStudent(student);

            // Find the course
            Course course = university.getCourses().stream()
                    .filter(c -> c.getName().equals(courseName))
                    .findFirst()
                    .orElse(null);

            if (course == null) {
                return "Course not found";
            }

            // Find the class in the specified semester
            Class class1 = course.getClasses().stream()
                    .filter(c -> c.getSemester().getName().equals(semester))
                    .findFirst()
                    .orElse(null);

            if (class1 == null) {
                return "Class not found in the specified semester";
            }

            // Check if the class is not full
            if (!class1.isFull()) {
                // Enroll the student in the class
                class1.enrollStudent(student);

                // Write the new enrollment entry to the file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                    writer.newLine();
                    writer.write(studentID + "," + "The American University in Cairo," + courseName + "," + semester);
                    writer.newLine();
                }

                return "Enrollment successful";
            } else {
                return "Class is full";
            }

        } catch (IOException | Class.ClassFullException e) {
            e.printStackTrace();
            return "An error occurred: " + e.getMessage();
        }
    }

    //Data Writers for the Professor Panels
    public static void inputGradesEntry(Student student, Class class1, String grade, String filePath, int professorID) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));

            class1.getStudentCourseEnrollment().getStudentGrades().stream()
                    .filter(studentGrade -> studentGrade.getStudent().getName().equals(student.getName()))
                    .findFirst()
                    .ifPresent(studentGrade -> {
                        // Update the grade entry
                        studentGrade.setGrade(studentGrade.getStudent(), grade, class1, true);

                        // Add the updated grade entry to the student's academic transcript
                        student.getAcademicTranscript().addStudentGrade(studentGrade);

                        // Write the updated grade entry to the file
                        try {
                            writer.newLine();
                            writer.write(professorID + "," + student.getStudentID() + "," + class1.getCourse().getName() + ","
                                    + class1.getSectionNumber() + "," + grade);
                        } catch (IOException e) {
                            System.out.println("An error occurred while writing to the file: " + e.getMessage());
                        }
                    });

            // Close the writer
            writer.close();

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public static void takeAttendanceEntry(Student student, Class class1, boolean isPresent, String filePath, int professorID) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));

            class1.getStudentCourseEnrollment().getStudentGrades().stream()
                    .filter(studentGrade -> studentGrade.getStudent().getName().equals(student.getName()))
                    .findFirst()
                    .ifPresent(studentGrade -> {
                        // Update attendance based on the isPresent value
                        if (isPresent) {
                            studentGrade.addAttendance();
                        } else {
                            studentGrade.addAbsence();
                        }

                        // Write the attendance entry to the file
                        try {
                            writer.newLine();
                            writer.write(professorID + "," + student.getStudentID() + "," + class1.getCourse().getName() + ","
                                    + class1.getSectionNumber() + "," + (isPresent ? "1" : "0"));
                        } catch (IOException e) {
                            System.out.println("An error occurred while writing to the file: " + e.getMessage());
                        }
                    });

            // Close the writer
            writer.close();

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    //Data Writers for the Student Panels
    public static String addToFavoritesEntry(Student student, University university, String courseName, String filePath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));

            // Check if the course exists
            Course course = university.getCourseByName(courseName);
            if (course == null) {
                return "Course not found";
            }

            // Check if the course is already in favorites
            if (student.getFavoriteCourses().contains(course)) {
                return "Course is already in favorites";
            }

            // Add the course to favorites
            student.addCourseToFavorites(university, courseName);

            // Write the new favorite course entry to the file
            writer.newLine();
            writer.write(student.getStudentID() + "," + courseName);

            // Close the writer
            writer.close();

            return "Course added to favorites successfully";
        } catch (IOException e) {
            // Handle IOException if needed
            e.printStackTrace();
            return "An error occurred: " + e.getMessage();
        }
    }

    public static String removeFromFavoritesEntry(Student student, String courseName, String filePath, University university) {
        try {
            List<String> updatedLines = new ArrayList<>();

            // Read and update lines
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    line = line.replaceAll("\\p{C}", "");

                    String[] favoriteData = line.split(",");

                    if (favoriteData.length == 2) {
                        int studentID = Integer.parseInt(favoriteData[0].trim());
                        String currentCourseName = favoriteData[1].trim();

                        if (student.getStudentID() == studentID && currentCourseName.equals(courseName)) {
                            // Skip the line if it matches the student and course to be removed
                            System.out.println("Course removed from favorites");
                        } else {
                            // Add the line to the list if it doesn't match
                            updatedLines.add(line);
                        }
                    }
                }
            }

            // Update the file with the modified lines
            updateFileWithModifiedLines(filePath, updatedLines);

            // Remove the course from favorites
            student.removeCourseFromFavorites(university, courseName);

            return "Course removed from favorites successfully";
        } catch (IOException e) {
            // Handle IOException if needed
            e.printStackTrace();
            return "An error occurred: " + e.getMessage();
        }
    }

    public static String enrollInCourseEntry(Student student, University university, String courseName, String semesterName, String filePath) {
        try {
            // Iterate over courses in the university
            for (Course course : university.getCourses()) {
                if (course.getName().equals(courseName)) {
                    // Check if the semester is found in the course
                    if (course.getClasses().stream()
                            .anyMatch(class1 -> class1.getSemester().getName().equals(semesterName))) {

                        // Check if any class in the semester is not full
                        if (course.getClasses().stream()
                                .filter(class1 -> class1.getSemester().getName().equals(semesterName))
                                .anyMatch(class1 -> !class1.isFull())) {

                            // Enroll the student in the class
                            Class classToEnroll = course.getClasses().stream()
                                    .filter(class1 -> class1.getSemester().getName().equals(semesterName) && !class1.isFull())
                                    .findFirst()
                                    .orElseThrow(() -> new RuntimeException("Class not found"));

                            classToEnroll.enrollStudent(student);

                            // Specify the file path where you want to store the enrollment data
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                                // Write the new enrollment entry to the file
                                writer.newLine();
                                writer.write(student.getStudentID() + "," + university.getName() + "," + courseName + "," + semesterName);
                                writer.newLine();
                            }

                            return "Enrolled in " + courseName + " for " + semesterName;
                        } else {
                            return "All classes for the semester are full";
                        }
                    } else {
                        return "Semester not found for the specified course";
                    }
                }
            }

            return "Course not found";

        } catch (IOException | Class.ClassFullException e) {
            return "An error occurred: " + e.getMessage();
        }
    }



}
