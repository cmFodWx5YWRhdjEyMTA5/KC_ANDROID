package com.deepak.kcl.models;

public class SplitAdvChild {

    private String date;
    private String type;
    private String amount;
    private String branch;
    private String desc;

    public SplitAdvChild(String date, String type, String amount, String branch, String desc) {
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.branch = branch;
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
