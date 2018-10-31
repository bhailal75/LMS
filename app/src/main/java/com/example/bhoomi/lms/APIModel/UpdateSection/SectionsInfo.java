package com.example.bhoomi.lms.APIModel.UpdateSection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class SectionsInfo {

    @SerializedName("section_id")
    @Expose
    private String sectionId;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("section_title")
    @Expose
    private String sectionTitle;
    @SerializedName("course_id")
    @Expose
    private String courseId;

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }


}
