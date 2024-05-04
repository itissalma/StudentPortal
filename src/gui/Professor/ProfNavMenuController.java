package gui.Professor;

import gui.landing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ProfNavMenuController implements ActionListener {
    private ProfessorPanels professorPanels;
    private ProfessorHomePanel professorHomePanel;
    private landing app;

    public ProfNavMenuController(ProfessorPanels professorPanels, ProfessorHomePanel professorHomePanel, landing app) {
        this.professorPanels = professorPanels;
        this.professorHomePanel = professorHomePanel;
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Attendance":
                professorHomePanel.switchToPanel(professorPanels.takeAttendancePanel());
                break;
            case "Grades":
                List<String> gradeChoices = new ArrayList<>();
                gradeChoices.add("A");
                gradeChoices.add("A-");
                gradeChoices.add("B+");
                gradeChoices.add("B");
                gradeChoices.add("B-");
                gradeChoices.add("C+");
                gradeChoices.add("C");
                gradeChoices.add("C-");
                gradeChoices.add("D");
                professorHomePanel.switchToPanel(professorPanels.createGradeEntryPanel(gradeChoices));
                System.out.println("Grades");
                break;
            case "Logout":
                app.switchToLandingFrame();
                professorHomePanel.switchToHomePage();
                break;
        }
    }

}
