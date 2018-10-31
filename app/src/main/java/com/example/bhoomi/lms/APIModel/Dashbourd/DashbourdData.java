package com.example.bhoomi.lms.APIModel.Dashbourd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashbourdData {

@SerializedName("course_count")
@Expose
private String courseCount;
@SerializedName("quiz_count")
@Expose
private String quizCount;
@SerializedName("participants_count")
@Expose
private Integer participantsCount;

public String getCourseCount() {
return courseCount;
}

public void setCourseCount(String courseCount) {
this.courseCount = courseCount;
}

public String getQuizCount() {
return quizCount;
}

public void setQuizCount(String quizCount) {
this.quizCount = quizCount;
}

public Integer getParticipantsCount() {
return participantsCount;
}

public void setParticipantsCount(Integer participantsCount) {
this.participantsCount = participantsCount;
}

}