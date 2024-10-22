package com.example.kan;

public class Participant {
    private String studentID; // Unique ID of the student
    private String name; // Name of the student

    // Default constructor
    public Participant() {
    }

    // Constructor
    public Participant(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
    }

    // Getters and Setters
    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Override toString() method for better logging
    @Override
    public String toString() {
        return "Participant{" +
                "studentID='" + studentID + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
