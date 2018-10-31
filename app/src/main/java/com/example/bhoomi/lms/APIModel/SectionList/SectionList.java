package com.example.bhoomi.lms.APIModel.SectionList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SectionList {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("course_sections_info")
    @Expose
    private List<CourseSectionsInfo> courseSectionsInfo = null;

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

    public List<CourseSectionsInfo> getCourseSectionsInfo() {
        return courseSectionsInfo;
    }

    public void setCourseSectionsInfo(List<CourseSectionsInfo> courseSectionsInfo) {
        this.courseSectionsInfo = courseSectionsInfo;
    }
}
