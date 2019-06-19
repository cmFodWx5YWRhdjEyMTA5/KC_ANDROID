package com.deepak.kcl.models;

public class ExpenseType {

    private int expense_id;
    private String expense_type;

    public ExpenseType(int expense_id, String expense_type) {
        this.expense_id = expense_id;
        this.expense_type = expense_type;
    }

    public int getExpense_id() {
        return expense_id;
    }

    public String getExpense_type() {
        return expense_type;
    }
}
