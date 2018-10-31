package com.example.bhoomi.lms.APIModel.CourseDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SectionInfo {

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
    @SerializedName("video_ids")
    @Expose
    private String videoIds;
    @SerializedName("doc_ids")
    @Expose
    private Object docIds;
    @SerializedName("audio_ids")
    @Expose
    private Object audioIds;
    @SerializedName("ppt_ids")
    @Expose
    private Object pptIds;
    @SerializedName("orderList")
    @Expose
    private String orderList;

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

    public String getVideoIds() {
        return videoIds;
    }

    public void setVideoIds(String videoIds) {
        this.videoIds = videoIds;
    }

    public Object getDocIds() {
        return docIds;
    }

    public void setDocIds(Object docIds) {
        this.docIds = docIds;
    }

    public Object getAudioIds() {
        return audioIds;
    }

    public void setAudioIds(Object audioIds) {
        this.audioIds = audioIds;
    }

    public Object getPptIds() {
        return pptIds;
    }

    public void setPptIds(Object pptIds) {
        this.pptIds = pptIds;
    }

    public String getOrderList() {
        return orderList;
    }

    public void setOrderList(String orderList) {
        this.orderList = orderList;
    }


}
