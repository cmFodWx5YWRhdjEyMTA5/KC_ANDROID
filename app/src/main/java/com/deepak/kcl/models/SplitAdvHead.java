package com.deepak.kcl.models;

public class SplitAdvHead {

    private String trip_exp_type;
    private String amount;

    public SplitAdvHead(String trip_exp_type, String amount) {
        this.trip_exp_type = trip_exp_type;
        this.amount = amount;
    }

    public String getTrip_exp_type() {
        return trip_exp_type;
    }

    public void setTrip_exp_type(String trip_exp_type) {
        this.trip_exp_type = trip_exp_type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
