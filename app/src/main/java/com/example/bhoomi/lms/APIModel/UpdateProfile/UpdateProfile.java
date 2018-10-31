package com.example.bhoomi.lms.APIModel.UpdateProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateProfile {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("profile_info")
    @Expose
    private List<ProfileInfo> profileInfo = null;

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

    public List<ProfileInfo> getProfileInfo() {
        return profileInfo;
    }

    public void setProfileInfo(List<ProfileInfo> profileInfo) {
        this.profileInfo = profileInfo;
    }



}
