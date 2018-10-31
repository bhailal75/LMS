package com.example.bhoomi.lms.APIModel.AddScheduleClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddScheduleClassResp {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("lms_class_info")
@Expose
private AddScheduleClassData lmsClassInfo;

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

public AddScheduleClassData getLmsClassInfo() {
return lmsClassInfo;
}

public void setLmsClassInfo(AddScheduleClassData lmsClassInfo) {
this.lmsClassInfo = lmsClassInfo;
}

}