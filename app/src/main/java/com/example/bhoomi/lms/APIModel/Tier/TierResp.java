package com.example.bhoomi.lms.APIModel.Tier;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TierResp {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("tier_info")
@Expose
private List<TierData> tierInfo = null;

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

public List<TierData> getTierInfo() {
return tierInfo;
}

public void setTierInfo(List<TierData> tierInfo) {
this.tierInfo = tierInfo;
}

}