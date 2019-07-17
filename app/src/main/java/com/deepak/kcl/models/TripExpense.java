package com.deepak.kcl.models;

public class TripExpense {

    private int user_id;
    private int tripexp_id;
    private String tripexp_type;
    private String tripexp_amount;
    private String tripexp_img;
    private String totalExpense;

    public TripExpense(int user_id, int tripexp_id, String tripexp_type, String tripexp_amount, String tripexp_img, String totalExpense) {
        this.user_id = user_id;
        this.tripexp_id = tripexp_id;
        this.tripexp_type = tripexp_type;
        this.tripexp_amount = tripexp_amount;
        this.tripexp_img = tripexp_img;
        this.totalExpense = totalExpense;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getTripexp_id() {
        return tripexp_id;
    }

    public String getTripexp_type() {
        return tripexp_type;
    }

    public String getTripexp_amount() {
        return tripexp_amount;
    }

    public String getTripexp_img() {
        return tripexp_img;
    }

    public String getTotalExpense() {
        return totalExpense;
    }
}
