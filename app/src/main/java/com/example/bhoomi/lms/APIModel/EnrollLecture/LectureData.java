package com.example.bhoomi.lms.APIModel.EnrollLecture;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LectureData {

@SerializedName("video_id")
@Expose
private String videoId;
@SerializedName("course_id")
@Expose
private String courseId;
@SerializedName("section_id")
@Expose
private String sectionId;
@SerializedName("video_title")
@Expose
private String videoTitle;
@SerializedName("preview_type")
@Expose
private String previewType;
@SerializedName("content_type")
@Expose
private String contentType;
@SerializedName("video_link")
@Expose
private String videoLink;
@SerializedName("youtube_link")
@Expose
private String youtubeLink;
@SerializedName("file_size")
@Expose
private String fileSize;
@SerializedName("uploaded_by")
@Expose
private String uploadedBy;
@SerializedName("orderList")
@Expose
private String orderList;
@SerializedName("quiz_image_url")
@Expose
private String quizImageUrl;
@SerializedName("quiz_web_view")
@Expose
private String quizWebView;
@SerializedName("is_complete")
@Expose
private String isComplete;

public String getVideoId() {
return videoId;
}

public void setVideoId(String videoId) {
this.videoId = videoId;
}

public String getCourseId() {
return courseId;
}

public void setCourseId(String courseId) {
this.courseId = courseId;
}

public String getSectionId() {
return sectionId;
}

public void setSectionId(String sectionId) {
this.sectionId = sectionId;
}

public String getVideoTitle() {
return videoTitle;
}

public void setVideoTitle(String videoTitle) {
this.videoTitle = videoTitle;
}

public String getPreviewType() {
return previewType;
}

public void setPreviewType(String previewType) {
this.previewType = previewType;
}

public String getContentType() {
return contentType;
}

public void setContentType(String contentType) {
this.contentType = contentType;
}

public String getVideoLink() {
return videoLink;
}

public void setVideoLink(String videoLink) {
this.videoLink = videoLink;
}

public String getYoutubeLink() {
return youtubeLink;
}

public void setYoutubeLink(String youtubeLink) {
this.youtubeLink = youtubeLink;
}

public String getFileSize() {
return fileSize;
}

public void setFileSize(String fileSize) {
this.fileSize = fileSize;
}

public String getUploadedBy() {
return uploadedBy;
}

public void setUploadedBy(String uploadedBy) {
this.uploadedBy = uploadedBy;
}

public String getOrderList() {
return orderList;
}

public void setOrderList(String orderList) {
this.orderList = orderList;
}

public String getQuizImageUrl() {
return quizImageUrl;
}

public void setQuizImageUrl(String quizImageUrl) {
this.quizImageUrl = quizImageUrl;
}

public String getQuizWebView() {
return quizWebView;
}

public void setQuizWebView(String quizWebView) {
this.quizWebView = quizWebView;
}

public String getIsComplete() {
return isComplete;
}

public void setIsComplete(String isComplete) {
this.isComplete = isComplete;
}

}