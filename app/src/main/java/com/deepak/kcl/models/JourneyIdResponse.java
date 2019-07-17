package com.deepak.kcl.models;

import java.util.List;

public class JourneyIdResponse {

    private boolean error;
    private String message;
    private List<JourneyId> JourneyID;

    public JourneyIdResponse(boolean error, String message, List<JourneyId> journeyID) {
        this.error = error;
        this.message = message;
        JourneyID = journeyID;
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

    public List<JourneyId> getJourneyID() {
        return JourneyID;
    }

    public void setJourneyID(List<JourneyId> journeyID) {
        JourneyID = journeyID;
    }
}
