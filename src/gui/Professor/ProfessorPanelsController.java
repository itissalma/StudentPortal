package gui.Professor;

import com.company.DataWriter;
import com.company.models.*;
import com.company.models.Class;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfessorPanelsController implements ActionListener {
    private ProfessorPanels view;
    private University university;
    private Professor professor;
    private String studentIDText;
    private String courseName;
    private String grade;

    public ProfessorPanelsController(University university, Professor professor) {
        //this.view = professorPanels;
        this.university = university;
        this.professor = professor;
        System.out.println("ProfessorPanelsController initialized." + ProfessorPanelsController.this);
    }

    public void setView(ProfessorPanels view) {
        this.view = view;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        System.out.println("Action performed: " + command);

        switch (command) {
            case "Submit Grade":
                System.out.println("Submit Grade button pressed.");
                studentIDText = view.getStudentIDField().getText();
                courseName = view.getCourseNameField().getText();
                grade = (String) view.getGradeComboBox().getSelectedItem();

                try {
                    int studentID2 = Integer.parseInt(studentIDText);

                    Student student7 = university.findStudentByID(studentID2);
                    Course course5 = university.getCourseByName(courseName);
                    Class class8 = course5.findClassByStudent(student7);

                    if (class8 == null) {
                        view.displayMessage("Class not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (student7 == null) {
                        view.displayMessage("Student not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (course5 == null) {
                        view.displayMessage("Course not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Call the new function to submit the grade and write to a file
                        int professorID = professor.getProfessorID();
                        DataWriter.inputGradesEntry(student7, class8, grade, "src/com/company/data/grades_data.csv", professorID);

                        JOptionPane.showMessageDialog(null, "Grade submitted.");
                        // Clear the text fields
                        view.clearSubmitGradeFields();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid student ID. Please enter a number.");
                }
                break;

            case "Take Attendance":
                studentIDText = view.getStudentIDField().getText();
                courseName = view.getCourseNameField().getText();
                boolean isPresent = view.getAttendanceCheckBox().isSelected();

                try {
                    int studentID = Integer.parseInt(studentIDText);
                    Student student = university.findStudentByID(studentID);
                    Course course = university.getCourseByName(courseName);
                    Class class1 = course.findClassByStudent(student); //find class by student in course

                    if(professor == null){
                        System.out.println("Professor is null");
                    }

                    if (student != null && course != null&& class1 != null) {
                        int professorID = professor.getProfessorID();
                        DataWriter.takeAttendanceEntry(student, class1, isPresent, "src/com/company/data/takeAttendance_data.csv",professorID );
                        view.displayMessage("Attendance taken.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        view.clearSubmitGradeFields();
                    } else {
                        view.displayMessage("Student, course, or course not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    view.displayMessage("Invalid student ID. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    System.out.println("here yo");
                    view.displayMessage("An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }
        }
    }
