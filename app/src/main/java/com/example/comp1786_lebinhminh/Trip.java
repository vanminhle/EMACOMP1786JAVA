package com.example.comp1786_lebinhminh;

public class Trip {
    String id;
    String tripName;
    String tripDestination;
    String vehicle;
    String requireAssessment;
    String dateOfTrip;
    String description;
    String status;

    public Trip(String id, String tripName, String tripDestination, String vehicle, String requireAssessment, String dateOfTrip, String description, String status) {
        this.id = id;
        this.tripName = tripName;
        this.tripDestination = tripDestination;
        this.vehicle = vehicle;
        this.requireAssessment = requireAssessment;
        this.dateOfTrip = dateOfTrip;
        this.description = description;
        this.status = status;
    }

    public String getTripId() {
        return id;
    }

    public String getTripName() {
        return tripName;
    }

    public String getTripDestination() {
        return tripDestination;
    }

    public String getVehicle() {
        return vehicle;
    }

    public String getRequireAssessment() {
        return requireAssessment;
    }

    public String getDateOfTrip() {
        return dateOfTrip;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }
}
