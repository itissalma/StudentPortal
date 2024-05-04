package gui.Admin;

import gui.landing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminNavMenuController implements ActionListener {
    private AdminHomePanel adminHomePanel;
    private AdminCoursesPanel view1;
    private AdminProfessorsPanel view2;
    private AdminStudentsPanel view3;
    private landing app;

    public AdminNavMenuController(AdminHomePanel adminHomePanel, AdminCoursesPanel view1, AdminProfessorsPanel view2, AdminStudentsPanel view3, landing app) {
        this.adminHomePanel = adminHomePanel;
        this.view1 = view1;
        this.view2 = view2;
        this.view3 = view3;
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "View Courses":
                //system out that yu are here
                System.out.println("View Courses");
                adminHomePanel.switchToPanel(view1.viewCoursesPanel());
                break;
            case "Add Course":
                adminHomePanel.switchToPanel(view1.addCoursePanel());
                break;
            case "Change Course Details":
                adminHomePanel.switchToPanel(view1.updateCourseDetailsPanel());
                break;
            case "Add Class":
                adminHomePanel.switchToPanel(view1.createAddClassPanel());
                break;
            case "Edit Class Capacity":
                adminHomePanel.switchToPanel(view1.editCapacityPanel());
                break;
            case "View Professors":
                adminHomePanel.switchToPanel(view2.getProfessorsPanel());
                break;
            case "Add Professor":
                adminHomePanel.switchToPanel(view2.createAddProfessorPanel());
                break;
            case "Remove Professor":
                adminHomePanel.switchToPanel(view2.createRemoveProfessorPanel());
                break;
            case "View Students":
                adminHomePanel.switchToPanel(view3.getStudentsPanel());
                break;
            case "Enroll Student":
                adminHomePanel.switchToPanel(view3.enrollStudentPanel());
                break;
            case "Generate Report":
                adminHomePanel.switchToPanel(view3.generateReportPanel());
                break;
            case "Logout":
                app.switchToLandingFrame();
                adminHomePanel.switchToHomePage();
                break;
        }
    }

}
