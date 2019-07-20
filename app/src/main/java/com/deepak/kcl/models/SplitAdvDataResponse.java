package com.deepak.kcl.models;

import java.util.List;

public class SplitAdvDataResponse {

    private boolean error;
    private String message;
    private List<SplitAdvData> splitAdvanceData;

    public SplitAdvDataResponse(boolean error, String message, List<SplitAdvData> splitAdvanceData) {
        this.error = error;
        this.message = message;
        this.splitAdvanceData = splitAdvanceData;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public List<SplitAdvData> getSplitAdvanceData() {
        return splitAdvanceData;
    }
}
