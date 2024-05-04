package gui;
import com.company.models.*;
import gui.Admin.*;
import gui.Professor.*;
import gui.Student.*;

import javax.swing.*;
import java.awt.*;

public class landing extends JFrame{
    private CoursesPanel coursesPanel;
    private GradesPanel gradesPanel;
    private ProfessorPanels professorPanels;
    private AdminCoursesPanel adminCoursesPanel;
    private AdminProfessorsPanel adminProfessorsPanel;
    private AdminStudentsPanel adminStudentsPanel;
    private StudentHomePanel studentHomePanel;
    private ProfessorHomePanel professorHomePanel;
    private AdminHomePanel adminHomePanel;
    private FormPanel formPanel;
    private JPanel cardPanel;
    private StudentNavMenu studentNavMenu;
    private ProfNavMenu profNavMenu;
    private AdminNavMenu adminNavMenu;
    private UserType userType;
    private University university;
    private Student student;
    private Professor professor;
    private Administrator admin;
    private AdminNavMenuController adminNavMenuController;
    private ProfNavMenuController profNavMenuController;
    private StudentNavMenuController studentNavMenuController;
    private ProfessorPanelsController professorPanelsController;
    private AdminCoursesPanelController adminCoursesPanelController;
    private AdminProfessorsPanelController adminProfessorsPanelController;
    private AdminStudentsPanelController adminStudentsPanelController;
    private CoursesPanelController coursesPanelController;
    private FormPanelController formPanelController;

    private ApplicationController controller;

    public landing(ApplicationController controller) {
        this.controller = controller;
        this.university = controller.getUniversity();
        if(controller.getUniversity() == null) {
            System.out.println("University is null");
        }

        setTitle("University Platform");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        formPanelController = new FormPanelController(university, this);
        formPanel = new FormPanel(this, university, formPanelController);
        formPanelController.setView(formPanel);

        userType = formPanel.getUserType();
        professorHomePanel = new ProfessorHomePanel(landing.this, profNavMenu, professor);
        adminHomePanel = new AdminHomePanel(landing.this, adminNavMenu, admin, coursesPanel);
        studentHomePanel = new StudentHomePanel(landing.this, studentNavMenu, student);

        professorPanelsController = new ProfessorPanelsController( university, professor);
        adminCoursesPanelController = new AdminCoursesPanelController(admin, university);
        adminProfessorsPanelController = new AdminProfessorsPanelController(admin, university);
        adminStudentsPanelController = new AdminStudentsPanelController(admin, university);
        coursesPanelController = new CoursesPanelController(university, student);

        coursesPanel = new CoursesPanel(studentHomePanel, university, coursesPanelController);
        coursesPanelController.setView(coursesPanel);
        gradesPanel = new GradesPanel(studentHomePanel, university);
        professorPanels = new ProfessorPanels (professorHomePanel, university, professor, professorPanelsController);
        professorPanelsController.setView(professorPanels);
        adminCoursesPanel = new AdminCoursesPanel(adminHomePanel, university, admin, adminCoursesPanelController);
        adminCoursesPanelController.setView(adminCoursesPanel);
        adminProfessorsPanel = new AdminProfessorsPanel(adminHomePanel, university, admin, adminProfessorsPanelController);
        adminProfessorsPanelController.setView(adminProfessorsPanel);
        adminStudentsPanel = new AdminStudentsPanel(adminHomePanel, university, admin, adminStudentsPanelController);
        adminStudentsPanelController.setView(adminStudentsPanel);

        profNavMenu = new ProfNavMenu(landing.this, university, professorHomePanel, professorPanels, profNavMenuController);
        studentNavMenu = new StudentNavMenu(landing.this, studentHomePanel, coursesPanel, gradesPanel, university, studentNavMenuController);
        adminNavMenu = new AdminNavMenu(landing.this, adminHomePanel, university, adminCoursesPanel, adminProfessorsPanel, adminStudentsPanel, adminNavMenuController);

        adminNavMenuController = new AdminNavMenuController(adminHomePanel, adminCoursesPanel, adminProfessorsPanel, adminStudentsPanel, landing.this);
        profNavMenuController = new ProfNavMenuController(professorPanels, professorHomePanel, landing.this);
        studentNavMenuController = new StudentNavMenuController(studentHomePanel,  coursesPanel, gradesPanel, landing.this, university,  student);

        System.out.println("professorPanelsController: " + professorPanelsController);

        coursesPanel.setStudent(student);

        // Set up the CardLayout for panel switching
        cardPanel = new JPanel(new CardLayout());
        cardPanel.add(formPanel, "LoginForm");

        setJMenuBar(null);
        setContentPane(cardPanel);
        setVisible(true);
    }

    public void switchToPanel(JPanel panel) {
        SwingUtilities.invokeLater(() -> {
            panel.add(studentNavMenu);
            setContentPane(panel);
            revalidate();
            repaint();
        });
    }

    public FormPanelController getFormPanel() {
        return formPanelController;
    }

    public void switchToLandingFrame(){
        SwingUtilities.invokeLater(() -> {
            setJMenuBar(null);
            setContentPane(cardPanel);
            revalidate();
            repaint();
        });
    }

    public void updateUserTypeAndMenu(UserType userType) {
        this.userType = userType;
        if (userType == UserType.STUDENT) {
            this.studentNavMenu = new StudentNavMenu(this, studentHomePanel, coursesPanel, gradesPanel, university, studentNavMenuController);
        } else if (userType == UserType.PROFESSOR) {
            this.profNavMenu = new ProfNavMenu(landing.this, university, professorHomePanel, professorPanels, profNavMenuController);

            // Handle professor menu
        } else if (userType == UserType.ADMIN) {
            this.adminNavMenu = new AdminNavMenu(this, adminHomePanel, university, adminCoursesPanel, adminProfessorsPanel, adminStudentsPanel, adminNavMenuController);
            // Handle admin menu
        } else {
            // No menu bar
            this.studentNavMenu = new StudentNavMenu(this, studentHomePanel, coursesPanel, gradesPanel, university, studentNavMenuController);
            setJMenuBar(studentNavMenu);
        }
        revalidate();
        repaint();
    }

    public Professor getProfessor() {
        return professor;
    }

    public Student getStudent() {
        return student;
    }

    public StudentHomePanel getStudentHomePanel() {
        return studentHomePanel;
    }

    public ProfessorHomePanel getProfessorHomePanel() {
        return professorHomePanel;
    }

    public AdminHomePanel getAdminHomePanel() {
        return adminHomePanel;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
        professorPanelsController.setProfessor(professor);
    }

    public void setAdmin(Administrator admin) {
        this.admin = admin;
        adminCoursesPanelController.setAdmin(admin);
        adminProfessorsPanelController.setAdmin(admin);
        adminStudentsPanelController.setAdmin(admin);
    }

    public void setStudent(Student student) {
        this.student = student;
        coursesPanelController.setStudent(student);
    }

    public void setUniversity(University university) {
        this.university = university;
    }
}
