package com.example.kan;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private String eventID; // Unique identifier for the event
    private String eventName; // Name of the event
    private String date; // Date of the event
    private String description; // Description of the event
    private String location; // Location of the event
    private List<Participant> waitingList; // List to hold waiting list participants
    private int name;

    // Default constructor
    public Event() {
        this.waitingList = new ArrayList<>(); // Initialize waiting list
    }

    // Constructor with all fields
    public Event(String eventID, String eventName, String date, String description, String location) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.date = date;
        this.description = description;
        this.location = location;
        this.waitingList = new ArrayList<>(); // Initialize waiting list
    }

    // Getters and Setters
    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Participant> getWaitingList() {
        return waitingList;
    }

    public void setWaitingList(List<Participant> waitingList) {
        this.waitingList = waitingList;
    }

    // Method to add a participant to the waiting list
    public void addParticipantToWaitingList(Participant participant) {
        waitingList.add(participant);
    }

    // Override toString() method for better logging
    @Override
    public String toString() {
        return "Event{" +
                "eventID='" + eventID + '\'' +
                ", eventName='" + eventName + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", waitingList=" + waitingList +
                '}';
    }

    public int getName() {
        return name;
    }

    // Participant class to represent a participant in the waiting list
}