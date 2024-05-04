package com.company;

import com.company.models.University;

import java.util.ArrayList;
import java.util.List;

public class UniversityManager {
    private static UniversityManager instance;
    private List<University> universities;

    private UniversityManager() {
        this.universities = new ArrayList<>();
    }

    public static UniversityManager getInstance() {
        if (instance == null) {
            instance = new UniversityManager();
        }
        return instance;
    }

    public List<University> getUniversities() {
        return universities;
    }

    public void addUniversity(University university) {
        universities.add(university);
    }

    public University findUniversityByName(String universityName) {
        for (University university : universities) {
            if (university.getName().equals(universityName)) {
                return university;
            }
        }
        return null;
    }
}

