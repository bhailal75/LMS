package com.example.bhoomi.lms.APIModel.Slider;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SliderResp {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("slider_info")
@Expose
private List<SliderData> sliderData = null;

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

public List<SliderData> getSliderData() {
        return sliderData;
    }

public void setSliderData(List<SliderData> sliderData) {
        this.sliderData = sliderData;
    }
}