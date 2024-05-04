package com.company.models;

import com.company.UniversityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Student {
    private int studentID;
    private String name;
    private String password;
    private String universityName;
    private AcademicTranscript academicTranscript;
    private ArrayList<Course> favoriteCourses;

    public Student(String name, String universityName, int studentID) {
        this.name = name;
        this.universityName = universityName;
        this.studentID = studentID;
        this.academicTranscript = new AcademicTranscript(); // Initialize the academic transcript
        this.favoriteCourses = new ArrayList<>();
    }

    public int getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public AcademicTranscript getAcademicTranscript() {
        return academicTranscript;
    }

    public ArrayList<Course> getFavoriteCourses() {
        try {
            //do it using streams
            favoriteCourses.stream().forEach(course -> {
                System.out.println("The course name is " + course.getName() + ", and the department is " + course.getDepartment() + "\n");
            });
            if(favoriteCourses.size() == 0) {
                System.out.println("You have no favorite courses.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        return favoriteCourses;
    }

    //"Students can mark courses as favorites.
    //Favorited courses should be easily accessible"
    public String addCourseToFavorites(University university, String courseName) {
        try {
            boolean isCourseAlreadyFavorited = favoriteCourses.stream()
                    .anyMatch(course -> course.getName().equals(courseName));

            if (!isCourseAlreadyFavorited) {
                boolean courseExists = university.getCourses().stream()
                        .anyMatch(course -> {
                            if (course.getName().equals(courseName)) {
                                favoriteCourses.add(course);
                                return true;
                            }
                            return false;
                        });

                if (courseExists) {
                    return "Course added to favorites";
                } else {
                    return "Course does not exist";
                }
            } else {
                return "Course is already favorited";
            }
        } catch (NoSuchElementException e) {
            return "Course does not exist in the university.";
        } catch (IllegalArgumentException e) {
            return "Invalid argument provided: " + e.getMessage();
        } catch (UnsupportedOperationException e) {
            return "Operation not supported on the favoriteCourses collection.";
        } catch (Exception e) {
            return "An error occurred: " + e.getMessage();
        }
    }

    public String removeCourseFromFavorites(University university, String courseName) {
        try {
            boolean isCourseAlreadyFavorited = favoriteCourses.stream()
                    .anyMatch(course -> course.getName().equals(courseName));

            if (isCourseAlreadyFavorited) {
                boolean courseExists = university.getCourses().stream()
                        .anyMatch(course -> {
                            if (course.getName().equals(courseName)) {
                                favoriteCourses.remove(course);
                                return true;
                            }
                            return false;
                        });

                if (courseExists) {
                    return "Course removed from favorites";
                } else {
                    return "Course does not exist";
                }
            } else {
                return "Course is not favorited";
            }
        } catch (Exception e) {
            return "An error occurred: " + e.getMessage();
        }
    }

    public String enrollInCourse(University university, String courseName, String semesterName) {
        try {
            boolean isEnrolled = university.getCourses().stream()
                    .filter(course -> course.getName().equals(courseName))
                    .flatMap(course -> course.getClasses().stream())
                    .filter(class1 -> class1.getSemester().getName().equals(semesterName))
                    .filter(class1 -> !class1.isFull())
                    .findFirst()
                    .map(class1 -> {
                        try {
                            class1.enrollStudent(this);
                        } catch (Class.ClassFullException e) {
                            e.printStackTrace();
                        }
                        return true;
                    })
                    .orElse(false);

            if (isEnrolled) {
                return "Enrolled in " + courseName + " for " + semesterName;
            } else {
                return "Course or semester not found or the class is full";
            }
        } catch (Exception e) {
            return "An error occurred: " + e.getMessage();
        }
    }


    public void getTranscript(University university) {
        try {
            academicTranscript.getStudentGrades().stream()
                    .filter(StudentGrade::isCompleted)
                    .forEach(studentGrade -> {
                        Class studentClass = studentGrade.getStudentClass();
                        Course course = studentClass.getCourse();
                        Semester semester = studentClass.getSemester();

                        System.out.println("The course name is " + course.getName() +
                                ", the semester is " + semester.getName() +
                                ", and the grade is " + studentGrade.getGrade() + "\n");
                    });
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public double calculateGPAForStudentInSemester(String semesterName) {
        int totalCredits = 0;
        double totalGradePoints = 0.0;

        for (StudentGrade studentGrade : academicTranscript.getStudentGrades()) {
            if (studentGrade.getStudentClass().getSemester().getName().equals(semesterName) &&
                    studentGrade.isCompleted()) {

                int courseCredits = studentGrade.getStudentClass().getCourse().getNumOfCredits();
                totalCredits += courseCredits;

                double numericalGrade = studentGrade.getNumericalGrade();
                double courseGradePoints = numericalGrade * courseCredits;
                totalGradePoints += courseGradePoints;
            }
        }

        return (totalCredits > 0) ? totalGradePoints / totalCredits : 0.0;
    }

    public void getGPA(University university) {
        try {
            List<String> semesters = academicTranscript.getStudentGrades().stream()
                    .filter(StudentGrade::isCompleted)
                    .map(grades -> grades.getStudentClass().getSemester().getName())
                    .distinct()
                    .collect(Collectors.toList());

            Map<String, Double> semesterGPAs = academicTranscript.getStudentGrades().stream()
                    .filter(StudentGrade::isCompleted)
                    .collect(Collectors.groupingBy(
                            grades -> grades.getStudentClass().getSemester().getName(),
                            Collectors.averagingDouble(grades -> grades.getNumericalGrade())
                    ));

            semesters.forEach(semester -> {
                double semesterGPA = semesterGPAs.getOrDefault(semester, 0.0);
                System.out.println("The semester is " + semester + ", and the GPA is " + semesterGPA);
            });
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public void getHistoricalCourseSchedule(University university) {
        try {
            academicTranscript.getStudentGrades().stream()
                    .filter(StudentGrade::isCompleted)
                    .forEach(grades -> {
                        System.out.println("The course name is " + grades.getStudentClass().getCourse().getName() +
                                ", the semester is " + grades.getStudentClass().getSemester().getName() +
                                ", and the grade is " + grades.getGrade() + "\n");
                        System.out.println("The course schedule is " + grades.getStudentClass().getSchedule().getClassTimings().getDayOfWeek() +
                                " " + grades.getStudentClass().getSchedule().getClassTimings().getTime() +
                                " and the location is " + grades.getStudentClass().getSchedule().getLocation() + "\n");
                    });
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

}
