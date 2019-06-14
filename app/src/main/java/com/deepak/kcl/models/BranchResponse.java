package com.deepak.kcl.models;

import java.util.List;

public class BranchResponse {

    private boolean error;
    private List<Branch> branches;

    public BranchResponse(boolean error, List<Branch> branches) {
        this.error = error;
        this.branches = branches;
    }

    public boolean isError() {
        return error;
    }

    public List<Branch> getBranches() {
        return branches;
    }
}
