package com.example.bhoomi.lms.APIModel.Dashbourd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashbourdResp {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("count_info")
@Expose
private DashbourdData dashbourdData;

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

public DashbourdData getDashbourdData() {
return dashbourdData;
}

public void setDashbourdData(DashbourdData dashbourdData) {
this.dashbourdData = dashbourdData;
}

}