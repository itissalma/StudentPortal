package gui.Student;

import com.company.DataWriter;
import com.company.models.Class;
import com.company.models.Course;
import com.company.models.Student;
import com.company.models.University;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CoursesPanelController implements ActionListener {
    private CoursesPanel view;
    private University university;
    private Student student;
    private String semesterName;
    private String departmentName;
    private String instructorName;
    private String courseName;
    private String feedback;
    private ArrayList<Course> courses;

    public CoursesPanelController(University university, Student student){
        this.university = university;
    }

    public void setView(CoursesPanel view) {
        this.view = view;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "View Courses":
                semesterName = view.getSemesterField().getText();

                // Implement logic to display available courses based on user input
                courses = university.getAvailableCourses(semesterName);

                // Clear the existing table data
                view.clearTableData();

                // Update the table with course data
                view.updateTable(courses);

                if(courses.size() == 0) {
                    view.displayMessage("No courses available for this semester", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "View Courses By Department":
                departmentName = view.getDepartmentField().getText();

                // Implement logic to display available courses based on user input
                courses = university.searchCourseByDepartment(departmentName);

                // Clear the existing table data
                view.clearTableData();

                // Update the table with course data
                view.updateTable(courses);

                if(courses.size() == 0){
                    view.displayMessage("No courses available for this department", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "View Courses By Professor":
                instructorName = view.getInstructorField().getText();

                // Implement logic to display available courses based on user input
                ArrayList<Course> courses = university.searchCourseByProfessor(instructorName);

                // Clear the existing table data
                view.clearTableData();

                // Update the table with course data
                view.updateTable(courses);

                if(courses.size() == 0){
                    view.displayMessage("No courses available for this instructor", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "View Course Details":
                courseName = view.getCourseNameField().getText();

                // Implement logic to get course details based on user input
                Course course = university.getCourseByName(courseName);

                if (course != null) {
                    // Clear the existing table data
                    view.getTableModel().setRowCount(0);

                    // Update the table with course details
                    view.getTableModel().addRow(new Object[]{course.getName(), course.getDepartment(), course.getDescription()});
                } else {
                    view.displayMessage("Course not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "View Course Schedule":
                semesterName = view.getSemesterField().getText();

                // Clear the existing table data
                view.clearTableData();

                // Implement logic to display course schedule based on user input
                university.getCourses().stream()
                        .flatMap(course2 -> course2.getClasses().stream())
                        .filter(class1 -> class1.getSemester().getName().equals(semesterName))
                        .forEach(class1 -> view.getTableModel().addRow(new Object[]{
                                class1.getCourse().getName(),
                                class1.getSemester().getName(),
                                class1.getSchedule().getClassTimings().getDayOfWeek(),
                                class1.getSchedule().getClassTimings().getTime(),
                                class1.getSchedule().getLocation()
                        }));
                break;
            case "Add to Favorites":
                courseName = view.getCourseNameField().getText();

                // Implement logic to add the course to favorites and provide user feedback
                feedback = DataWriter.addToFavoritesEntry(student, university, courseName, "src/com/company/data/favoriteCourses_data.csv");

                // Display user feedback in the result area
                view.setResultArea(feedback);
                break;
            case "Remove from Favorites":
                System.out.println("Remove from favorites clicked");
                courseName = view.getCourseNameField().getText();

                // Specify the file path where you want to store the favorites data
                String favoritesFilePath = "src/com/company/data/favoriteCourses_data.csv"; // Update with your actual file path

                // Implement logic to remove the course from favorites and provide user feedback
                feedback = DataWriter.removeFromFavoritesEntry(student, courseName, favoritesFilePath, university);

                // Display user feedback in the result area
                view.setResultArea(feedback);
                break;

            case "View Favorites":
                System.out.println("View favorites clicked");
                // Clear the existing table data
                view.clearTableData();

                // Implement logic to display favorites
                student.getFavoriteCourses().forEach(course1 -> view.getTableModel().addRow(new Object[]{course1.getName()}));

                //if there are no courses in the favorites list, say in the text area that there are no courses
                if (student.getFavoriteCourses().size() == 0) {
                    view.displayMessage("There are no courses in your favorites list.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "Enroll in Course":
                courseName = view.getCourseNameField().getText();
                semesterName = view.getSemesterField().getText();

                // Implement logic to enroll in the course
                String feedback = DataWriter.enrollInCourseEntry(student, university, courseName, semesterName, "src/com/company/data/studentEnrollment_data.csv");

                // Display feedback in the result area
                view.setResultArea(feedback);
                break;
        }
    }

}
