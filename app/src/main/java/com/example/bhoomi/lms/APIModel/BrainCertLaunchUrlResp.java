package com.example.bhoomi.lms.APIModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BrainCertLaunchUrlResp {

@SerializedName("status")
@Expose
private String status;
@SerializedName("class_id")
@Expose
private String classId;
@SerializedName("method")
@Expose
private String method;
@SerializedName("launchurl")
@Expose
private String launchurl;
@SerializedName("encryptedlaunchurl")
@Expose
private String encryptedlaunchurl;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getClassId() {
return classId;
}

public void setClassId(String classId) {
this.classId = classId;
}

public String getMethod() {
return method;
}

public void setMethod(String method) {
this.method = method;
}

public String getLaunchurl() {
return launchurl;
}

public void setLaunchurl(String launchurl) {
this.launchurl = launchurl;
}

public String getEncryptedlaunchurl() {
return encryptedlaunchurl;
}

public void setEncryptedlaunchurl(String encryptedlaunchurl) {
this.encryptedlaunchurl = encryptedlaunchurl;
}

}