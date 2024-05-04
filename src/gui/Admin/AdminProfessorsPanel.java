package gui.Admin;
import com.company.models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class AdminProfessorsPanel extends JPanel {
    private AdminHomePanel parentPanel;
    private University university;
    private AdminProfessorsPanelController controller;
    Administrator admin;
    private JTextField professorNameField;
    private JTextField departmentField;

    public AdminProfessorsPanel(AdminHomePanel parentPanel, University university, Administrator admin, AdminProfessorsPanelController controller) {
        this.parentPanel = parentPanel;
        this.university = university;
        this.admin = admin;
        this.controller = controller;
        setLayout(new BorderLayout());
    }

    public JTextField getProfessorNameField() {
        return professorNameField;
    }

    public JTextField getDepartmentField() {
        return departmentField;
    }

    public void clearAddProfessorFields() {
        professorNameField.setText("");
        departmentField.setText("");
    }

    public void displayMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    public void clearRemoveProfessorFields(){
        professorNameField.setText(""); // Clear the input field
    }

    public JPanel createAddProfessorPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Reduce the margin

        // Professor Name
        JLabel professorNameLabel = new JLabel("Professor Name:");
        professorNameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(professorNameLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        professorNameField = new JTextField(20); // Reduce the width
        professorNameField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        panel.add(professorNameField, gbc);

        // Department
        JLabel departmentLabel = new JLabel("Department:");
        departmentLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(departmentLabel, gbc);

        gbc.gridx = 1;
        departmentField = new JTextField(20); // Reduce the width
        departmentField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        panel.add(departmentField, gbc);

        // Center and make the button the same width and increase button height
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2; // Span 2 columns
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton addProfessorButton = new JButton("Add Professor");
        addProfessorButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        addProfessorButton.setPreferredSize(new Dimension(200, 40)); // Increase the button height
        panel.add(addProfessorButton, gbc);

        addProfessorButton.setActionCommand("Add Professor Button");
        addProfessorButton.addActionListener(controller);

        return panel;
    }


    public JPanel createRemoveProfessorPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Reduce the margin

        // Professor Name
        JLabel professorNameLabel = new JLabel("Professor ID:");
        professorNameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(professorNameLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        professorNameField = new JTextField(20); // Reduce the width
        professorNameField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        panel.add(professorNameField, gbc);

        // Center and make the button the same width and increase button height
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2; // Span 2 columns
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton removeProfessorButton = new JButton("Remove Professor");
        removeProfessorButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        removeProfessorButton.setPreferredSize(new Dimension(200, 40)); // Increase the button height
        panel.add(removeProfessorButton, gbc);

        removeProfessorButton.setActionCommand("Remove Professor Button");
        removeProfessorButton.addActionListener(controller);

        return panel;
    }

    public JPanel getProfessorsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Professor Name");
        tableModel.addColumn("Department");

        JTable professorTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(professorTable);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Increase the font size of the header cells
        JTableHeader tableHeader = professorTable.getTableHeader();
        Font headerFont = tableHeader.getFont();
        tableHeader.setFont(new Font(headerFont.getName(), Font.BOLD, 16)); // Change 16 to the desired font size

        // Implement logic to display available professors
        List<Professor> professors = university.getProfessors();

        // Update the table with professor data
        for (Professor professor : professors) {
            tableModel.addRow(new Object[]{professor.getName(), professor.getDepartment()});
        }

        return panel;
    }


}
