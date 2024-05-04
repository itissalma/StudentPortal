package gui;

import com.company.models.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class FormPanelController implements ActionListener {
    private FormPanel formPanel;
    private University university;
    private landing parentFrame;
    private Student student;
    private Professor professor;
    private Administrator admin;
    private Consumer<Student> studentSetListener;
    private Consumer<Professor> professorSetListener;
    private Consumer<Administrator> adminSetListener;

    public FormPanelController(University university, landing parentFrame) {
        this.university = university;
        this.parentFrame = parentFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
            case "Submit Button":
                System.out.println("Submit button pressed");
                String id = formPanel.getIdField().getText();

                try {
                    boolean isAdmin = false;
                    int idInt = Integer.parseInt(id);
                    System.out.println("The ID is " + idInt);
                    String password = new String(formPanel.getPasswordField().getPassword());

                    if (id.startsWith("1"))
                        isAdmin = true;

                    if (id.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (!isAdmin && university.findStudentByID(idInt) == null && university.findProfessorByID(idInt) == null) {
                        JOptionPane.showMessageDialog(null, "Invalid ID", "Error", JOptionPane.ERROR_MESSAGE);
                        // set fields to empty
                        formPanel.getIdField().setText("");
                        formPanel.getPasswordField().setText("");
                    } else {
                        if (id.startsWith("9")) {
                            formPanel.setUserType(UserType.STUDENT);
                            parentFrame.updateUserTypeAndMenu(formPanel.getUserType()); // Update the menu and user type
                            student = university.findStudentByID(idInt); // Replace with actual student data
                            parentFrame.setStudent(student);
                            parentFrame.switchToPanel(parentFrame.getStudentHomePanel());
                            // Notify the listener that the student is set
                            if (studentSetListener != null) {
                                studentSetListener.accept(student);
                            }
                        } else if (id.startsWith("8")) {
                            formPanel.setUserType(UserType.PROFESSOR);
                            parentFrame.updateUserTypeAndMenu(formPanel.getUserType()); // Update the menu and user type
                            professor = university.findProfessorByID(idInt); // Replace with actual student data
                            parentFrame.setProfessor(professor);
                            parentFrame.switchToPanel(parentFrame.getProfessorHomePanel());

                            if (professorSetListener != null) {
                                System.out.println("professorSetListener is not null");
                                professorSetListener.accept(professor);
                            }
                            System.out.println("the profess is " + parentFrame.getProfessor());
                        } else if (id.startsWith("1")) {
                            formPanel.setUserType(UserType.ADMIN);
                            admin = new Administrator(university.getName(), 1001, "admin1234");
                            parentFrame.setAdmin(admin);
                            parentFrame.updateUserTypeAndMenu(formPanel.getUserType()); // Update the menu and user type
                            // repaint so homepage text isn't displayed twice
                            parentFrame.switchToPanel(parentFrame.getAdminHomePanel());

                            if (adminSetListener != null) {
                                adminSetListener.accept(admin);
                            }
                        } else {
                            System.out.println("Invalid ID yo.");
                            JOptionPane.showMessageDialog(null, "Invalid ID.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        formPanel.getIdField().setText("");
                        formPanel.getPasswordField().setText("");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input for ID. Please enter a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
                    formPanel.getIdField().setText("");
                    formPanel.getPasswordField().setText("");
                }
                break;

        }
    }

    public void addStudentSetListener(Consumer<Student> listener) {
        this.studentSetListener = listener;
        // Check if student is available and not null
        if (student != null) {
            this.studentSetListener.accept(student); // Notify the listener with the current student
        }
    }

    public void addProfessorSetListener(Consumer<Professor> listener) {
        this.professorSetListener = listener;
        // Check if student is available and not null
        if (professor != null) {
            this.professorSetListener.accept(professor); // Notify the listener with the current student
        }
    }

    public void addAdminSetListener(Consumer<Administrator> listener) {
        this.adminSetListener = listener;
        // Check if student is available and not null
        if (admin != null) {
            this.adminSetListener.accept(admin); // Notify the listener with the current student
        }
    }

    public void setView(FormPanel formPanel) {
        this.formPanel = formPanel;
    }

    public void setUniversity (University university) {
        this.university = university;
    }

}
