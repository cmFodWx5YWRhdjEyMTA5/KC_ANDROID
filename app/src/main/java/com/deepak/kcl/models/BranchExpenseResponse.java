package com.deepak.kcl.models;

import java.util.List;

public class BranchExpenseResponse {

    private boolean error;
    private String message;
    private List<BranchExpense> branchExpense;

    public BranchExpenseResponse(boolean error, String message, List<BranchExpense> branchExpense) {
        this.error = error;
        this.message = message;
        this.branchExpense = branchExpense;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public List<BranchExpense> getBranchExpense() {
        return branchExpense;
    }
}
