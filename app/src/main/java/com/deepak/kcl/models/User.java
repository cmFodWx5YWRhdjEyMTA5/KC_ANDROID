package com.deepak.kcl.models;

public class User {

    private int uid;
    private String uname;
    private String uemail;
    private String umobile;
    private String uimei_no1;
    private String uimei_no2;

    public User(int uid, String uname, String uemail, String umobile, String uimei_no1, String uimei_no2) {
        this.uid = uid;
        this.uname = uname;
        this.uemail = uemail;
        this.umobile = umobile;
        this.uimei_no1 = uimei_no1;
        this.uimei_no2 = uimei_no2;
    }

    public int getUid() {
        return uid;
    }

    public String getUname() {
        return uname;
    }

    public String getUemail() {
        return uemail;
    }

    public String getUmobile() {
        return umobile;
    }

    public String getUimei_no1() {
        return uimei_no1;
    }

    public String getUimei_no2() {
        return uimei_no2;
    }
}
