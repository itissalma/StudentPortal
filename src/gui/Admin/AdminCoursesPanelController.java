package gui.Admin;

import com.company.DataWriter;
import com.company.models.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AdminCoursesPanelController implements ActionListener{
    private AdminCoursesPanel view;
    private Administrator admin;
    private University university;
    private AdminHomePanel adminHomePanel;
    String filePath;

    public AdminCoursesPanelController( Administrator admin, University university) {
        this.admin = admin;
        this.university = university;
    }

    public void setView(AdminCoursesPanel view) {
        this.view = view;
    }

    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }

        @Override
        public void actionPerformed(ActionEvent e) {
            String courseName;
            String oldCourseName;
            String newCourseName;
            String department;
            String description;
            String semesterName;
            String newCapacityStr;
            String sectionNumberStr;
            int professorID;
            int maxClassCapacity;
            int numOfCredits;

            //define tags and switch on them
            String command = e.getActionCommand();
            switch (command) {
                case "Create Button":
                    try {
                        // Specify the file path where you want to store the data
                        filePath = "src/com/company/data/courses_data.csv";

                        // Create an instance of BufferedWriter for writing to the file
                        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));

                        // Instantiate DataWriter with the BufferedWriter
                        DataWriter dataWriter = new DataWriter(writer);

                        // Get data from the view
                        courseName = view.getCourseName().getText();
                        department = view.getDepartment().getText();
                        description = view.getDescription().getText();
                        numOfCredits = view.getNumOfCredits();

                        // Use DataWriter to write the course entry
                        if (dataWriter.writeCourseEntry(university, writer, courseName, department, description, numOfCredits)) {
                            // Display success message
                            view.displayMessage("Course created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            // Clear the input fields if needed
                            view.clearInputFields();
                        } else {
                            // Display error message
                            view.displayMessage("An error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                        // Close the BufferedWriter after writing
                        writer.close();

                    } catch (IOException e1) {
                        // Handle IOException if needed
                        e1.printStackTrace();
                    }
                    break;
                case "Update Course Button":
                    try {
                        oldCourseName = view.getOldCourseName().getText();
                        newCourseName = view.getNewCourseName().getText();
                        department = view.getDepartment().getText();

                        // Validate input and handle exceptions
                        if (oldCourseName.isEmpty() || newCourseName.isEmpty() || department.isEmpty()) {
                            throw new IllegalArgumentException("All fields must be filled.");
                        }

                        // Call the updateCourseEntry function and provide user feedback
                        String feedback = DataWriter.updateCourseEntry(university, "src/com/company/data/courses_data.csv", oldCourseName, newCourseName, department, "");

                        if (feedback.equals("Course updated")) {
                            view.displayMessage(feedback, "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            view.displayMessage(feedback, "Error", JOptionPane.ERROR_MESSAGE);
                        }

                        // Clear the input fields
                        view.clearUpdateCourseFields();
                    } catch (Exception ex) {
                        // Handle exceptions
                        view.displayMessage("An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case "Add Class Button":
                    // Specify the file path where you want to store the data
                    String filePath = "src/com/company/data/classes_data.csv";

                    try {
                        // Create an instance of BufferedWriter for writing to the file
                        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));

                        // Instantiate DataWriter with the BufferedWriter
                        DataWriter dataWriter = new DataWriter(writer);

                        courseName = view.getCourseName().getText();
                        semesterName = view.getSemesterField().getText();

                        try {
                            professorID = Integer.parseInt(view.getProfessorIDField().getText());
                            maxClassCapacity = Integer.parseInt(view.getMaxClassCapacityField().getText());
                        } catch (NumberFormatException ex) {
                            view.displayMessage("Professor ID and Max Class Capacity must be valid numbers.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        String location = view.getClassLocationField().getText();
                        String time = view.getClassTimeField().getText();
                        String day = view.getClassDaysField().getText();

                        if (courseName.isEmpty() || semesterName.isEmpty() || location.isEmpty() || time.isEmpty() || day.isEmpty()) {
                            view.displayMessage("All fields must be filled.", "Missing Information", JOptionPane.ERROR_MESSAGE);
                        } else {
                            // Call the writeClassEntry function and provide user feedback
                            if (dataWriter.writeClassEntry(university, writer, courseName, semesterName, professorID, maxClassCapacity, location, time, day)) {
                                view.displayMessage("Class added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                view.displayMessage("Error adding class", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            //CLEAR FIELDS
                            view.clearAddClassFields();
                        }

                        // Close the BufferedWriter after writing
                        writer.close();

                    } catch (IOException e1) {
                        // Handle IOException if needed
                        e1.printStackTrace();
                    }
                    break;

                case "Edit Capacity Button":
                    courseName = view.getCourseName().getText();
                    semesterName = view.getSemesterField().getText();
                    newCapacityStr = view.getNewCapacityField().getText();
                    sectionNumberStr = view.getSectionNumberField().getText();

                    if (courseName.isEmpty() || semesterName.isEmpty()) {
                        view.displayMessage("Course name and semester are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int newCapacity, sectionNumber;
                    try {
                        newCapacity = Integer.parseInt(newCapacityStr);
                        sectionNumber = Integer.parseInt(sectionNumberStr);
                    } catch (NumberFormatException ex) {
                        view.displayMessage("New capacity and section number must be integers.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Specify the file path where you want to store the data
                    filePath = "src/com/company/data/classes_data.csv";

                    // Call the editCapacityEntry function and provide user feedback
                    String feedback = DataWriter.editCapacityEntry(filePath, courseName, semesterName, newCapacity, sectionNumber);

                    // Display user feedback using an output dialog
                    if (feedback.equals("Class capacity updated")) {
                        view.displayMessage(feedback, "Edit Capacity Result", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        view.displayMessage(feedback, "Edit Capacity Result", JOptionPane.ERROR_MESSAGE);
                    }

                    // Clear the input fields
                    view.clearEditCapacityFields();
                    break;

            }
        }

    public boolean createCourse(University university, String courseName, String department, String description, int numOfCredits) {
        try {
            Course adminCourse = new Course(courseName, department, description, numOfCredits);
            university.addCourse(adminCourse);
            return true;
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return false;
        }
    }


}

