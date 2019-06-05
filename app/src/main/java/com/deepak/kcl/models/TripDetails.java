package com.deepak.kcl.models;

public class TripDetails {

    private String detail_id;
    private String detail_name;


    public TripDetails(String detail_id, String detail_name) {
        this.detail_id = detail_id;
        this.detail_name = detail_name;
    }

    public String getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(String detail_id) {
        this.detail_id = detail_id;
    }

    public String getDetail_name() {
        return detail_name;
    }

    public void setDetail_name(String detail_name) {
        this.detail_name = detail_name;
    }
}
