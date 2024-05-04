package com.company.models;

public class Schedule {
    private ClassTiming classTimings;
    private String location;

    public Schedule(ClassTiming classTimings, String location) {
        this.classTimings = classTimings;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public ClassTiming getClassTimings() {
        return classTimings;
    }
}
