package com.example.bhoomi.lms.APIModel.UpdateCategotyTutor;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateCategoryResp {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("categories_info")
@Expose
private List<UpdateCategoryData> updateCategoryData = null;

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

    public List<UpdateCategoryData> getUpdateCategoryData() {
        return updateCategoryData;
    }

    public void setUpdateCategoryData(List<UpdateCategoryData> updateCategoryData) {
        this.updateCategoryData = updateCategoryData;
    }
}