package com.example.bhoomi.lms.APIModel.CreateQuiz;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateQuizResp {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("exam_title_info")
@Expose
private List<CreateQuizData> createQuizData = null;

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

public List<CreateQuizData> getCreateQuizData() {
        return createQuizData;
    }

public void setCreateQuizData(List<CreateQuizData> createQuizData) {
        this.createQuizData = createQuizData;
    }
}