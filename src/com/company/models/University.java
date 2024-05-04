package com.company.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class University {
    private String name;
    private String location;
    private ArrayList<Class> classes;
    private ArrayList<Course> courses;
    private ArrayList<Professor> professors;
    private ArrayList<Student> students;
    private ArrayList<Administrator> admins;
    private ArrayList<Semester> semesters;
    private ArrayList<ClassTiming> classTimings;
    private ArrayList<Schedule> schedules;

    public University(String name, String location) {
        this.name = name;
        this.location = location;
        this.classes = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.professors = new ArrayList<>();
        this.students = new ArrayList<>();
        this.semesters = new ArrayList<>();
        this.admins = new ArrayList<>();
        this.classTimings = new ArrayList<>();
        this.schedules = new ArrayList<>();
    }
    public String getName() {
        return name;
    }

    public ArrayList<Semester> getSemesters() {
        return semesters;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<Class> getClasses() {
        return classes;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public ArrayList<Professor> getProfessors() {
        return professors;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void addAdmin(Administrator admin) {
        admins.add(admin);
    }

    public void addClassTiming(ClassTiming classTiming) {
        classTimings.add(classTiming);
    }

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    public ClassTiming getClassTiming(String day, String time) {
        return classTimings.stream()
                .filter(classTiming -> classTiming.getDayOfWeek().equals(day) && classTiming.getTime().equals(time))
                .peek(ct -> System.out.println("Class timing found"))
                .findFirst()
                .orElse(null);
    }

    public Schedule getSchedule(ClassTiming classTiming, String location) {
        return schedules.stream()
                .filter(schedule -> schedule.getClassTimings().equals(classTiming) && schedule.getLocation().equals(location))
                .findFirst()
                .orElse(null);
    }

    //Students can access a list of available courses, displaying course names and departments.
    public ArrayList<Course> getAvailableCourses(String semester) {
        try {
            // Check if the provided semester is valid
            boolean semesterFound = getSemesters().stream().anyMatch(sem -> sem.getName().equals(semester));

            if (!semesterFound) {
                return new ArrayList<>(); // Return an empty ArrayList of available courses
            }

            return getCourses().stream()
                    .filter(course -> course.getOfferedInSemesters().stream().anyMatch(sem -> sem.getName().equals(semester)))
                    .distinct() // Ensure distinct courses
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    //"Students can search for courses using filters like department and professor.
    //The system displays matching courses."
    public ArrayList<Course> searchCourseByDepartment(String department) {
        try {
            return getCourses().stream()
                    .filter(course -> course.getDepartment().equals(department))
                    .peek(course -> System.out.println("The course name is " + course.getName() +
                            ", and the department is " + course.getDepartment() + "\n"))
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public ArrayList<Course> searchCourseByProfessor(String professorName) {
        try {
            return getCourses().stream()
                    .filter(course -> course.getClasses().stream()
                            .anyMatch(aClass -> {
                                try {
                                    return aClass.getProfessor() != null && aClass.getProfessor().getName().equals(professorName);
                                } catch (NullPointerException e) {
                                    System.err.println("Error: Professor name is null for class in course " + course.getName());
                                    return false;
                                }
                            }))
                    .peek(course -> System.out.println("The course name is " + course.getName() + ", and the department is " + course.getDepartment() + "\n"))
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    //Students can access a detailed page for each course, displaying course descriptions and schedules.
    public void getCourseDetails(String courseName) {
        try {
            getCourses().stream()
                    .filter(course -> course.getName().equals(courseName))
                    .forEach(course -> {
                        System.out.println("The course name is " + course.getName() + ", and the department is " + course.getDepartment() + "\n");
                        System.out.println("The course description is " + course.getDescription() + "\n");
                    });
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    //Students can access the course schedule for a specific semester, including class timings and locations.
    public void getCourseSchedule(String semester) {
        try {
            getCourses().stream()
                    .flatMap(course -> course.getClasses().stream())
                    .filter(class1 -> class1.getSemester().getName().equals(semester))
                    .forEach(class1 -> {
                        System.out.println("The course name is " + class1.getCourse().getName() + ": \n");
                        System.out.println("The semester is " + class1.getSemester().getName() +
                                ", the class timings are " + class1.getSchedule().getClassTimings().getDayOfWeek() +
                                " " + class1.getSchedule().getClassTimings().getTime() +
                                ", and the location is " + class1.getSchedule().getLocation() + "\n");
                    });
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
    // Find professor by name
    public Professor findProfessorByName(String professorName) {
        return professors.stream()
                .filter(professor -> professor.getName().equals(professorName))
                .findFirst()
                .orElse(null);
    }

    public Professor findProfessorByID(int professorID) {
        return professors.stream()
                .filter(professor -> professor.getProfessorID() == professorID)
                .findFirst()
                .orElseGet(() -> {
                    System.out.println("Professor not found");
                    return null;
                });
    }

    public Semester findSemesterByName(String semesterName) {
        return semesters.stream()
                .filter(semester -> semester.getName().equals(semesterName))
                .findFirst()
                .orElseGet(() -> {
                    System.out.println("Semester not found");
                    return null;
                });
    }

    public Course getCourseByName(String courseName) {
        return courses.stream()
                .filter(course -> course.getName().equals(courseName))
                .findFirst()
                .orElse(null); // Course not found
    }

    public Student findStudentByID(int studentID) {
        return students.stream()
                .filter(student -> student.getStudentID() == studentID)
                .findFirst()
                .orElse(null); // Student not found
    }

    // Add student to university
    public void addStudent(Student student) {
        if (student != null) {
            this.students.add(student);
        } else {
            System.out.println("Cannot add a null student to the university.");
        }
    }

    public void addCourse(Course course) {
        if (course != null) {
            this.courses.add(course);
        } else {
            System.out.println("Cannot add a null course to the university.");
        }
    }

    public void removeCourse(Course course) {
        if (course != null) {
            this.courses.remove(course);
        } else {
            System.out.println("Cannot remove a null course from the university.");
        }
    }

    public void addClass(Class class1, Course course) {
        if (class1 == null) {
            System.out.println("Class is null");
            return;
        }
        if (course == null) {
            System.out.println("Course is null");
            return;
        }
        this.classes.add(class1);
        // Add class to the course
        course.addClass(class1);
    }

    public void addProfessor(Professor professor) {
        if (professor != null) {
            this.professors.add(professor);
        } else {
            System.out.println("Cannot add a null professor to the university.");
        }
    }

    public int getNewStudentID(){
        return students.size() + 1;
    }
}
