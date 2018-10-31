package com.example.bhoomi.lms.APIModel.ViewQuiz;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewQuizData {

@SerializedName("title_id")
@Expose
private String titleId;
@SerializedName("title_name")
@Expose
private String titleName;
@SerializedName("exam_price")
@Expose
private String examPrice;
@SerializedName("syllabus")
@Expose
private String syllabus;
@SerializedName("random_ques_no")
@Expose
private String randomQuesNo;
@SerializedName("pass_mark")
@Expose
private String passMark;
@SerializedName("time_duration")
@Expose
private String timeDuration;
@SerializedName("user_id")
@Expose
private String userId;
@SerializedName("category_id")
@Expose
private String categoryId;
@SerializedName("student_can_see_result")
@Expose
private String studentCanSeeResult;
@SerializedName("student_can_see_ans_key")
@Expose
private String studentCanSeeAnsKey;
@SerializedName("retake_allowed")
@Expose
private String retakeAllowed;
@SerializedName("active")
@Expose
private String active;
@SerializedName("course_id")
@Expose
private String courseId;
@SerializedName("public")
@Expose
private String _public;
@SerializedName("exam_created")
@Expose
private String examCreated;
@SerializedName("last_modified_by")
@Expose
private String lastModifiedBy;
@SerializedName("category_name")
@Expose
private String categoryName;
@SerializedName("created_by_name")
@Expose
private String createdByName;
@SerializedName("image_url")
@Expose
private String imageUrl;
@SerializedName("icon_class")
@Expose
private String iconClass;

public String getTitleId() {
return titleId;
}

public void setTitleId(String titleId) {
this.titleId = titleId;
}

public String getTitleName() {
return titleName;
}

public void setTitleName(String titleName) {
this.titleName = titleName;
}

public String getExamPrice() {
return examPrice;
}

public void setExamPrice(String examPrice) {
this.examPrice = examPrice;
}

public String getSyllabus() {
return syllabus;
}

public void setSyllabus(String syllabus) {
this.syllabus = syllabus;
}

public String getRandomQuesNo() {
return randomQuesNo;
}

public void setRandomQuesNo(String randomQuesNo) {
this.randomQuesNo = randomQuesNo;
}

public String getPassMark() {
return passMark;
}

public void setPassMark(String passMark) {
this.passMark = passMark;
}

public String getTimeDuration() {
return timeDuration;
}

public void setTimeDuration(String timeDuration) {
this.timeDuration = timeDuration;
}

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public String getCategoryId() {
return categoryId;
}

public void setCategoryId(String categoryId) {
this.categoryId = categoryId;
}

public String getStudentCanSeeResult() {
return studentCanSeeResult;
}

public void setStudentCanSeeResult(String studentCanSeeResult) {
this.studentCanSeeResult = studentCanSeeResult;
}

public String getStudentCanSeeAnsKey() {
return studentCanSeeAnsKey;
}

public void setStudentCanSeeAnsKey(String studentCanSeeAnsKey) {
this.studentCanSeeAnsKey = studentCanSeeAnsKey;
}

public String getRetakeAllowed() {
return retakeAllowed;
}

public void setRetakeAllowed(String retakeAllowed) {
this.retakeAllowed = retakeAllowed;
}

public String getActive() {
return active;
}

public void setActive(String active) {
this.active = active;
}

public String getCourseId() {
return courseId;
}

public void setCourseId(String courseId) {
this.courseId = courseId;
}

public String getPublic() {
return _public;
}

public void setPublic(String _public) {
this._public = _public;
}

public String getExamCreated() {
return examCreated;
}

public void setExamCreated(String examCreated) {
this.examCreated = examCreated;
}

public String getLastModifiedBy() {
return lastModifiedBy;
}

public void setLastModifiedBy(String lastModifiedBy) {
this.lastModifiedBy = lastModifiedBy;
}

public String getCategoryName() {
return categoryName;
}

public void setCategoryName(String categoryName) {
this.categoryName = categoryName;
}

public String getCreatedByName() {
return createdByName;
}

public void setCreatedByName(String createdByName) {
this.createdByName = createdByName;
}

public String getImageUrl() {
return imageUrl;
}

public void setImageUrl(String imageUrl) {
this.imageUrl = imageUrl;
}

public String getIconClass() {
return iconClass;
}

public void setIconClass(String iconClass) {
this.iconClass = iconClass;
}

}