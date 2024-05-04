package gui.Professor;
import com.company.models.*;
import com.company.models.Class;
import gui.Student.CoursesPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ProfessorPanels extends JPanel {
    private ProfessorHomePanel parentPanel;
    private CoursesPanel coursesPanel;
    private University university;
    private Professor professor;
    private JButton submitButton;
    private ProfessorPanelsController controller;

    private JTextField studentIDField;
    private JTextField courseNameField;
    private JComboBox<String> gradeComboBox;
    private JCheckBox attendanceCheckBox;

    public ProfessorPanels(ProfessorHomePanel parentPanel, University university, Professor professor, ProfessorPanelsController controller){
        this.parentPanel = parentPanel;
        this.university = university;
        this.professor = professor;
        this.coursesPanel = coursesPanel;
        this.controller = controller;
        System.out.println("First controller is " + controller);
        setLayout(new BorderLayout());
    }

    public JTextField getStudentIDField() {
        return studentIDField;
    }

    public JTextField getCourseNameField() {
        return courseNameField;
    }

    public JComboBox<String> getGradeComboBox() {
        return gradeComboBox;
    }

    public JCheckBox getAttendanceCheckBox() {
        return attendanceCheckBox;
    }

    public void displayMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    public void clearSubmitGradeFields(){
        studentIDField.setText("");
        courseNameField.setText("");
    }

    public JPanel createGradeEntryPanel(List<String> gradeChoices) {
        System.out.println("Creating grade entry panel.");
        professor = parentPanel.getProfessor();
        JPanel gradeEntryPanel = new JPanel();
        gradeEntryPanel.setLayout(new BorderLayout());

        // Create a panel for entering data
        JPanel dataEntryPanel = new JPanel();
        dataEntryPanel.setLayout(new FlowLayout());

        // Input student ID as int and give an error if not int
        studentIDField = new JTextField(10);
        dataEntryPanel.add(new JLabel("Student ID: "));
        dataEntryPanel.add(studentIDField);

        // Input course name
        courseNameField = new JTextField(10);
        dataEntryPanel.add(new JLabel("Course Name: "));
        dataEntryPanel.add(courseNameField);

        // Create a combo box for grades
        gradeComboBox = new JComboBox<>(gradeChoices.toArray(new String[0]));
        gradeComboBox.setPreferredSize(new Dimension(100, 30));
        dataEntryPanel.add(new JLabel("Grade: "));
        dataEntryPanel.add(gradeComboBox);

        gradeEntryPanel.add(dataEntryPanel, BorderLayout.CENTER);

        // Create a submit button
        submitButton = new JButton("Submit Grade");
        gradeEntryPanel.add(submitButton, BorderLayout.SOUTH);

        submitButton.setActionCommand("Submit Grade");
        submitButton.addActionListener(controller);
        System.out.println("Second controller is " + controller);


        return gradeEntryPanel;
    }

    public JPanel takeAttendancePanel() {
        professor = parentPanel.getProfessor();
        JPanel attendancePanel = new JPanel();
        attendancePanel.setLayout(new BorderLayout());

        JPanel dataEntryPanel = new JPanel();
        dataEntryPanel.setLayout(new FlowLayout());

        studentIDField = new JTextField(10);
        dataEntryPanel.add(new JLabel("Student ID: "));
        dataEntryPanel.add(studentIDField);

        courseNameField = new JTextField(10);
        dataEntryPanel.add(new JLabel("Course Name: "));
        dataEntryPanel.add(courseNameField);

        attendanceCheckBox = new JCheckBox("Attended?");
        dataEntryPanel.add(attendanceCheckBox);
        //add a tool kit to explain what the checkbox is for
        attendanceCheckBox.setToolTipText("Check if student attended class");

        attendancePanel.add(dataEntryPanel, BorderLayout.CENTER);

        JButton takeAttendanceButton = new JButton("Take Attendance");
        takeAttendanceButton.setActionCommand("Take Attendance");
        takeAttendanceButton.addActionListener(controller);

        System.out.println("The professor is " + professor.getName());

        attendancePanel.add(takeAttendanceButton, BorderLayout.SOUTH);

        return attendancePanel;
    }


}
