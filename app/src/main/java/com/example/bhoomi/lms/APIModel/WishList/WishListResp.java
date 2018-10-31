package com.example.bhoomi.lms.APIModel.WishList;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WishListResp {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("wish_info")
@Expose
private List<WishListData> wishListData = null;

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

public List<WishListData> getWishInfo() {
return wishListData;
}

public void setWishInfo(List<WishListData> wishInfo) {
this.wishListData = wishInfo;
}

}