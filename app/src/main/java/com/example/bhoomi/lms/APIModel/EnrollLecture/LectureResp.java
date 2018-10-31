package com.example.bhoomi.lms.APIModel.EnrollLecture;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LectureResp {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("course_title")
@Expose
private String courseTitle;
@SerializedName("user_name")
@Expose
private String userName;
@SerializedName("course_complete")
@Expose
private String courseComplete;
@SerializedName("rate_complete")
@Expose
private String rateComplete;
@SerializedName("course_image")
@Expose
private String courseImage;
@SerializedName("course_video")
@Expose
private String courseVideo;
@SerializedName("course_videos_info")
@Expose
private List<LectureVideosInfo> lectureVideosInfo = null;

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

public String getCourseTitle() {
return courseTitle;
}

public void setCourseTitle(String courseTitle) {
this.courseTitle = courseTitle;
}

public String getUserName() {
return userName;
}

public void setUserName(String userName) {
this.userName = userName;
}

public String getCourseComplete() {
return courseComplete;
}

public void setCourseComplete(String courseComplete) {
this.courseComplete = courseComplete;
}

public String getRateComplete() {
return rateComplete;
}

public void setRateComplete(String rateComplete) {
this.rateComplete = rateComplete;
}

public String getCourseImage() {
return courseImage;
}

public void setCourseImage(String courseImage) {
this.courseImage = courseImage;
}

public String getCourseVideo() {
return courseVideo;
}

public void setCourseVideo(String courseVideo) {
this.courseVideo = courseVideo;
}

public List<LectureVideosInfo> getCourseVideosInfo() {
return lectureVideosInfo;
}

public void setCourseVideosInfo(List<LectureVideosInfo> courseVideosInfo) {
this.lectureVideosInfo = courseVideosInfo;
}

}