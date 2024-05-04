package gui.Admin;

import com.company.DataWriter;
import com.company.models.Administrator;
import com.company.models.University;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminProfessorsPanelController implements ActionListener {
    private AdminProfessorsPanel view;
    private AdminHomePanel adminHomePanel;
    private Administrator admin;
    private University university;
    private String professorName;
    private String department;
    private String professorIDStr;

    public AdminProfessorsPanelController(Administrator admin, University university) {
        this.admin = admin;
        this.university = university;
    }

    public void setView(AdminProfessorsPanel view) {
        this.view = view;
    }

    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch (command) {
                case "Add Professor Button":
                    try {
                        professorName = view.getProfessorNameField().getText();
                        department = view.getDepartmentField().getText();

                        // Validate input and handle exceptions
                        if (professorName.isEmpty() || department.isEmpty()) {
                            view.displayMessage("All fields must be filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Specify the file path where you want to store the data
                        String filePath = "src/com/company/data/professor_data.csv";

                        // Call the addProfessorEntry function and provide user feedback
                        String feedback = DataWriter.addProfessorEntry(filePath, professorName, department, university);

                        // Display user feedback using an output dialog
                        view.displayMessage(feedback, "Add Professor Result", JOptionPane.INFORMATION_MESSAGE);

                        // Clear the input fields
                        view.clearAddProfessorFields();
                    } catch (Exception ex) {
                        // Handle exceptions
                        view.displayMessage("An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case "Remove Professor Button":
                    try {
                        professorIDStr = view.getProfessorNameField().getText();

                        // Validate input and handle exceptions
                        if (professorIDStr.isEmpty()) {
                            view.displayMessage("Professor ID is empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        int professorID = Integer.parseInt(professorIDStr);

                        // Specify the file path where you want to store the data
                        String filePath = "src/com/company/data/professor_data.csv";

                        // Call the removeProfessorEntry function and provide user feedback
                        String feedback = DataWriter.removeProfessorEntry(filePath, professorID);

                        // Display user feedback using an output dialog
                        view.displayMessage(feedback, "Remove Professor Result", JOptionPane.INFORMATION_MESSAGE);

                        // Clear the input fields
                        view.clearRemoveProfessorFields();
                    } catch (Exception ex) {
                        // Handle exceptions
                        view.displayMessage("An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;


            }
    }
}
