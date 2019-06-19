package com.deepak.kcl.models;

import java.util.List;

public class ExpenseTypeResponse {

    private boolean error;
    private List<ExpenseType> expenses;

    public ExpenseTypeResponse(boolean error, List<ExpenseType> expenses) {
        this.error = error;
        this.expenses = expenses;
    }

    public boolean isError() {
        return error;
    }

    public List<ExpenseType> getExpenses() {
        return expenses;
    }
}
