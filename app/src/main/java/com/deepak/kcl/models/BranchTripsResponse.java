package com.deepak.kcl.models;

import java.util.List;

public class BranchTripsResponse {

    private boolean error;
    private String message;
    private List<BranchTrips> BranchTrips;

    public BranchTripsResponse(boolean error, String message, List<com.deepak.kcl.models.BranchTrips> branchTrips) {
        this.error = error;
        this.message = message;
        BranchTrips = branchTrips;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public List<com.deepak.kcl.models.BranchTrips> getBranchTrips() {
        return BranchTrips;
    }
}
