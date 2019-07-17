package com.deepak.kcl.models;

import java.util.List;

public class TripAdvanceResponse {

    private boolean error;
    private String message;
    private List<TripAdvance> tripAdvance;

    public TripAdvanceResponse(boolean error, String message, List<TripAdvance> tripAdvance) {
        this.error = error;
        this.message = message;
        this.tripAdvance = tripAdvance;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public List<TripAdvance> getTripAdvance() {
        return tripAdvance;
    }
}
