package com.company.models;

import java.util.ArrayList;

public class Semester {
    private String name;
    private ArrayList<Course> offeredCourses;

    public Semester(String name) {
        this.name = name;
        this.offeredCourses = new ArrayList<>();
    }
    public String getName() {
        return name;
    }


}
