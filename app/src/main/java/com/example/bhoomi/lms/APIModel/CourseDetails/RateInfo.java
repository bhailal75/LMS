package com.example.bhoomi.lms.APIModel.CourseDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RateInfo {

    @SerializedName("rate_id")
    @Expose
    private String rateId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("no_of_rate")
    @Expose
    private String noOfRate;
    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("created_date_time")
    @Expose
    private String createdDateTime;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("user_name")
    @Expose
    private String userName;

    public String getRateId() {
        return rateId;
    }

    public void setRateId(String rateId) {
        this.rateId = rateId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNoOfRate() {
        return noOfRate;
    }

    public void setNoOfRate(String noOfRate) {
        this.noOfRate = noOfRate;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
