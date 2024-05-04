package gui;

import com.company.DataInitializer;
import com.company.UniversityDataManager;
import com.company.models.University;

import javax.swing.*;

public class ApplicationController {
    private landing landingApp;
    private University university = new University("The American University in Cairo", "New Cairo");

    public ApplicationController() {
        initializeApplication();
    }

    private void initializeApplication() {
        landingApp = new landing(this);

        // Initialize data
        UniversityDataManager universityDataManager = new UniversityDataManager();
        universityDataManager.readUniversityData(university);

        System.out.println("University data initialized successfully.");
        landingApp.setUniversity(university);
        landingApp.getFormPanel().setUniversity(university);
        System.out.println("University data set successfully.");

        // Add your listener initialization here
        landingApp.getFormPanel().addStudentSetListener(student -> {
            landingApp.setStudent(student);
            landingApp.getStudentHomePanel().setStudent(student);
        });

        landingApp.getFormPanel().addProfessorSetListener(professor -> {
            landingApp.setProfessor(professor);
            landingApp.getProfessorHomePanel().setProfessor(professor);
        });

        landingApp.getFormPanel().addAdminSetListener(administrator -> {
            landingApp.setAdmin(administrator);
            landingApp.getAdminHomePanel().setAdmin(administrator);
        });

        // Set up the user interface
        landingApp.setVisible(true);
    }

    public University getUniversity() {
        return university;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ApplicationController::new);
    }
}
