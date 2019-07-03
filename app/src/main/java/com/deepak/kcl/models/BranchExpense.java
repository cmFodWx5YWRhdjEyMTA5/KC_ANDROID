package com.deepak.kcl.models;

public class BranchExpense {

    private String branch_expense_id;
    private int user_id;
    private String bch_date;
    private String branch_name;
    private String expense_type;
    private String lr_number;
    private String trip_expense_type;
    private String bch_amount;
    private String bch_desc;

    public BranchExpense(String branch_expense_id, int user_id, String bch_date, String branch_name, String expense_type, String lr_number, String trip_expense_type, String bch_amount, String bch_desc) {
        this.branch_expense_id = branch_expense_id;
        this.user_id = user_id;
        this.bch_date = bch_date;
        this.branch_name = branch_name;
        this.expense_type = expense_type;
        this.lr_number = lr_number;
        this.trip_expense_type = trip_expense_type;
        this.bch_amount = bch_amount;
        this.bch_desc = bch_desc;
    }

    public String getBranch_expense_id() {
        return branch_expense_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getBch_date() {
        return bch_date;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public String getExpense_type() {
        return expense_type;
    }

    public String getLr_number() {
        return lr_number;
    }

    public String getTrip_expense_type() {
        return trip_expense_type;
    }

    public String getBch_amount() {
        return bch_amount;
    }

    public String getBch_desc() {
        return bch_desc;
    }
}
