package com.example.bhoomi.lms.APIModel.TutorSubCategoryList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TutorSubCategoryList {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("sub_category_info")
    @Expose
    private List<SubCategoryInfo> subCategoryInfo = null;

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

    public List<SubCategoryInfo> getSubCategoryInfo() {
        return subCategoryInfo;
    }

    public void setSubCategoryInfo(List<SubCategoryInfo> subCategoryInfo) {
        this.subCategoryInfo = subCategoryInfo;
    }

}
