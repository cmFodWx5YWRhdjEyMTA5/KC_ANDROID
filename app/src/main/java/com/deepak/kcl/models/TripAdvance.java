package com.deepak.kcl.models;

public class TripAdvance {

    private String date;
    private String amount;
    private String branch;
    private String desc;
    private String trip_exp_type;

    public TripAdvance(String date, String amount, String branch, String desc, String trip_exp_type) {
        this.date = date;
        this.amount = amount;
        this.branch = branch;
        this.desc = desc;
        this.trip_exp_type = trip_exp_type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTrip_exp_type() {
        return trip_exp_type;
    }

    public void setTrip_exp_type(String trip_exp_type) {
        this.trip_exp_type = trip_exp_type;
    }

    @Override
    public String toString() {
        return "TripAdvance{" +
                "date='" + date + '\'' +
                ", amount='" + amount + '\'' +
                ", branch='" + branch + '\'' +
                ", desc='" + desc + '\'' +
                ", trip_exp_type='" + trip_exp_type + '\'' +
                '}';
    }
}
