package gui.Student;

import com.company.models.Student;
import com.company.models.University;
import gui.landing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentNavMenu extends JMenuBar{
    public JMenuBar getStudentMenuBar() {
        return this; // Return the current instance, which is a JMenuBar
    }
    private Student student;
    private landing app;
    private StudentHomePanel studentHomePanel;
    private CoursesPanel coursesPanel;
    private GradesPanel gradesPanel;
    private University university;
    private StudentNavMenuController controller;

    public StudentNavMenu(landing app, StudentHomePanel studentHomePanel, CoursesPanel coursesPanel, GradesPanel gradesPanel, University university, StudentNavMenuController controller) {
        this.studentHomePanel=studentHomePanel;
        this.app = app;
        this.coursesPanel = coursesPanel;
        this.gradesPanel = gradesPanel;
        this.university = university;
        this.controller = controller;

        JMenuBar menuBar = new JMenuBar();

        // Courses menu
        JMenu coursesMenu = new JMenu("Courses");
        coursesMenu.setFont(new Font("sansserif", Font.PLAIN, 18));
        coursesMenu.setBackground(new Color(7, 164, 121));
        JMenuItem viewCoursesItem = new JMenuItem("View Courses");
        JMenuItem viewCourseByDepartmentItem = new JMenuItem("View Course by Department");
        JMenuItem viewCourseByProfessorItem = new JMenuItem("View Course by Professor");
        JMenuItem getCourseDetailsItem = new JMenuItem("Get Course Details");
        JMenuItem getCourseScheduleItem = new JMenuItem("Get Course Schedule");
        JMenuItem favoriteCoursesItem = new JMenuItem("Favorite Courses");
        JMenuItem removeCourseFromFavoritesItem = new JMenuItem("Remove Course from Favorites");
        JMenuItem viewFavoriteCoursesItem = new JMenuItem("View Favorite Courses");
        JMenuItem enrollInCourseItem = new JMenuItem("Enroll in a Course");
        coursesMenu.add(viewCoursesItem);
        coursesMenu.add(viewCourseByDepartmentItem);
        coursesMenu.add(viewCourseByProfessorItem);
        coursesMenu.add(getCourseDetailsItem);
        coursesMenu.add(getCourseScheduleItem);
        coursesMenu.add(favoriteCoursesItem);
        coursesMenu.add(removeCourseFromFavoritesItem);
        coursesMenu.add(viewFavoriteCoursesItem);
        coursesMenu.add(enrollInCourseItem);

        // Reports menu
        JMenu reportsMenu = new JMenu("Reports");
        reportsMenu.setFont(new Font("sansserif", Font.PLAIN, 18));
        reportsMenu.setBackground(new Color(7, 164, 121));
        JMenuItem viewAcademicTranscriptItem = new JMenuItem("View Academic Transcript");
        JMenuItem viewHistoricalCourseScheduleItem = new JMenuItem("View Historical Course Schedule");
        JMenuItem viewPastSemesterPerformanceItem = new JMenuItem("View Past Semester Performance");
        reportsMenu.add(viewAcademicTranscriptItem);
        reportsMenu.add(viewHistoricalCourseScheduleItem);
        reportsMenu.add(viewPastSemesterPerformanceItem);

        // Logout menu
        JButton logoutMenu = new JButton("Logout");
        //make it look like menu item
        logoutMenu.setBorderPainted(false);
        logoutMenu.setFocusPainted(false);
        logoutMenu.setContentAreaFilled(false);
        logoutMenu.setFont(new Font("sansserif", Font.PLAIN, 18));

        menuBar.setLayout(new GridBagLayout()); // Use GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; // Allow horizontal expansion
        gbc.insets = new Insets(20, 20, 20, 20); // Add some padding
        menuBar.setPreferredSize(new Dimension(menuBar.getPreferredSize().width, 40)); // Set the preferred height to 80 pixels
        //set color to green
        menuBar.setBackground(new Color(7, 164, 121));
        //menuBar.setLayout(new FlowLayout(FlowLayout.LEFT)); // Use FlowLayout with left alignment

        Component horizontalStrut = Box.createHorizontalStrut(70);
        // Add menus to the menu bar
        menuBar.add(coursesMenu);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(reportsMenu);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(logoutMenu);

        logoutMenu.setForeground(new Color(7, 164, 121));

        //app.setJMenuBar(menuBar);
        studentHomePanel.setJMenuBar(menuBar);

        viewCoursesItem.setActionCommand("View Courses");
        viewCourseByDepartmentItem.setActionCommand("View Course by Department");
        viewCourseByProfessorItem.setActionCommand("View Course by Professor");
        getCourseDetailsItem.setActionCommand("Get Course Details");
        getCourseScheduleItem.setActionCommand("Get Course Schedule");
        favoriteCoursesItem.setActionCommand("Favorite Courses");
        removeCourseFromFavoritesItem.setActionCommand("Remove Course from Favorites");
        viewFavoriteCoursesItem.setActionCommand("View Favorite Courses");
        enrollInCourseItem.setActionCommand("Enroll in a Course");
        viewAcademicTranscriptItem.setActionCommand("View Academic Transcript");
        viewHistoricalCourseScheduleItem.setActionCommand("View Historical Course Schedule");
        viewPastSemesterPerformanceItem.setActionCommand("View Past Semester Performance");
        logoutMenu.setActionCommand("Logout");

        viewCoursesItem.addActionListener(controller);
        viewCourseByDepartmentItem.addActionListener(controller);
        viewCourseByProfessorItem.addActionListener(controller);
        getCourseDetailsItem.addActionListener(controller);
        getCourseScheduleItem.addActionListener(controller);
        favoriteCoursesItem.addActionListener(controller);
        removeCourseFromFavoritesItem.addActionListener(controller);
        viewFavoriteCoursesItem.addActionListener(controller);
        enrollInCourseItem.addActionListener(controller);
        viewAcademicTranscriptItem.addActionListener(controller);
        viewHistoricalCourseScheduleItem.addActionListener(controller);
        viewPastSemesterPerformanceItem.addActionListener(controller);
        logoutMenu.addActionListener(controller);

    }
}

