package com.example.bhoomi.lms.APIModel.CourseDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvarageRate {

    @SerializedName("five_star")
    @Expose
    private String fiveStar;
    @SerializedName("fourstar")
    @Expose
    private String fourstar;
    @SerializedName("threestar")
    @Expose
    private String threestar;
    @SerializedName("twostar")
    @Expose
    private String twostar;
    @SerializedName("onestar")
    @Expose
    private String onestar;
    @SerializedName("average")
    @Expose
    private Integer average;

    public String getFiveStar() {
        return fiveStar;
    }

    public void setFiveStar(String fiveStar) {
        this.fiveStar = fiveStar;
    }

    public String getFourstar() {
        return fourstar;
    }

    public void setFourstar(String fourstar) {
        this.fourstar = fourstar;
    }

    public String getThreestar() {
        return threestar;
    }

    public void setThreestar(String threestar) {
        this.threestar = threestar;
    }

    public String getTwostar() {
        return twostar;
    }

    public void setTwostar(String twostar) {
        this.twostar = twostar;
    }

    public String getOnestar() {
        return onestar;
    }

    public void setOnestar(String onestar) {
        this.onestar = onestar;
    }

    public Integer getAverage() {
        return average;
    }

    public void setAverage(Integer average) {
        this.average = average;
    }
}
