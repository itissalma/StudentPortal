package gui.Admin;

import com.company.models.University;
import gui.Student.CoursesPanel;
import gui.landing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminNavMenu extends JMenuBar {
    private landing app;
    private University university;
    private AdminHomePanel adminHomePanel;
    private AdminCoursesPanel adminCoursesPanel;
    private AdminProfessorsPanel adminProfessorsPanel;
    private CoursesPanel coursesPanel;
    private AdminStudentsPanel adminStudentsPanel;
    private AdminCoursesPanelController adminCoursesPanelController;
    private AdminProfessorsPanelController adminProfessorsPanelController;
    private AdminStudentsPanelController adminStudentsPanelController;
    private AdminNavMenuController controller;

    public AdminNavMenu(landing app, AdminHomePanel adminHomePanel, University university, AdminCoursesPanel adminCoursesPanel,
                        AdminProfessorsPanel adminProfessorsPanel, AdminStudentsPanel adminStudentsPanel, AdminNavMenuController controller) {
        this.app = app;
        this.university = university;
        this.adminHomePanel = adminHomePanel;
        this.adminCoursesPanel = adminCoursesPanel;
        this.adminProfessorsPanel = adminProfessorsPanel;
        this.adminStudentsPanel = adminStudentsPanel;
        this.coursesPanel = coursesPanel;
        this.controller = controller;

        JMenuBar menuBar = new JMenuBar();

        // Courses menu
        JMenu coursesMenu = new JMenu("Courses");
        coursesMenu.setFont(new Font("sansserif", Font.PLAIN, 18));
        coursesMenu.setBackground(new Color(7, 164, 121));

        JMenuItem addClass = new JMenuItem("Add Class");
        JMenuItem editClass = new JMenuItem("Edit Class Capacity");
        JMenuItem viewCoursesItem = new JMenuItem("View Courses");
        JMenuItem addCourseItem = new JMenuItem("Add Course");
        JMenuItem changeCourseDetailsItem = new JMenuItem("Change Course Details");

        coursesMenu.add(viewCoursesItem);
        coursesMenu.add(addCourseItem);
        coursesMenu.add(changeCourseDetailsItem);
        coursesMenu.add(addClass);
        coursesMenu.add(editClass);

        // Professors menu
        JMenu professorsMenu = new JMenu("Professors");
        professorsMenu.setFont(new Font("sansserif", Font.PLAIN, 18));
        professorsMenu.setBackground(new Color(7, 164, 121));

        JMenuItem viewProfessorsItem = new JMenuItem("View Professors");
        JMenuItem addProfessorItem = new JMenuItem("Add Professor");
        JMenuItem removeProfessorItem = new JMenuItem("Remove Professor");

        professorsMenu.add(addProfessorItem);
        professorsMenu.add(removeProfessorItem);
        professorsMenu.add(viewProfessorsItem);

        // Students menu
        JMenu studentsMenu = new JMenu("Students");
        studentsMenu.setFont(new Font("sansserif", Font.PLAIN, 18));
        studentsMenu.setBackground(new Color(7, 164, 121));

        JMenuItem viewStudentsItem = new JMenuItem("View Students");
        JMenuItem enrollStudentItem = new JMenuItem("Enroll Student");
        JMenuItem generateReportItem = new JMenuItem("Generate Report");

        studentsMenu.add(viewStudentsItem);
        studentsMenu.add(enrollStudentItem);
        studentsMenu.add(generateReportItem);

        // Logout menu
        JButton logoutMenu = new JButton("Logout");
        // Make it look like a menu item
        logoutMenu.setBorderPainted(false);
        logoutMenu.setFocusPainted(false);
        logoutMenu.setContentAreaFilled(false);
        logoutMenu.setFont(new Font("sansserif", Font.PLAIN, 18));

        // Add menus to the menu bar
        menuBar.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 20, 20);
        menuBar.setPreferredSize(new Dimension(menuBar.getPreferredSize().width, 40));
        menuBar.setBackground(new Color(7, 164, 121));

        menuBar.add(coursesMenu);
        menuBar.add(professorsMenu);
        menuBar.add(studentsMenu);
        menuBar.add(logoutMenu);

        //add color to menu bar
        //menuBar.setBackground(new Color(7, 164, 121));
        logoutMenu.setForeground(new Color(7, 164, 121));
        menuBar.setOpaque(true);
        // Set the menu bar for the main frame
        app.setJMenuBar(menuBar);

        addClass.setActionCommand("Add Class");
        editClass.setActionCommand("Edit Class Capacity");
        viewCoursesItem.setActionCommand("View Courses");
        addCourseItem.setActionCommand("Add Course");
        changeCourseDetailsItem.setActionCommand("Change Course Details");
        viewProfessorsItem.setActionCommand("View Professors");
        addProfessorItem.setActionCommand("Add Professor");
        removeProfessorItem.setActionCommand("Remove Professor");
        viewStudentsItem.setActionCommand("View Students");
        enrollStudentItem.setActionCommand("Enroll Student");
        generateReportItem.setActionCommand("Generate Report");
        logoutMenu.setActionCommand("Logout");

        addClass.addActionListener(controller);
        editClass.addActionListener(controller);
        viewCoursesItem.addActionListener(controller);
        addCourseItem.addActionListener(controller);
        changeCourseDetailsItem.addActionListener(controller);
        viewProfessorsItem.addActionListener(controller);
        addProfessorItem.addActionListener(controller);
        removeProfessorItem.addActionListener(controller);
        viewStudentsItem.addActionListener(controller);
        enrollStudentItem.addActionListener(controller);
        generateReportItem.addActionListener(controller);
        logoutMenu.addActionListener(controller);
    }
}
