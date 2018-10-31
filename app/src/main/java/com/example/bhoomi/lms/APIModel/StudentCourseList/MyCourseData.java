package com.example.bhoomi.lms.APIModel.StudentCourseList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyCourseData {

@SerializedName("course_id")
@Expose
private String courseId;
@SerializedName("course_title")
@Expose
private String courseTitle;
@SerializedName("created_by_name")
@Expose
private String createdByName;
@SerializedName("course_complete")
@Expose
private String courseComplete;
@SerializedName("pur_date")
@Expose
private String purDate;
@SerializedName("subscription_enddate")
@Expose
private String subscriptionEnddate;
@SerializedName("course_image")
@Expose
private String courseImage;

public String getCourseId() {
return courseId;
}

public void setCourseId(String courseId) {
this.courseId = courseId;
}

public String getCourseTitle() {
return courseTitle;
}

public void setCourseTitle(String courseTitle) {
this.courseTitle = courseTitle;
}

public String getCreatedByName() {
return createdByName;
}

public void setCreatedByName(String createdByName) {
this.createdByName = createdByName;
}

public String getCourseComplete() {
return courseComplete;
}

public void setCourseComplete(String courseComplete) {
this.courseComplete = courseComplete;
}

public String getPurDate() {
return purDate;
}

public void setPurDate(String purDate) {
this.purDate = purDate;
}

public String getSubscriptionEnddate() {
return subscriptionEnddate;
}

public void setSubscriptionEnddate(String subscriptionEnddate) {
this.subscriptionEnddate = subscriptionEnddate;
}

public String getCourseImage() {
return courseImage;
}

public void setCourseImage(String courseImage) {
this.courseImage = courseImage;
}

}