package com.deepak.kcl.models;

public class TripList {
    private String startDate;
    private String endDate;
    private String clientName;
    private String routeNumber;
    private String vehicleNumber;
    private String lrNumber;
    private String tripStatus;

    public TripList(String startDate, String endDate, String clientName, String routeNumber, String vehicleNumber, String lrNumber, String tripStatus) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.clientName = clientName;
        this.routeNumber = routeNumber;
        this.vehicleNumber = vehicleNumber;
        this.lrNumber = lrNumber;
        this.tripStatus = tripStatus;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getLrNumber() {
        return lrNumber;
    }

    public void setLrNumber(String lrNumber) {
        this.lrNumber = lrNumber;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }
}

