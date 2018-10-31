package com.example.bhoomi.lms.APIModel.SearchBy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchBy {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("course_info")
    @Expose
    private List<CourseInfo> courseInfo = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CourseInfo> getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(List<CourseInfo> courseInfo) {
        this.courseInfo = courseInfo;
    }
}
