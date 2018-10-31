package com.example.bhoomi.lms.APIModel.Celebrity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CelebrityData {

@SerializedName("user_id")
@Expose
private String userId;
@SerializedName("user_name")
@Expose
private String userName;
@SerializedName("user_desc")
@Expose
private String userDesc;
@SerializedName("reviews")
@Expose
private String reviews;
@SerializedName("totalstudents")
@Expose
private String totalstudents;
@SerializedName("totalcourse")
@Expose
private String totalcourse;
@SerializedName("about_the_instructor")
@Expose
private String aboutTheInstructor;
@SerializedName("profile_image")
@Expose
private String profileImage;

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public String getUserName() {
return userName;
}

public void setUserName(String userName) {
this.userName = userName;
}

public String getUserDesc() {
return userDesc;
}

public void setUserDesc(String userDesc) {
this.userDesc = userDesc;
}

public String getReviews() {
return reviews;
}

public void setReviews(String reviews) {
this.reviews = reviews;
}

public String getTotalstudents() {
return totalstudents;
}

public void setTotalstudents(String totalstudents) {
this.totalstudents = totalstudents;
}

public String getTotalcourse() {
return totalcourse;
}

public void setTotalcourse(String totalcourse) {
this.totalcourse = totalcourse;
}

public String getAboutTheInstructor() {
return aboutTheInstructor;
}

public void setAboutTheInstructor(String aboutTheInstructor) {
this.aboutTheInstructor = aboutTheInstructor;
}

public String getProfileImage() {
return profileImage;
}

public void setProfileImage(String profileImage) {
this.profileImage = profileImage;
}

}