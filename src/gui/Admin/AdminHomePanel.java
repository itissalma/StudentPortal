package gui.Admin;

import com.company.models.Administrator;
import com.company.models.University;
import gui.Student.CoursesPanel;
import gui.landing;

import javax.swing.*;
import java.awt.*;

public class AdminHomePanel extends JPanel {
    private University university;
    private Administrator admin;
    private landing app;
    private AdminNavMenu adminNavMenu;
    private JPanel currentPanel;
    private CoursesPanel coursesPanel;
    JLabel welcomeLabel;

    public AdminHomePanel(landing app, AdminNavMenu adminNavMenu, Administrator admin, CoursesPanel coursesPanel) {
        this.app = app;
        this.admin = admin;
        this.adminNavMenu = adminNavMenu;
        this.coursesPanel = coursesPanel;

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

    public void setJMenuBar(JMenuBar menuBar) {
        app.setJMenuBar(menuBar);
    }

    public Administrator getAdmin() {
        return admin;
    }

    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }
}
