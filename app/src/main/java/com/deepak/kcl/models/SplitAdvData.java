package com.deepak.kcl.models;

public class SplitAdvData {

    private String split_advId;
    private String split_date;
    private String branch_name;
    private String split_type;
    private String amount;
    private String description;
    private String split_head;

    public SplitAdvData(String split_advId, String split_date, String branch_name, String split_type, String amount, String description, String split_head) {
        this.split_advId = split_advId;
        this.split_date = split_date;
        this.branch_name = branch_name;
        this.split_type = split_type;
        this.amount = amount;
        this.description = description;
        this.split_head = split_head;
    }

    public String getSplit_advId() {
        return split_advId;
    }

    public String getSplit_date() {
        return split_date;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public String getSplit_type() {
        return split_type;
    }

    public String getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getSplit_head() {
        return split_head;
    }
}
