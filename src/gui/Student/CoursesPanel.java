package gui.Student;
import com.company.models.Course;
import com.company.models.Class;
import com.company.models.Student;
import com.company.models.University;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CoursesPanel extends JPanel {
    private StudentHomePanel parentPanel;
    private CoursesPanelController controller;
    private University university;
    private Student student;
    private JTextField semesterField;
    private JTextField departmentField;
    private JTextField instructorField;
    private JTextField courseNameField;
    private DefaultTableModel tableModel;
    private JTextArea resultArea;

    public CoursesPanel(StudentHomePanel parentPanel, University university, CoursesPanelController controller) {
        this.parentPanel = parentPanel;
        this.university = university;
        this.student = parentPanel.getStudent();
        this.controller = controller;
        setLayout(new BorderLayout());
    }
    public JTextField getSemesterField() {
        return semesterField;
    }

    public JTextField getDepartmentField() {
        return departmentField;
    }

    public JTextField getInstructorField() {
        return instructorField;
    }

    public JTextField getCourseNameField() {
        return courseNameField;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void displayMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    public void clearTableData() {
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }
    }

    public void updateTable (ArrayList<Course> courses){
        //use streams to do it
        courses.stream().forEach(course -> tableModel.addRow(new Object[]{course.getName(), course.getDepartment()}));
    }

    public void setResultArea(String result) {
        resultArea.setText(result);
    }

    public JPanel viewCoursesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Add components for viewing courses by semester
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        inputPanel.add(new JLabel("Enter the semester: "));
        semesterField = new JTextField(20);
        inputPanel.add(semesterField);
        JButton viewCoursesButton = new JButton("View Courses");
        inputPanel.add(viewCoursesButton);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Course Name");
        tableModel.addColumn("Department");

        JTable courseTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(courseTable);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Increase the font size of the header cells
        JTableHeader tableHeader = courseTable.getTableHeader();
        Font headerFont = tableHeader.getFont();
        tableHeader.setFont(new Font(headerFont.getName(), Font.BOLD, 16)); // Change 16 to the desired font size

        viewCoursesButton.setActionCommand("View Courses");
        viewCoursesButton.addActionListener(controller);

        return panel;
    }

    public JPanel viewCoursesByDepartmentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Add components for viewing courses by department
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        inputPanel.add(new JLabel("Enter the department: "));
        departmentField = new JTextField(20);
        inputPanel.add(departmentField);
        JButton viewCoursesButton = new JButton("View Courses");
        inputPanel.add(viewCoursesButton);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Course Name"); // Include only the "Course Name" column

        JTable courseTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(courseTable);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Increase the font size of the header cells
        JTableHeader tableHeader = courseTable.getTableHeader();
        Font headerFont = tableHeader.getFont();
        tableHeader.setFont(new Font(headerFont.getName(), Font.BOLD, 16)); // Change 16 to the desired font size
        tableHeader.setReorderingAllowed(false); // Prevent column reordering

        viewCoursesButton.setActionCommand("View Courses By Department");
        viewCoursesButton.addActionListener(controller);

        return panel;
    }

    public JPanel viewCoursesByProfessorPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Add components for viewing courses by instructor
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        inputPanel.add(new JLabel("Enter the instructor: "));
        instructorField = new JTextField(20);
        inputPanel.add(instructorField);
        JButton viewCoursesButton = new JButton("View Courses");
        inputPanel.add(viewCoursesButton);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Course Name"); // Include only the "Course Name" column

        JTable courseTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(courseTable);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Increase the font size of the header cells
        JTableHeader tableHeader = courseTable.getTableHeader();
        Font headerFont = tableHeader.getFont();
        tableHeader.setFont(new Font(headerFont.getName(), Font.BOLD, 16)); // Change 16 to the desired font size
        tableHeader.setReorderingAllowed(false); // Prevent column reordering

        viewCoursesButton.setActionCommand("View Courses By Professor");
        viewCoursesButton.addActionListener(controller);

        return panel;
    }

    public JPanel viewCourseDetailsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Add components for viewing course details
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        inputPanel.add(new JLabel("Enter the course name: "));
        courseNameField = new JTextField(20);
        inputPanel.add(courseNameField);
        JButton viewDetailsButton = new JButton("View Course Details");
        inputPanel.add(viewDetailsButton);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Course Name");
        tableModel.addColumn("Department");
        tableModel.addColumn("Description");

        JTable courseTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(courseTable);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Increase the font size of the header cells
        JTableHeader tableHeader = courseTable.getTableHeader();
        Font headerFont = tableHeader.getFont();
        tableHeader.setFont(new Font(headerFont.getName(), Font.BOLD, 16)); // Change 16 to the desired font size
        tableHeader.setReorderingAllowed(false); // Prevent column reordering

        viewDetailsButton.setActionCommand("View Course Details");
        viewDetailsButton.addActionListener(controller);

        return panel;
    }

    public JPanel getCourseSchedulePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Add components for viewing course schedule
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        inputPanel.add(new JLabel("Enter the semester: "));
        semesterField = new JTextField(20);
        inputPanel.add(semesterField);
        JButton viewScheduleButton = new JButton("View Course Schedule");
        inputPanel.add(viewScheduleButton);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Course Name");
        tableModel.addColumn("Semester");
        tableModel.addColumn("Day of the Week");
        tableModel.addColumn("Time");
        tableModel.addColumn("Location");

        JTable scheduleTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(scheduleTable);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Increase the font size of the header cells
        JTableHeader tableHeader = scheduleTable.getTableHeader();
        Font headerFont = tableHeader.getFont();
        tableHeader.setFont(new Font(headerFont.getName(), Font.BOLD, 16)); // Change 16 to the desired font size
        tableHeader.setReorderingAllowed(false); // Prevent column reordering

        viewScheduleButton.setActionCommand("View Course Schedule");
        viewScheduleButton.addActionListener(controller);

        return panel;
    }


    public JPanel addToFavoritesPanel() {
        student = parentPanel.getStudent();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Add components for adding a course to favorites
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        inputPanel.add(new JLabel("Enter the course name: "));
        courseNameField = new JTextField(20);
        inputPanel.add(courseNameField);
        JButton addToFavoritesButton = new JButton("Add to Favorites");
        inputPanel.add(addToFavoritesButton);

        resultArea = new JTextArea(4, 40);
        resultArea.setEditable(false);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(resultArea, BorderLayout.CENTER);

        addToFavoritesButton.setActionCommand("Add to Favorites");
        addToFavoritesButton.addActionListener(controller);

        return panel;
    }

    public JPanel removeCourseFromFavorites(){
        student = parentPanel.getStudent();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Add components for removing a course from favorites
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        inputPanel.add(new JLabel("Enter the course name: "));
        courseNameField = new JTextField(20);
        inputPanel.add(courseNameField);
        JButton removeFromFavoritesButton = new JButton("Remove from Favorites");
        inputPanel.add(removeFromFavoritesButton);

        resultArea = new JTextArea(4, 40);
        resultArea.setEditable(false);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(resultArea, BorderLayout.CENTER);

        removeFromFavoritesButton.setActionCommand("Remove from Favorites");
        removeFromFavoritesButton.addActionListener(controller);

        return panel;
    }

    public JPanel viewFavoriteCoursesPanel() {
        student = parentPanel.getStudent();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Add components for viewing favorites
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton viewFavoritesButton = new JButton("View Favorites");
        inputPanel.add(viewFavoritesButton);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Course Name");

        JTable favoritesTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(favoritesTable);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Increase the font size of the header cells
        JTableHeader tableHeader = favoritesTable.getTableHeader();
        Font headerFont = tableHeader.getFont();
        tableHeader.setFont(new Font(headerFont.getName(), Font.BOLD, 16)); // Change 16 to the desired font size
        tableHeader.setReorderingAllowed(false); // Prevent column reordering

        viewFavoritesButton.setActionCommand("View Favorites");
        viewFavoritesButton.addActionListener(controller);

        return panel;
    }

    public JPanel enrollInCoursePanel() {
        student = parentPanel.getStudent();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Add components for enrolling in a course
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel courseNameLabel = new JLabel("Enter the course name: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(5, 5, 5, 5); // Add some padding
        inputPanel.add(courseNameLabel, constraints);

        courseNameField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        inputPanel.add(courseNameField, constraints);

        JLabel semesterLabel = new JLabel("Enter the semester: ");
        constraints.gridx = 0;
        constraints.gridy = 1;
        inputPanel.add(semesterLabel, constraints);

        semesterField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        inputPanel.add(semesterField, constraints);

        JButton enrollButton = new JButton("Enroll in Course");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2; // Make the button span two columns
        inputPanel.add(enrollButton, constraints);

        resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(resultArea, BorderLayout.CENTER);

        enrollButton.setActionCommand("Enroll in Course");
        enrollButton.addActionListener(controller);

        return panel;
    }


}
