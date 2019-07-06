package com.deepak.kcl.models;

public class BranchTrips {

    private int trip_assign_id;
    private int trip_id;
    private String LR;
    private String vehicle_no;
    private String RouteName;
    private String client_name;
    private String s_time;
    private String e_time;
    private String s_date;
    private String e_date;
    private String budget;
    private String quantity;
    private String KM;

    public BranchTrips(int trip_assign_id, int trip_id, String LR, String vehicle_no, String routeName, String client_name, String s_time, String e_time, String s_date, String e_date, String budget, String quantity, String KM) {
        this.trip_assign_id = trip_assign_id;
        this.trip_id = trip_id;
        this.LR = LR;
        this.vehicle_no = vehicle_no;
        RouteName = routeName;
        this.client_name = client_name;
        this.s_time = s_time;
        this.e_time = e_time;
        this.s_date = s_date;
        this.e_date = e_date;
        this.budget = budget;
        this.quantity = quantity;
        this.KM = KM;
    }

    public int getTrip_assign_id() {
        return trip_assign_id;
    }

    public int getTrip_id() {
        return trip_id;
    }

    public String getLR() {
        return LR;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public String getRouteName() {
        return RouteName;
    }

    public String getClient_name() {
        return client_name;
    }

    public String getS_time() {
        return s_time;
    }

    public String getE_time() {
        return e_time;
    }

    public String getS_date() {
        return s_date;
    }

    public String getE_date() {
        return e_date;
    }

    public String getBudget() {
        return budget;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getKM() {
        return KM;
    }
}
