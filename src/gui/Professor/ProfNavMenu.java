package gui.Professor;

import com.company.models.University;
import gui.Professor.ProfessorHomePanel;
import gui.Professor.ProfessorPanels;
import gui.Student.CoursesPanel;
import gui.landing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ProfNavMenu extends JMenuBar {
    private landing app;
    private University university;
    private ProfessorHomePanel professorHomePanel;
    private ProfessorPanels professorPanels;
    private ProfNavMenuController controller;

    public ProfNavMenu(landing app, University university, ProfessorHomePanel professorHomePanel, ProfessorPanels professorPanels, ProfNavMenuController controller) {
        this.app = app;
        this.university = university;
        this.professorHomePanel = professorHomePanel;

        JMenuBar menuBar = new JMenuBar();

        JButton attendanceMenu = new JButton("Take Attendance");
        attendanceMenu.setBorderPainted(false);
        attendanceMenu.setFocusPainted(false);
        attendanceMenu.setContentAreaFilled(false);
        attendanceMenu.setFont(new Font("sansserif", Font.PLAIN, 18));


        JButton inputGradesMenu = new JButton("Input Grades");
        inputGradesMenu.setBorderPainted(false);
        inputGradesMenu.setFocusPainted(false);
        inputGradesMenu.setContentAreaFilled(false);
        inputGradesMenu.setFont(new Font("sansserif", Font.PLAIN, 18));

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

        menuBar.add(attendanceMenu);
        menuBar.add(inputGradesMenu);
        menuBar.add(logoutMenu);

        //add color to menu bar
        //menuBar.setBackground(new Color(7, 164, 121));
        logoutMenu.setForeground(new Color(7, 164, 121));
        menuBar.setOpaque(true);
        // Set the menu bar for the main frame
        app.setJMenuBar(menuBar);

        attendanceMenu.setActionCommand("Attendance");
        inputGradesMenu.setActionCommand("Grades");
        logoutMenu.setActionCommand("Logout");
        System.out.println("controller is " + controller);
        attendanceMenu.addActionListener(controller);
        inputGradesMenu.addActionListener(controller);
        logoutMenu.addActionListener(controller);

        System.out.println("ProfNavMenu initialized");
    }
}
