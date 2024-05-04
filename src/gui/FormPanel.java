package gui;

import com.company.models.*;
import gui.Student.StudentNavMenu;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class FormPanel extends JPanel {
    private JTextField idField;
    private JPasswordField passwordField;
    private UserType userType;
    private StudentNavMenu studentNavMenu;
    private University university;
    private Student student; // Add a field to store the Student object set in FormPanel
    private Professor professor;
    private Administrator admin;
    private landing parent;
    private FormPanelController controller;

    public FormPanel(landing parentFrame, University university, FormPanelController controller) {
        this.university = university;
        this.parent = parentFrame;
        this.controller = controller;


        setLayout(new GridLayout(0, 1, 10, 10));

        JLabel label = new JLabel("Login");
        label.setFont(new Font("sansserif", Font.BOLD, 30));
        label.setForeground(new Color(7, 164, 121));
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label);

        JLabel idLabel = new JLabel("University ID:");
        idLabel.setForeground(new Color(7, 164, 121));
        idLabel.setFont(new Font("sansserif", Font.BOLD, 15));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(new Color(7, 164, 121));
        passwordLabel.setFont(new Font("sansserif", Font.BOLD, 15));

        idField = new JTextField();
        passwordField = new JPasswordField();

        add(idLabel);
        add(idField);
        add(passwordLabel);
        add(passwordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        CustomButton submitButton = new CustomButton("Submit");
        // Make the button the same green color
        submitButton.setBackground(new Color(7, 164, 121));
        buttonPanel.add(submitButton);

        add(buttonPanel);

        submitButton.setActionCommand("Submit Button");
        submitButton.addActionListener(this.controller);
    }

    public JTextField getIdField() {
        return idField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
    }

}

class CustomButton extends JButton {
    public CustomButton(String text) {
        super(text);
        setForeground(new Color(7, 164, 121));
        setFont(new Font("sansserif", Font.BOLD, 20));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(0, 123, 91));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(new Color(7, 164, 121));
            }
        });
    }
}
