package com.gymassistant.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by KamilH on 2016-05-09.
 */
public class ServerResponse {
    @SerializedName("Success")
    private boolean success;


    @SerializedName("UserId")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
