package com.example.bhoomi.lms.APIModel.LiveClass;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiveClassResp {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("lms_class_info")
@Expose
private List<LiveClassData> liveClassData = null;

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

public List<LiveClassData> getLiveClassData() {
return liveClassData;
}

public void setLiveClassData(List<LiveClassData> liveClassData) {
this.liveClassData = liveClassData;
}

}