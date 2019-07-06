package com.deepak.kcl.models;

public class User {

    private int user_id;
    private String email;
    private String full_name;
    private String mobile;
    private int branch_id;
    private String branch_name;
    private String IMEI1;
    private String IMEI2;
    private String u_img;

    public User(int user_id, String email, String full_name, String mobile, int branch_id, String branch_name, String IMEI1, String IMEI2, String u_img) {
        this.user_id = user_id;
        this.email = email;
        this.full_name = full_name;
        this.mobile = mobile;
        this.branch_id = branch_id;
        this.branch_name = branch_name;
        this.IMEI1 = IMEI1;
        this.IMEI2 = IMEI2;
        this.u_img = u_img;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getEmail() {
        return email;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getMobile() {
        return mobile;
    }

    public int getBranch_id() {
        return branch_id;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public String getIMEI1() {
        return IMEI1;
    }

    public String getIMEI2() {
        return IMEI2;
    }

    public String getU_img() {
        return u_img;
    }
}
