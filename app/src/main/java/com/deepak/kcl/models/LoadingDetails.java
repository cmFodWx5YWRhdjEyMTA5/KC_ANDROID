package com.deepak.kcl.models;

public class LoadingDetails {

    private int user_id;
    private String loading_type;
    private String loading_qty;
    private String load_img;

    public LoadingDetails(int user_id, String loading_type, String loading_qty, String load_img) {
        this.user_id = user_id;
        this.loading_type = loading_type;
        this.loading_qty = loading_qty;
        this.load_img = load_img;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getLoading_type() {
        return loading_type;
    }

    public String getLoading_qty() {
        return loading_qty;
    }

    public String getLoad_img() {
        return load_img;
    }
}
