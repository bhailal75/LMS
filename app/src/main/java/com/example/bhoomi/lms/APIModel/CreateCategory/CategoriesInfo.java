package com.example.bhoomi.lms.APIModel.CreateCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class CategoriesInfo {


    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("last_modified_by")
    @Expose
    private String lastModifiedBy;
    @SerializedName("icon_class")
    @Expose
    private String iconClass;
    @SerializedName("category_image")
    @Expose
    private String categoryImage;
    @SerializedName("status")
    @Expose
    private String status;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
