package com.deepak.kcl.models;

import java.util.List;

public class LoadingResponse {

    private boolean error;
    private String message;
    private List<LoadingDetails> load_unload;

    public LoadingResponse(boolean error, String message, List<LoadingDetails> load_unload) {
        this.error = error;
        this.message = message;
        this.load_unload = load_unload;
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

    public List<LoadingDetails> getLoad_unload() {
        return load_unload;
    }

    public void setLoad_unload(List<LoadingDetails> load_unload) {
        this.load_unload = load_unload;
    }
}
