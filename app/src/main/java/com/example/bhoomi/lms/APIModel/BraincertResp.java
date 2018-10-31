package com.example.bhoomi.lms.APIModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BraincertResp {

@SerializedName("status")
@Expose
private String status;
@SerializedName("method")
@Expose
private String method;
@SerializedName("class_id")
@Expose
private Integer classId;
@SerializedName("title")
@Expose
private String title;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getMethod() {
return method;
}

public void setMethod(String method) {
this.method = method;
}

public Integer getClassId() {
return classId;
}

public void setClassId(Integer classId) {
this.classId = classId;
}

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

}