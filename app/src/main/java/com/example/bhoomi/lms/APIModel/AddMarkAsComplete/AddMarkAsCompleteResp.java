package com.example.bhoomi.lms.APIModel.AddMarkAsComplete;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddMarkAsCompleteResp {

@SerializedName("status")
@Expose
private String status;
@SerializedName("is_complete")
@Expose
private String isComplete;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getIsComplete() {
return isComplete;
}

public void setIsComplete(String isComplete) {
this.isComplete = isComplete;
}

}