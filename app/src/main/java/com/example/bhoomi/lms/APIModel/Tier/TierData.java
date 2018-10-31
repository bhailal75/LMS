package com.example.bhoomi.lms.APIModel.Tier;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TierData {

@SerializedName("tier_id")
@Expose
private String tierId;
@SerializedName("tier_name")
@Expose
private String tierName;
@SerializedName("price")
@Expose
private String price;
@SerializedName("ios_id")
@Expose
private String iosId;
@SerializedName("android_id")
@Expose
private String androidId;

public String getTierId() {
return tierId;
}

public void setTierId(String tierId) {
this.tierId = tierId;
}

public String getTierName() {
return tierName;
}

public void setTierName(String tierName) {
this.tierName = tierName;
}

public String getPrice() {
return price;
}

public void setPrice(String price) {
this.price = price;
}

public String getIosId() {
return iosId;
}

public void setIosId(String iosId) {
this.iosId = iosId;
}

public String getAndroidId() {
return androidId;
}

public void setAndroidId(String androidId) {
this.androidId = androidId;
}

}