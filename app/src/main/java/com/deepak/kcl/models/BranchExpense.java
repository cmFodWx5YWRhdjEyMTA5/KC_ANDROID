package com.deepak.kcl.models;

public class BranchExpense {

    private String branch_expense_id;
    private int user_id;
    private String bch_date;
    private String branch_id;
    private String expense_id;
    private String lr_number;
    private String trip_expense_type;
    private String bch_amount;
    private String bch_desc;

    public BranchExpense(String branch_expense_id, int user_id, String bch_date, String branch_id, String expense_id, String lr_number, String trip_expense_type, String bch_amount, String bch_desc) {
        this.branch_expense_id = branch_expense_id;
        this.user_id = user_id;
        this.bch_date = bch_date;
        this.branch_id = branch_id;
        this.expense_id = expense_id;
        this.lr_number = lr_number;
        this.trip_expense_type = trip_expense_type;
        this.bch_amount = bch_amount;
        this.bch_desc = bch_desc;
    }

    public String getBranch_expense_id() {
        return branch_expense_id;
    }

    public void setBranch_expense_id(String branch_expense_id) {
        this.branch_expense_id = branch_expense_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getBch_date() {
        return bch_date;
    }

    public void setBch_date(String bch_date) {
        this.bch_date = bch_date;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getExpense_id() {
        return expense_id;
    }

    public void setExpense_id(String expense_id) {
        this.expense_id = expense_id;
    }

    public String getLr_number() {
        return lr_number;
    }

    public void setLr_number(String lr_number) {
        this.lr_number = lr_number;
    }

    public String getTrip_expense_type() {
        return trip_expense_type;
    }

    public void setTrip_expense_type(String trip_expense_type) {
        this.trip_expense_type = trip_expense_type;
    }

    public String getBch_amount() {
        return bch_amount;
    }

    public void setBch_amount(String bch_amount) {
        this.bch_amount = bch_amount;
    }

    public String getBch_desc() {
        return bch_desc;
    }

    public void setBch_desc(String bch_desc) {
        this.bch_desc = bch_desc;
    }
}
