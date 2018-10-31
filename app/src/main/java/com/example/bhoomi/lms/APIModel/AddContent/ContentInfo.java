package com.example.bhoomi.lms.APIModel.AddContent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class ContentInfo {

    @SerializedName("section_id")
    @Expose
    private String sectionId;
    @SerializedName("video_id")
    @Expose
    private String videoId;
    @SerializedName("content_type")
    @Expose
    private String contentType;
    @SerializedName("course_id")
    @Expose
    private String courseId;

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
