package com.deepak.kcl.models;

public class Branch {

    private String branch_name;

    public Branch(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String toString() {
        return branch_name;
    }
}
