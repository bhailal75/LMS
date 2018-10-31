package com.example.bhoomi.lms.APIModel.AddWishList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddWishListResp {

@SerializedName("status")
@Expose
private String status;
@SerializedName("is_wish")
@Expose
private String isWish;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getIsWish() {
return isWish;
}

public void setIsWish(String isWish) {
this.isWish = isWish;
}

}