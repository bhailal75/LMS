package com.example.bhoomi.lms.APIModel.Celebrity;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CelebrityResp {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("celebrity_info")
@Expose
private List<CelebrityData> celebrityInfo = null;

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

public List<CelebrityData> getCelebrityInfo() {
return celebrityInfo;
}

public void setCelebrityInfo(List<CelebrityData> celebrityInfo) {
this.celebrityInfo = celebrityInfo;
}

}