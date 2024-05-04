package gui.Admin;

import com.company.DataWriter;
import com.company.models.Administrator;
import com.company.models.University;
import gui.landing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminStudentsPanelController implements ActionListener {
    private AdminStudentsPanel view;
    private AdminHomePanel adminHomePanel;
    private landing app;
    private University university;
    private Administrator admin;

    private String studentName;
    private String courseName;
    private String semester;

    public AdminStudentsPanelController(Administrator admin, University university) {
        this.admin = admin;
        this.university = university;
    }

    public void setView(AdminStudentsPanel view) {
        this.view = view;
    }

    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Enroll Button":
                try {
                    studentName = view.getStudentNameField().getText();
                    courseName = view.getCourseNameField().getText();
                    semester = view.getSemesterField().getText();

                    // Validate input and handle exceptions
                    if (studentName.isEmpty() || courseName.isEmpty() || semester.isEmpty()) {
                        view.displayMessage("All fields must be filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Specify the file path where you want to store the enrollment data
                    String filePath = "src/com/company/data/studentEnrollment_data.csv";

                    // Call the writeEnrollmentEntry function and provide user feedback
                    String feedback = DataWriter.writeEnrollmentEntry(filePath, university, studentName, courseName, semester);

                    // Display user feedback using an output dialog
                    view.displayMessage(feedback, "Enrollment Result", JOptionPane.INFORMATION_MESSAGE);

                    // Clear input fields
                    view.clearEnrollButtonFields();
                } catch (Exception ex) {
                    // Handle exceptions
                    view.displayMessage("An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;

        }
    }
}
