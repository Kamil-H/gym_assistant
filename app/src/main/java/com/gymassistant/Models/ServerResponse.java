package com.gymassistant.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by KamilH on 2016-05-09.
 */
public class ServerResponse {
    @SerializedName("Success")
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
