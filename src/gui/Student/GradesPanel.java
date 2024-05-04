package gui.Student;
import com.company.models.*;
import com.company.models.Class;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GradesPanel extends JPanel {
    private StudentHomePanel parentPanel;
    private University university;
    private Student student;

    public GradesPanel(StudentHomePanel parentPanel, University university) {
        this.parentPanel = parentPanel;
        this.university = university;
        this.student = parentPanel.getStudent();
        setLayout(new BorderLayout());
    }


    public JPanel getTranscriptPanel() {
        student = parentPanel.getStudent();
        AcademicTranscript academicTranscript = student.getAcademicTranscript();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Add components for viewing the academic transcript
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Course Name");
        tableModel.addColumn("Semester");
        tableModel.addColumn("Grade");

        JTable transcriptTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(transcriptTable);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Increase the font size of the header cells
        JTableHeader tableHeader = transcriptTable.getTableHeader();
        Font headerFont = tableHeader.getFont();
        tableHeader.setFont(new Font(headerFont.getName(), Font.BOLD, 16)); // Change 16 to the desired font size
        tableHeader.setReorderingAllowed(false); // Prevent column reordering

        // Fetch and display academic transcript data using streams
        try {
            academicTranscript.getStudentGrades().stream()
                    .filter(StudentGrade::isCompleted)
                    .forEach(studentGrade -> {
                        Class studentClass = studentGrade.getStudentClass();
                        Course course = studentClass.getCourse();
                        Semester semester = studentClass.getSemester();

                        // Add a new row to the table model with course, semester, and grade
                        tableModel.addRow(new Object[]{course.getName(), semester.getName(), studentGrade.getGrade()});
                    });
        } catch (Exception e) {
            //create dialog with error
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return panel;
    }

    public JPanel getHistoricalCourseSchedulePanel() {
        student = parentPanel.getStudent();
        AcademicTranscript academicTranscript = student.getAcademicTranscript();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Add components for viewing the historical course schedule
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Course Name");
        tableModel.addColumn("Semester");
        tableModel.addColumn("Grade");
        tableModel.addColumn("Day of the Week");
        tableModel.addColumn("Time");
        tableModel.addColumn("Location");

        JTable scheduleTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(scheduleTable);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Increase the font size of the header cells
        JTableHeader tableHeader = scheduleTable.getTableHeader();
        Font headerFont = tableHeader.getFont();
        tableHeader.setFont(new Font(headerFont.getName(), Font.BOLD, 16)); // Change 16 to the desired font size
        tableHeader.setReorderingAllowed(false); // Prevent column reordering

        // Fetch and display historical course schedule data using streams
        try {
            academicTranscript.getStudentGrades().stream()
                    .filter(StudentGrade::isCompleted)
                    .forEach(grades -> {
                        Class studentClass = grades.getStudentClass();
                        Course course = studentClass.getCourse();
                        Semester semester = studentClass.getSemester();

                        // Add a new row to the table model with course, semester, grade, day, time, and location
                        tableModel.addRow(new Object[]{
                                course.getName(),
                                semester.getName(),
                                grades.getGrade(),
                                studentClass.getSchedule().getClassTimings().getDayOfWeek(),
                                studentClass.getSchedule().getClassTimings().getTime(),
                                studentClass.getSchedule().getLocation()
                        });
                    });
        } catch (Exception e) {
            //create dialog with error
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return panel;
    }

    public JPanel getGPAPanel() {
        Student student = parentPanel.getStudent();
        AcademicTranscript academicTranscript = student.getAcademicTranscript();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Add components for viewing GPA
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Semester");
        tableModel.addColumn("GPA");

        JTable gpaTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(gpaTable);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Increase the font size of the header cells
        JTableHeader tableHeader = gpaTable.getTableHeader();
        Font headerFont = tableHeader.getFont();
        tableHeader.setFont(new Font(headerFont.getName(), Font.BOLD, 16)); // Change 16 to the desired font size
        tableHeader.setReorderingAllowed(false); // Prevent column reordering

        try {
            // Use streams to collect semester data
            Map<String, List<Double>> semesterData = academicTranscript.getStudentGrades().stream()
                    .filter(StudentGrade::isCompleted)
                    .collect(
                            Collectors.groupingBy(
                                    grades -> grades.getStudentClass().getSemester().getName(),
                                    Collectors.mapping(StudentGrade::getNumericalGrade, Collectors.toList())
                            )
                    );

            // Calculate GPA for each semester and add the data to the table
            semesterData.forEach((semesterName, gradePoints) -> {
                double gpa = gradePoints.stream().mapToDouble(Double::doubleValue).sum() / gradePoints.size();
                // Add a new row to the table model with semester and GPA
                tableModel.addRow(new Object[]{semesterName, gpa});
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return panel;
    }


}
