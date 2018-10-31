package com.example.bhoomi.lms.APIModel.GmailResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("gplus_id")
    @Expose
    private String gplusId;
    @SerializedName("user_role_id")
    @Expose
    private String userRoleId;
    @SerializedName("profile_image")
    @Expose
    private String profile_image;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getGplusId() {
        return gplusId;
    }

    public void setGplusId(String gplusId) {
        this.gplusId = gplusId;
    }

    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }
}
