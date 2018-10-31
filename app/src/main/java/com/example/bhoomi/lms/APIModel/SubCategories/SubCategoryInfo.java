package com.example.bhoomi.lms.APIModel.SubCategories;

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
    private Object iconClass;
    @SerializedName("sub_cat_name_ar")
    @Expose
    private String subCatNameAr;


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

    public Object getIconClass() {
        return iconClass;
    }

    public void setIconClass(Object iconClass) {
        this.iconClass = iconClass;
    }

    public String getSubCatNameAr() {
        return subCatNameAr;
    }

    public void setSubCatNameAr(String subCatNameAr) {
        this.subCatNameAr = subCatNameAr;
    }
}
