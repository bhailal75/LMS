package com.example.bhoomi.lms.APIModel.TutorSubCategoryList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategoryInfo {

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
    @SerializedName("created_by_name")
    @Expose
    private String createdByName;
    @SerializedName("category_name")
    @Expose
    private String category_name;

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

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
