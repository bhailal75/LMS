package com.example.bhoomi.lms.APIModel.CourseDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatedbyInfo {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_phone")
    @Expose
    private String userPhone;
    @SerializedName("user_role_id")
    @Expose
    private String userRoleId;
    @SerializedName("user_pass")
    @Expose
    private String userPass;
    @SerializedName("paypal_id")
    @Expose
    private String paypalId;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("banned")
    @Expose
    private String banned;
    @SerializedName("user_from")
    @Expose
    private String userFrom;
    @SerializedName("subscription_id")
    @Expose
    private Object subscriptionId;
    @SerializedName("subscription_start")
    @Expose
    private String subscriptionStart;
    @SerializedName("subscription_end")
    @Expose
    private String subscriptionEnd;
    @SerializedName("fb_id")
    @Expose
    private Object fbId;
    @SerializedName("twitter_id")
    @Expose
    private Object twitterId;
    @SerializedName("gplus_id")
    @Expose
    private Object gplusId;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("no_of_student")
    @Expose
    private String noOfStudent;
    @SerializedName("count_course")
    @Expose
    private String countCourse;
    @SerializedName("course_rating")
    @Expose
    private String courseRating;

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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getPaypalId() {
        return paypalId;
    }

    public void setPaypalId(String paypalId) {
        this.paypalId = paypalId;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getBanned() {
        return banned;
    }

    public void setBanned(String banned) {
        this.banned = banned;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    public Object getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Object subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getSubscriptionStart() {
        return subscriptionStart;
    }

    public void setSubscriptionStart(String subscriptionStart) {
        this.subscriptionStart = subscriptionStart;
    }

    public String getSubscriptionEnd() {
        return subscriptionEnd;
    }

    public void setSubscriptionEnd(String subscriptionEnd) {
        this.subscriptionEnd = subscriptionEnd;
    }

    public Object getFbId() {
        return fbId;
    }

    public void setFbId(Object fbId) {
        this.fbId = fbId;
    }

    public Object getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(Object twitterId) {
        this.twitterId = twitterId;
    }

    public Object getGplusId() {
        return gplusId;
    }

    public void setGplusId(Object gplusId) {
        this.gplusId = gplusId;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getNoOfStudent() {
        return noOfStudent;
    }

    public void setNoOfStudent(String noOfStudent) {
        this.noOfStudent = noOfStudent;
    }

    public String getCountCourse() {
        return countCourse;
    }

    public void setCountCourse(String countCourse) {
        this.countCourse = countCourse;
    }

    public String getCourseRating() {
        return courseRating;
    }

    public void setCourseRating(String courseRating) {
        this.courseRating = courseRating;
    }

}
