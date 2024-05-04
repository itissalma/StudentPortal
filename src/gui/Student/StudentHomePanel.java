package gui.Student;

import com.company.models.Student;
import com.company.models.University;
import gui.landing;

import javax.swing.*;
import java.awt.*;

public class StudentHomePanel extends JPanel {
    private University university;
    private Student student;
    private landing app;
    private StudentNavMenu studentNavMenu;
    private JPanel currentPanel;
    JLabel welcomeLabel;

    public StudentHomePanel(landing app, StudentNavMenu studentNavMenu, Student student) {
        this.app = app;
        this.student = student;
        this.studentNavMenu = studentNavMenu;

        setLayout(new GridBagLayout());

        // Welcome message
        welcomeLabel = new JLabel("Welcome to UniTrack!");
        welcomeLabel.setFont(new Font("sansserif", Font.BOLD, 40));

        // Create GridBagConstraints to center the welcome message
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0); // Adjust vertical spacing
        gbc.anchor = GridBagConstraints.CENTER;
        add(welcomeLabel, gbc);

        app.revalidate();
        app.repaint();
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void switchToPanel(JPanel newPanel) {
        if (currentPanel != null) {
            remove(currentPanel);
        }
        //change layout of panel to box layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //remove welcome label
        removeAll();
        currentPanel = newPanel;
        add(currentPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void setMenuBar(StudentNavMenu navMenu) {
        app.setJMenuBar(navMenu);
        app.revalidate();
        app.repaint();
    }

    public void setJMenuBar(JMenuBar menuBar) {
        app.setJMenuBar(menuBar);
    }

    public void switchToHomePage() {
        if (currentPanel != null) {
            remove(currentPanel);
        }
        currentPanel = null; // Clear the current panel
        setLayout(new GridBagLayout());
        JLabel welcomeLabel = new JLabel("Welcome to UniTrack!");
        welcomeLabel.setFont(new Font("sansserif", Font.BOLD, 40));
        add(welcomeLabel, new GridBagConstraints());
        revalidate();
        repaint();
    }
}
