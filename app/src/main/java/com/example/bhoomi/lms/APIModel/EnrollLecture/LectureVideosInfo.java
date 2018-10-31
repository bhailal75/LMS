package com.example.bhoomi.lms.APIModel.EnrollLecture;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LectureVideosInfo {

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
private String docIds;
@SerializedName("audio_ids")
@Expose
private String audioIds;
@SerializedName("ppt_ids")
@Expose
private String pptIds;
@SerializedName("orderList")
@Expose
private String orderList;
@SerializedName("quiz_id")
@Expose
private String quizId;
@SerializedName("video_info")
@Expose
private List<LectureData> lectureData = null;

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

public String getDocIds() {
return docIds;
}

public void setDocIds(String docIds) {
this.docIds = docIds;
}

public String getAudioIds() {
return audioIds;
}

public void setAudioIds(String audioIds) {
this.audioIds = audioIds;
}

public String getPptIds() {
return pptIds;
}

public void setPptIds(String pptIds) {
this.pptIds = pptIds;
}

public String getOrderList() {
return orderList;
}

public void setOrderList(String orderList) {
this.orderList = orderList;
}

public String getQuizId() {
return quizId;
}

public void setQuizId(String quizId) {
this.quizId = quizId;
}

public List<LectureData> getVideoInfo() {
return lectureData;
}

public void setVideoInfo(List<LectureData> videoInfo) {
this.lectureData = videoInfo;
}

}