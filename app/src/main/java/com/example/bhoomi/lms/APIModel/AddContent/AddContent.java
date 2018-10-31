package com.example.bhoomi.lms.APIModel.AddContent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddContent {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("content_info")
    @Expose
    private ContentInfo contentInfo;

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

    public ContentInfo getContentInfo() {
        return contentInfo;
    }

    public void setContentInfo(ContentInfo contentInfo) {
        this.contentInfo = contentInfo;
    }
}
