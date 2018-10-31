package com.example.bhoomi.lms.APIModel.WishList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WishListData {

@SerializedName("course_id")
@Expose
private String courseId;
@SerializedName("course_title")
@Expose
private String courseTitle;
@SerializedName("course_rating")
@Expose
private String courseRating;
@SerializedName("created_by")
@Expose
private String createdBy;
@SerializedName("created_by_name")
@Expose
private String createdByName;
@SerializedName("course_price")
@Expose
private String coursePrice;
@SerializedName("course_new_price")
@Expose
private String courseNewPrice;
@SerializedName("total_user_rate")
@Expose
private String totalUserRate;
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

public String getCourseRating() {
return courseRating;
}

public void setCourseRating(String courseRating) {
this.courseRating = courseRating;
}

public String getCreatedBy() {
return createdBy;
}

public void setCreatedBy(String createdBy) {
this.createdBy = createdBy;
}

public String getCreatedByName() {
return createdByName;
}

public void setCreatedByName(String createdByName) {
this.createdByName = createdByName;
}

public String getCoursePrice() {
return coursePrice;
}

public void setCoursePrice(String coursePrice) {
this.coursePrice = coursePrice;
}

public String getCourseNewPrice() {
return courseNewPrice;
}

public void setCourseNewPrice(String courseNewPrice) {
this.courseNewPrice = courseNewPrice;
}

public String getTotalUserRate() {
return totalUserRate;
}

public void setTotalUserRate(String totalUserRate) {
this.totalUserRate = totalUserRate;
}

public String getCourseImage() {
return courseImage;
}

public void setCourseImage(String courseImage) {
this.courseImage = courseImage;
}

}