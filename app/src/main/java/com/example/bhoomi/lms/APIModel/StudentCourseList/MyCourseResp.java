package com.example.bhoomi.lms.APIModel.StudentCourseList;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyCourseResp {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("my_course")
@Expose
private List<MyCourseData> myCourse = null;

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

public List<MyCourseData> getMyCourse() {
return myCourse;
}

public void setMyCourse(List<MyCourseData> myCourse) {
this.myCourse = myCourse;
}

}