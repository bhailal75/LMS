package com.example.bhoomi.lms.APIModel.CreateCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateCategory {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("categories_info")
    @Expose
    private List<CategoriesInfo> categoriesInfo = null;

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

    public List<CategoriesInfo> getCategoriesInfo() {
        return categoriesInfo;
    }

    public void setCategoriesInfo(List<CategoriesInfo> categoriesInfo) {
        this.categoriesInfo = categoriesInfo;
    }
}
