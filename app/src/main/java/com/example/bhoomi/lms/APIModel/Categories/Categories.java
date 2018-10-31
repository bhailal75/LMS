package com.example.bhoomi.lms.APIModel.Categories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Categories {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("home_info")
    @Expose
    private List<HomeInfo> homeInfo = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<HomeInfo> getHomeInfo() {
        return homeInfo;
    }

    public void setHomeInfo(List<HomeInfo> homeInfo) {
        this.homeInfo = homeInfo;
    }

}
