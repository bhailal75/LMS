package com.example.bhoomi.lms.APIModel.Categories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeInfo {

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
    private Object iconClass;
    @SerializedName("category_image")
    @Expose
    private String categoryImage;
    @SerializedName("category_detail")
    @Expose
    private List<CourseDetail> courseDetail = null;
    @SerializedName("category_slug")
    @Expose
    private String category_slug;
    @SerializedName("category_name_ar")
    @Expose
    private String categoryNameAr;


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

    public Object getIconClass() {
        return iconClass;
    }

    public void setIconClass(Object iconClass) {
        this.iconClass = iconClass;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public List<CourseDetail> getCourseDetail() {
        return courseDetail;
    }

    public void setCourseDetail(List<CourseDetail> courseDetail) {
        this.courseDetail = courseDetail;
    }

    public String getCategory_slug() {
        return category_slug;
    }

    public void setCategory_slug(String category_slug) {
        this.category_slug = category_slug;
    }

    public String getCategoryNameAr() {
        return categoryNameAr;
    }

    public void setCategoryNameAr(String categoryNameAr) {
        this.categoryNameAr = categoryNameAr;
    }
}
