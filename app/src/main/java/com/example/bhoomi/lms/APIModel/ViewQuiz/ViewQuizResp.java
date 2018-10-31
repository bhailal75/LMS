package com.example.bhoomi.lms.APIModel.ViewQuiz;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewQuizResp {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("exam_title_info")
@Expose
private List<ViewQuizData> viewQuizData = null;

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

public List<ViewQuizData> getViewQuizData() {
return viewQuizData;
}

public void setViewQuizData(List<ViewQuizData> viewQuizData) {
this.viewQuizData = viewQuizData;
}

}