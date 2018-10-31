package com.example.bhoomi.lms.APIModel.CreateSubCategories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateSubcategories {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("subcategories_info")
    @Expose
    private List<SubcategoriesInfo> subcategoriesInfo = null;

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

    public List<SubcategoriesInfo> getSubcategoriesInfo() {
        return subcategoriesInfo;
    }

    public void setSubcategoriesInfo(List<SubcategoriesInfo> subcategoriesInfo) {
        this.subcategoriesInfo = subcategoriesInfo;
    }

}
