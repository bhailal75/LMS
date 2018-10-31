package com.example.bhoomi.lms.APIModel.Slider;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SliderData{

@SerializedName("slider_id")
@Expose
private String sliderId;
@SerializedName("slider_en")
@Expose
private String sliderEn;
@SerializedName("slider_ar")
@Expose
private String sliderAr;

public String getSliderId() {
return sliderId;
}

public void setSliderId(String sliderId) {
this.sliderId = sliderId;
}

public String getSliderEn() {
return sliderEn;
}

public void setSliderEn(String sliderEn) {
this.sliderEn = sliderEn;
}

public String getSliderAr() {
return sliderAr;
}

public void setSliderAr(String sliderAr) {
this.sliderAr = sliderAr;
}

}