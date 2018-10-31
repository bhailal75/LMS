package com.example.bhoomi.lms.APIModel.SubcategoryList;

import com.example.bhoomi.lms.APIModel.CategoryList.CategoryInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubcatList {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("course_info")
    @Expose
    private List<CourseInfo> courseInfo = null;

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

    public List<CourseInfo> getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(List<CourseInfo> courseInfo) {
        this.courseInfo = courseInfo;
    }

}
