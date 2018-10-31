package com.example.bhoomi.lms.APIModel.SubCategories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubCategories {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("popular_courses")
    @Expose
    private List<PopularCourse> popularCourses = null;
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

    public List<PopularCourse> getPopularCourses() {
        return popularCourses;
    }

    public void setPopularCourses(List<PopularCourse> popularCourses) {
        this.popularCourses = popularCourses;
    }

    public List<SubCategoryInfo> getSubCategoryInfo() {
        return subCategoryInfo;
    }

    public void setSubCategoryInfo(List<SubCategoryInfo> subCategoryInfo) {
        this.subCategoryInfo = subCategoryInfo;
    }
}
