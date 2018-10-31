
package com.example.bhoomi.lms.APIModel.ViewCourse;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewCourseList {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("courses_info")
    @Expose
    private List<ViewCoursesInfo> viewcoursesInfo = null;

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

    public List<ViewCoursesInfo> getViewCoursesInfo() {
        return viewcoursesInfo;
    }

    public void setCoursesInfo(List<ViewCoursesInfo> coursesInfo) {
        this.viewcoursesInfo = coursesInfo;
    }

}
