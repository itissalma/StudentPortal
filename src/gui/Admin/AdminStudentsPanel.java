package gui.Admin;
import com.company.models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AdminStudentsPanel extends JPanel {
    private AdminHomePanel parentPanel;
    private University university;
    Administrator admin;
    private AdminStudentsPanelController controller;
    private JTextField studentNameField;
    private JTextField courseNameField;
    private JTextField semesterField;


    public AdminStudentsPanel(AdminHomePanel parentPanel, University university, Administrator admin, AdminStudentsPanelController controller) {
        this.parentPanel = parentPanel;
        this.university = university;
        this.admin = admin;
        this.controller = controller;
        setLayout(new BorderLayout());
    }

    public JTextField getStudentNameField() {
        return studentNameField;
    }

    public JTextField getCourseNameField() {
        return courseNameField;
    }

    public JTextField getSemesterField() {
        return semesterField;
    }

    public void clearEnrollButtonFields(){
        studentNameField.setText("");
        courseNameField.setText("");
        semesterField.setText("");
    }

    public void displayMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    public JPanel getStudentsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Student Name"); // Display only student names

        JTable studentTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(studentTable);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Increase the font size of the header cells
        JTableHeader tableHeader = studentTable.getTableHeader();
        Font headerFont = tableHeader.getFont();
        tableHeader.setFont(new Font(headerFont.getName(), Font.BOLD, 16)); // Change 16 to the desired font size

        // Implement logic to display available students
        List<Student> students = university.getStudents();

        // Update the table with student names
        for (Student student : students) {
            tableModel.addRow(new Object[]{student.getName()}); // Display only student names
        }

        return panel;
    }

    public JPanel enrollStudentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Student Name
        JLabel studentNameLabel = new JLabel("Student Name:");
        studentNameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(studentNameLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        studentNameField = new JTextField(20);
        studentNameField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        panel.add(studentNameField, gbc);

        // Course Name
        JLabel courseNameLabel = new JLabel("Course Name:");
        courseNameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(courseNameLabel, gbc);

        gbc.gridx = 1;
        courseNameField = new JTextField(20);
        courseNameField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        panel.add(courseNameField, gbc);

        // Semester
        JLabel semesterLabel = new JLabel("Semester:");
        semesterLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(semesterLabel, gbc);

        gbc.gridx = 1;
        semesterField = new JTextField(20);
        semesterField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        panel.add(semesterField, gbc);

        // Enroll Button
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton enrollButton = new JButton("Enroll Student");
        enrollButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        enrollButton.setPreferredSize(new Dimension(200, 40));
        panel.add(enrollButton, gbc);

        enrollButton.setActionCommand("Enroll Button");
        enrollButton.addActionListener(controller);

        return panel;
    }


    public JPanel generateReportPanel() {
        ArrayList<Student> students = university.getStudents();
        JPanel panel = new JPanel(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Student Name");
        tableModel.addColumn("Course");
        tableModel.addColumn("Grade");
        tableModel.addColumn("Attendance");
        tableModel.addColumn("Absence");

        JTable reportTable = new JTable(tableModel);
        reportTable.setFont(new Font("SansSerif", Font.PLAIN, 16));

        try {
            if (students != null) {
                // Iterate through each student and their grades
                for (Student student : students) {
                    if (student != null && student.getAcademicTranscript() != null) {
                        for (StudentGrade studentGrade : student.getAcademicTranscript().getStudentGrades()) {
                            String studentName = student.getName();
                            String courseName = studentGrade.getStudentClass().getCourse().getName();
                            String grade = studentGrade.getGrade();
                            int attendance = studentGrade.getAttendance();
                            int absence = studentGrade.getAbsence();

                            // Add a row to the table with student performance information
                            tableModel.addRow(new Object[]{studentName, courseName, grade, attendance, absence});
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Handle any exceptions as needed
            e.printStackTrace();
        }

        JScrollPane tableScrollPane = new JScrollPane(reportTable);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        return panel;
    }

}
