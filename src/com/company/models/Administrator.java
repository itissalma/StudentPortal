package com.company.models;
import java.util.Iterator;
import java.util.Optional;

public class Administrator {
    private int adminID;
    private String password;
    private String universityName;

    public Administrator(String universityName, int adminID, String password) {
        this.universityName = universityName;
    }

    //Administrators can enter course details, including course name, department, and description.
    public void createCourse(University university, String courseName, String department, String description, int numOfCredits) {
        try {
            Course adminCourse = new Course(courseName, department, description, numOfCredits);
            university.addCourse(adminCourse);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    //Admins can add a section to a specific course

    public String addClass(University university, String courseName, String semesterName, int professorID, int maxClassCapacity, Schedule schedule) {
        try {
            Course course = university.getCourseByName(courseName);
            Semester semester = university.findSemesterByName(semesterName);
            Professor professor = university.findProfessorByID(professorID);

            if (course == null) {
                return "Course does not exist";
            }
            if (semester == null) {
                return "Semester does not exist";
            }
            if (professor == null) {
                return "Professor does not exist";
            }

            // Check if a class with the same course and semester already exists
            Optional<Class> existingClass = course.getClasses().stream()
                    .filter(class1 -> class1.getSemester().getName().equals(semesterName))
                    .findFirst();

            if (existingClass.isPresent()) {
                int sectionNumber = existingClass.get().getSectionNumber() + 1;
                Class newClass = new Class(course, sectionNumber, semester, professor, maxClassCapacity, schedule);
                course.addClass(newClass);
                university.addClass(newClass, course);
                return "Added a new section to the course: " + courseName;
            } else {
                // No existing class, create a new one
                int sectionNumber = 1;
                Class newClass = new Class(course, sectionNumber, semester, professor, maxClassCapacity, schedule);
                course.addClass(newClass);
                university.addClass(newClass, course);
                return "Class added successfully";
            }

        } catch (Exception e) {
            return "An error occurred: " + e.getMessage();
        }
    }

    public String updateCourse(University university, String oldCourseName, String newCourseName, String department, String description) {
        try {
            // Check if the course exists
            Optional<Course> existingCourse = university.getCourses().stream()
                    .filter(course -> course.getName().equals(oldCourseName))
                    .findFirst();

            if (existingCourse.isPresent()) {
                // Update the course details
                Course course = existingCourse.get();
                course.setName(newCourseName);
                course.setDepartment(department);
                course.setDescription(description);
                return "Course updated";
            } else {
                return "Course not found";
            }
        } catch (Exception e) {
            return "An error occurred: " + e.getMessage();
        }
    }

    public void addProfessor(University university, String professorName, String department) {
        try {
            Professor professor = new Professor(professorName, department, university.getName(), university.getProfessors().size() + 8001);
            university.addProfessor(professor);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public boolean removeProfessor(University university, int professorID) {
        try {
            boolean foundProfessor = false;
            Iterator<Professor> professorIterator = university.getProfessors().iterator();
            while (professorIterator.hasNext()) {
                Professor professor = professorIterator.next();
                if (professor.getProfessorID() == professorID) {
                    professorIterator.remove();
                    foundProfessor = true;
                    System.out.println("Professor removed");
                    return true;
                }
            }

            if (!foundProfessor) {
                System.out.println("Professor not found");
                return false;
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return false;
        }
        return false;
    }

    public String enrollStudent(University university, String studentName, String courseName, String semester) {
        try {
            // Check if the course exists
            Optional<Course> existingCourse = university.getCourses().stream()
                    .filter(course -> course.getName().equals(courseName))
                    .findFirst();

            if (existingCourse.isPresent()) {
                Course course = existingCourse.get();

                // Check if the class exists for the specified semester
                Optional<Class> existingClass = course.getClasses().stream()
                        .filter(class1 -> class1.getSemester().getName().equals(semester))
                        .findFirst();

                if (existingClass.isPresent()) {
                    Class class1 = existingClass.get();

                    // Check if the class is not full
                    if (!class1.isFull()) {
                        // Enroll the student in the class
                        int studentID = university.getNewStudentID();
                        Student student = new Student(studentName, university.getName(), studentID);
                        university.addStudent(student);
                        class1.enrollStudent(student);
                        return "Enrollment successful";
                    } else {
                        return "Class is full";
                    }
                } else {
                    throw new IllegalArgumentException("Class not found in the specified semester");
                }
            } else {
                throw new IllegalArgumentException("Course not found");
            }
        } catch (Exception e) {
            // Log or handle the exception as needed
            return "An error occurred: " + e.getMessage();
        }
    }

    public String editCapacity(University university, String courseName, String semester, int capacity, int sectionNumber) {
        try {
            Optional<Course> existingCourse = university.getCourses().stream()
                    .filter(course -> course.getName().equals(courseName))
                    .findFirst();

            if (existingCourse.isPresent()) {
                Course course = existingCourse.get();

                Optional<Class> existingClass = course.getClasses().stream()
                        .filter(class1 -> class1.getSectionNumber() == sectionNumber && class1.getSemester().getName().equals(semester))
                        .findFirst();

                if (existingClass.isPresent()) {
                    Class class1 = existingClass.get();
                    class1.setMaxClassCapacity(capacity);
                    return "Capacity updated";
                } else {
                    return "Section not found";
                }
            } else {
                return "Course does not exist";
            }
        } catch (Exception e) {
            return "An error occurred: " + e.getMessage();
        }
    }

}
