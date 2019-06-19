package com.deepak.kcl.models;

import java.util.List;

public class TripExpenseResponse {

    private boolean error;
    private String message;
    private List<TripExpense> tripExpense;

    public TripExpenseResponse(boolean error, String message, List<TripExpense> tripExpense) {
        this.error = error;
        this.message = message;
        this.tripExpense = tripExpense;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public List<TripExpense> getTripExpense() {
        return tripExpense;
    }
}
