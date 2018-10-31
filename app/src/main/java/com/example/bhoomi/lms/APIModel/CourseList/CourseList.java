
package com.example.bhoomi.lms.APIModel.CourseList;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseList {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("courses_info")
    @Expose
    private List<CoursesInfo> coursesInfo = null;

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

    public List<CoursesInfo> getCoursesInfo() {
        return coursesInfo;
    }

    public void setCoursesInfo(List<CoursesInfo> coursesInfo) {
        this.coursesInfo = coursesInfo;
    }

}
