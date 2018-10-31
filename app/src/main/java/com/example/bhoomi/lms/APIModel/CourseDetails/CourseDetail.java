package com.example.bhoomi.lms.APIModel.CourseDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourseDetail {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("courses_info")
    @Expose
    private List<CoursesInfo> coursesInfo = null;
    @SerializedName("student_also_view")
    @Expose
    private List<StudentAlsoView> studentAlsoView = null;
    @SerializedName("createdby_info")
    @Expose
    private CreatedbyInfo createdbyInfo;
    @SerializedName("avarage_rate")
    @Expose
    private AvarageRate avarageRate;
    @SerializedName("rate_info")
    @Expose
    private List<RateInfo> rateInfo = null;

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

    public List<StudentAlsoView> getStudentAlsoView() {
        return studentAlsoView;
    }

    public void setStudentAlsoView(List<StudentAlsoView> studentAlsoView) {
        this.studentAlsoView = studentAlsoView;
    }

    public CreatedbyInfo getCreatedbyInfo() {
        return createdbyInfo;
    }

    public void setCreatedbyInfo(CreatedbyInfo createdbyInfo) {
        this.createdbyInfo = createdbyInfo;
    }

    public AvarageRate getAvarageRate() {
        return avarageRate;
    }

    public void setAvarageRate(AvarageRate avarageRate) {
        this.avarageRate = avarageRate;
    }

    public List<RateInfo> getRateInfo() {
        return rateInfo;
    }

    public void setRateInfo(List<RateInfo> rateInfo) {
        this.rateInfo = rateInfo;
    }
}
