package com.example.bhoomi.lms.APIModel.AddSections;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddSection {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("sections_info")
    @Expose
    private SectionsInfo sectionsInfo;

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

    public SectionsInfo getSectionsInfo() {
        return sectionsInfo;
    }

    public void setSectionsInfo(SectionsInfo sectionsInfo) {
        this.sectionsInfo = sectionsInfo;
    }
}
