package gui.Student;

import com.company.models.Student;
import com.company.models.University;
import gui.landing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentNavMenuController implements ActionListener {
    private StudentNavMenu view;
    private StudentHomePanel studentHomePanel;
    private CoursesPanel coursesPanel;
    private GradesPanel gradesPanel;
    private landing app;
    private University university;
    private Student student;


    public StudentNavMenuController(StudentHomePanel studentHomePanel, CoursesPanel coursesPanel, GradesPanel gradesPanel, landing app,
                                    University university, Student student){
        this.studentHomePanel = studentHomePanel;
        this.university = university;
        this.student = student;
        this.app = app;
        this.coursesPanel = coursesPanel;
        this.gradesPanel = gradesPanel;
    }

    public void setView(StudentNavMenu view){
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command){
            case "View Courses":
                studentHomePanel.switchToPanel(coursesPanel.viewCoursesPanel());
                break;
            case "View Course by Department":
                studentHomePanel.switchToPanel(coursesPanel.viewCoursesByDepartmentPanel());
                break;
            case "View Course by Professor":
                studentHomePanel.switchToPanel(coursesPanel.viewCoursesByProfessorPanel());
                break;
            case "Get Course Details":
                studentHomePanel.switchToPanel(coursesPanel.viewCourseDetailsPanel());
                break;
            case "Get Course Schedule":
                studentHomePanel.switchToPanel(coursesPanel.getCourseSchedulePanel());
                break;
            case "Favorite Courses":
                studentHomePanel.switchToPanel(coursesPanel.addToFavoritesPanel());
                break;
            case "Remove Course from Favorites":
                studentHomePanel.switchToPanel(coursesPanel.removeCourseFromFavorites());
                break;
            case "View Favorite Courses":
                studentHomePanel.switchToPanel(coursesPanel.viewFavoriteCoursesPanel());
                break;
            case "Enroll in a Course":
                studentHomePanel.switchToPanel(coursesPanel.enrollInCoursePanel());
                break;
            case "View Academic Transcript":
                studentHomePanel.switchToPanel(gradesPanel.getTranscriptPanel());
                break;
            case "View Historical Course Schedule":
                studentHomePanel.switchToPanel(gradesPanel.getHistoricalCourseSchedulePanel());
                break;
            case "View Past Semester Performance":
                studentHomePanel.switchToPanel(gradesPanel.getGPAPanel());
                break;
            case "Logout":
                app.switchToLandingFrame();
                studentHomePanel.switchToHomePage();
                break;
        }
    }
}
