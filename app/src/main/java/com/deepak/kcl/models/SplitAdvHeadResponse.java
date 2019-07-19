package com.deepak.kcl.models;

import java.util.List;

public class SplitAdvHeadResponse {

    private boolean error;
    private String message;
    private List<SplitAdvHead> splitAdvHeading;

    public SplitAdvHeadResponse(boolean error, String message, List<SplitAdvHead> splitAdvHeading) {
        this.error = error;
        this.message = message;
        this.splitAdvHeading = splitAdvHeading;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SplitAdvHead> getSplitAdvHeading() {
        return splitAdvHeading;
    }

    public void setSplitAdvHeading(List<SplitAdvHead> splitAdvHeading) {
        this.splitAdvHeading = splitAdvHeading;
    }
}
