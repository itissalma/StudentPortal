package com.company;

import com.company.models.University;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class UniversityDataManager {
    public static University readUniversityData(University university) {
        String[] filePaths = {
                "src/com/company/data/administrator_data.csv",
                "src/com/company/data/student_data.csv",
                "src/com/company/data/professor_data.csv",
                "src/com/company/data/semesters_data.csv",
                "src/com/company/data/classTimings_data.csv",
                "src/com/company/data/schedule_data.csv",
                "src/com/company/data/classTimings_data.csv",
                "src/com/company/data/courses_data.csv",
                "src/com/company/data/classes_data.csv",
                "src/com/company/data/studentEnrollment_data.csv",
                "src/com/company/data/grades_data.csv",
                "src/com/company/data/takeAttendance_data.csv",
                "src/com/company/data/favoriteCourses_data.csv",
        };

        for (String filePath : filePaths) {
            try {
                // Use the DataReader class to read data from files
                DataReader.readData(university, filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return university;
    }
}

