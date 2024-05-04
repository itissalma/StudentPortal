package gui.Admin;
import com.company.models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminCoursesPanel extends JPanel {
    private AdminHomePanel parentPanel;
    private University university;
    private Administrator admin;
    private JTextField nameField;
    private JTextField departmentField;
    private JTextField descriptionField;
    private JComboBox<String> creditsComboBox;
    private JLabel messageLabel;
    private AdminCoursesPanelController controller;
    private JTextField oldCourseNameField;
    private JTextField newCourseNameField;
    private  JTextField semesterField;
    private JTextField professorIDField;
    private JTextField maxClassCapacityField;
    private JTextField classLocationField;
    private JTextField classTimeField;
    private JTextField newCapacityField;
    private JTextField sectionNumberField;
    private JTextField classDaysField;

    public AdminCoursesPanel(AdminHomePanel parentPanel, University university, Administrator admin, AdminCoursesPanelController controller) {
        this.parentPanel = parentPanel;
        this.university = university;
        this.admin = admin;
        this.controller = controller;

        setLayout(new BorderLayout());
    }

    public JTextField getSemesterField() {
        return semesterField;
    }

    public JTextField getProfessorIDField() {
        return professorIDField;
    }

    public JTextField getMaxClassCapacityField() {
        return maxClassCapacityField;
    }

    public JTextField getClassLocationField() {
        return classLocationField;
    }

    public JTextField getClassTimeField() {
        return classTimeField;
    }

    public JTextField getCourseName() {
        return nameField;
    }

    public JTextField getDepartment() {
        return departmentField;
    }

    public JTextField getDescription() {
        return descriptionField;
    }

    public int getNumOfCredits() {
        return Integer.parseInt((String) creditsComboBox.getSelectedItem());
    }

    public JTextField getOldCourseName() {
        return oldCourseNameField;
    }

    public JTextField getNewCourseName() {
        return newCourseNameField;
    }

    public JTextField getNewCapacityField() {
        return newCapacityField;
    }

    public JTextField getSectionNumberField() {
        return sectionNumberField;
    }

    public JTextField getClassDaysField() {
        return classDaysField;
    }

    public void clearInputFields() {
        nameField.setText("");
        departmentField.setText("");
        descriptionField.setText("");
        creditsComboBox.setSelectedIndex(0);
    }

    public void clearEditCapacityFields(){
        nameField.setText("");
        semesterField.setText("");
        newCapacityField.setText("");
        sectionNumberField.setText("");
    }

    public void clearUpdateCourseFields(){
        oldCourseNameField.setText("");
        newCourseNameField.setText("");
        departmentField.setText("");
    }

    public void clearAddClassFields(){
        nameField.setText("");
        semesterField.setText("");
        professorIDField.setText("");
        maxClassCapacityField.setText("");
        classLocationField.setText("");
        classTimeField.setText("");
        classDaysField.setText("");
    }

    public void displayMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    public JPanel viewCoursesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Course Name");
        tableModel.addColumn("Department");

        JTable courseTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(courseTable);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Increase the font size of the header cells
        JTableHeader tableHeader = courseTable.getTableHeader();
        Font headerFont = tableHeader.getFont();
        tableHeader.setFont(new Font(headerFont.getName(), Font.BOLD, 16)); // Change 16 to the desired font size

        // Implement logic to display available courses
        ArrayList<Course> courses = university.getCourses();

        // Update the table with course data
        courses.forEach(course -> tableModel.addRow(new Object[]{course.getName(), course.getDepartment()}));


        return panel;
    }


    public JPanel addCoursePanel() {
        JPanel addCoursePanel = new JPanel();
        addCoursePanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        // Course Name
        JLabel nameLabel = new JLabel("Course Name:");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        addCoursePanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        nameField = new JTextField(30);
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        addCoursePanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        // Department
        JLabel departmentLabel = new JLabel("Department:");
        departmentLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        addCoursePanel.add(departmentLabel, gbc);
        gbc.gridx = 1;
        departmentField = new JTextField(30);
        departmentField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        addCoursePanel.add(departmentField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        // Description
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        addCoursePanel.add(descriptionLabel, gbc);
        gbc.gridx = 1;
        descriptionField = new JTextField(30);
        descriptionField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        addCoursePanel.add(descriptionField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        // Number of Credits (ComboBox)
        JLabel creditsLabel = new JLabel("Number of Credits:");
        creditsLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        addCoursePanel.add(creditsLabel, gbc);
        gbc.gridx = 1;
        String[] creditOptions = { "1", "2", "3", "4" };
        creditsComboBox = new JComboBox<>(creditOptions);
        creditsComboBox.setFont(new Font("SansSerif", Font.PLAIN, 18));
        addCoursePanel.add(creditsComboBox, gbc);

        // Center and make the button the same width and increase button height
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2; // Span 2 columns
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton createButton = new JButton("Create Course");
        createButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        createButton.setPreferredSize(new Dimension(200, 30));
        addCoursePanel.add(createButton, gbc);

        // Create a JLabel for displaying messages
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        messageLabel = new JLabel();
        messageLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        addCoursePanel.add(messageLabel, gbc);

        createButton.setActionCommand("Create Button");
        createButton.addActionListener(controller);

        return addCoursePanel;
    }

    public JPanel updateCourseDetailsPanel() {
        JButton updateCourseButton; // Declare the button variable

        admin = parentPanel.getAdmin();
        if (admin == null) {
            System.out.println("Admin is null");
        }
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Change layout to GridBagLayout for consistent sizing and layout

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        // Increase the size of components
        int fieldWidth = 30;

        // Old Course Name
        JLabel oldCourseNameLabel = new JLabel("Old Course Name:");
        oldCourseNameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        panel.add(oldCourseNameLabel, gbc);

        gbc.gridx = 1;
        oldCourseNameField = new JTextField(fieldWidth);
        oldCourseNameField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        panel.add(oldCourseNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        // New Course Name
        JLabel newCourseNameLabel = new JLabel("New Course Name:");
        newCourseNameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        panel.add(newCourseNameLabel, gbc);

        gbc.gridx = 1;
        newCourseNameField = new JTextField(fieldWidth);
        newCourseNameField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        panel.add(newCourseNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        // Department
        JLabel departmentLabel = new JLabel("Department:");
        departmentLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        panel.add(departmentLabel, gbc);

        gbc.gridx = 1;
        departmentField = new JTextField(fieldWidth);
        departmentField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        panel.add(departmentField, gbc);

        // Create a button panel
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2; // Span 2 columns
        gbc.fill = GridBagConstraints.HORIZONTAL;
        updateCourseButton = new JButton("Update Course");
        updateCourseButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        updateCourseButton.setPreferredSize(new Dimension(200, 30));
        panel.add(updateCourseButton, gbc);

        // Create a JLabel for displaying messages
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2; // Span 2 columns
        gbc.fill = GridBagConstraints.HORIZONTAL;
        messageLabel = new JLabel();
        messageLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        panel.add(messageLabel, gbc);

        updateCourseButton.setActionCommand("Update Course Button");
        updateCourseButton.addActionListener(controller);

        return panel;
    }


    public JPanel createAddClassPanel() {
        JPanel addClassPanel = new JPanel();
        addClassPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        // Course Name
        JLabel courseNameLabel = new JLabel("Course Name:");
        courseNameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        addClassPanel.add(courseNameLabel, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        nameField = new JTextField(30);
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        addClassPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        // Semester Name
        JLabel semesterLabel = new JLabel("Semester:");
        semesterLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        addClassPanel.add(semesterLabel, gbc);
        gbc.gridx = 1;
        semesterField = new JTextField(30);
        semesterField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        addClassPanel.add(semesterField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        // Professor's ID
        JLabel professorIDLabel = new JLabel("Professor's ID:");
        professorIDLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        addClassPanel.add(professorIDLabel, gbc);
        gbc.gridx = 1;
        professorIDField = new JTextField(30);
        professorIDField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        addClassPanel.add(professorIDField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        // Max Class Capacity
        JLabel maxClassCapacityLabel = new JLabel("Max Class Capacity:");
        maxClassCapacityLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        addClassPanel.add(maxClassCapacityLabel, gbc);
        gbc.gridx = 1;
        maxClassCapacityField = new JTextField(30);
        maxClassCapacityField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        addClassPanel.add(maxClassCapacityField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        // Location
        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        addClassPanel.add(locationLabel, gbc);
        gbc.gridx = 1;
        classLocationField = new JTextField(30);
        classLocationField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        addClassPanel.add(classLocationField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        // Time
        JLabel timeLabel = new JLabel("Time:");
        timeLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        addClassPanel.add(timeLabel, gbc);
        gbc.gridx = 1;
        classTimeField = new JTextField(30);
        classTimeField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        addClassPanel.add(classTimeField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        // Days
        JLabel daysLabel = new JLabel("Days:");
        daysLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        addClassPanel.add(daysLabel, gbc);
        gbc.gridx = 1;
        classDaysField = new JTextField(30);
        classDaysField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        addClassPanel.add(classDaysField, gbc);

        // Center and make the button the same width and increase button height
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2; // Span 2 columns
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton addClassButton = new JButton("Add Class");
        addClassButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        addClassButton.setPreferredSize(new Dimension(200, 30));
        addClassPanel.add(addClassButton, gbc);

        // Create a JLabel for displaying messages
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        messageLabel = new JLabel();
        messageLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        addClassPanel.add(messageLabel, gbc);

        addClassButton.setActionCommand("Add Class Button");
        addClassButton.addActionListener(controller);

        return addClassPanel;
    }


    public JPanel editCapacityPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create input fields and labels
        nameField = new JTextField(20);
        semesterField = new JTextField(20);
        newCapacityField = new JTextField(20);
        sectionNumberField = new JTextField(20);

        // Create the "Edit Capacity" button
        JButton editCapacityButton = new JButton("Edit Capacity");

        // Create a main content panel with GridBagLayout
        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(new JLabel("Enter the name of the course: "), gbc);
        gbc.gridx = 1;
        contentPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        contentPanel.add(new JLabel("Enter the semester: "), gbc);
        gbc.gridx = 1;
        contentPanel.add(semesterField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        contentPanel.add(new JLabel("Enter the new capacity: "), gbc);
        gbc.gridx = 1;
        contentPanel.add(newCapacityField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        contentPanel.add(new JLabel("Enter the section number: "), gbc);
        gbc.gridx = 1;
        contentPanel.add(sectionNumberField, gbc);

        // Create a button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(editCapacityButton);

        // Add components to the main panel
        panel.add(contentPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        editCapacityButton.setActionCommand("Edit Capacity Button");
        editCapacityButton.addActionListener(controller);

        return panel;
    }

}
