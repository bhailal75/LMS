package com.example.bhoomi.lms.APIModel.UpdateSubCategories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class SubcategoriesInfo {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sub_cat_name")
    @Expose
    private String subCatName;
    @SerializedName("cat_id")
    @Expose
    private String catId;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("sub_cat_active")
    @Expose
    private String subCatActive;
    @SerializedName("last_modified_by")
    @Expose
    private String lastModifiedBy;
    @SerializedName("icon_class")
    @Expose
    private String iconClass;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubCatName() {
        return subCatName;
    }

    public void setSubCatName(String subCatName) {
        this.subCatName = subCatName;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getSubCatActive() {
        return subCatActive;
    }

    public void setSubCatActive(String subCatActive) {
        this.subCatActive = subCatActive;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
