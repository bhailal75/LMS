package com.example.bhoomi.lms.Teacher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoursesInfo {

    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("course_title")
    @Expose
    private String courseTitle;
    @SerializedName("course_intro")
    @Expose
    private String courseIntro;
    @SerializedName("course_description")
    @Expose
    private String courseDescription;
    @SerializedName("course_price")
    @Expose
    private String coursePrice;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("course_requirement")
    @Expose
    private String courseRequirement;
    @SerializedName("target_audience")
    @Expose
    private String targetAudience;
    @SerializedName("what_i_get")
    @Expose
    private String whatIGet;
    @SerializedName("course_count_reviews")
    @Expose
    private String courseCountReviews;
    @SerializedName("course_rating")
    @Expose
    private String courseRating;
    @SerializedName("course_view_count")
    @Expose
    private String courseViewCount;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("public")
    @Expose
    private String _public;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("parent_category_id")
    @Expose
    private String parentCategoryId;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("total_video_hours")
    @Expose
    private String totalVideoHours;
    @SerializedName("total_articals")
    @Expose
    private String totalArticals;
    @SerializedName("access_module")
    @Expose
    private String accessModule;
    @SerializedName("certificate_of_completion")
    @Expose
    private String certificateOfCompletion;
    @SerializedName("launguage")
    @Expose
    private String launguage;
    @SerializedName("feature_video")
    @Expose
    private String featureVideo;
    @SerializedName("created_by_name")
    @Expose
    private String createdByName;
    @SerializedName("last_modified_by")
    @Expose
    private String lastModifiedBy;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("subcategory_name")
    @Expose
    private String subcategoryName;
    @SerializedName("icon_class")
    @Expose
    private String iconClass;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseIntro() {
        return courseIntro;
    }

    public void setCourseIntro(String courseIntro) {
        this.courseIntro = courseIntro;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(String coursePrice) {
        this.coursePrice = coursePrice;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCourseRequirement() {
        return courseRequirement;
    }

    public void setCourseRequirement(String courseRequirement) {
        this.courseRequirement = courseRequirement;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public String getWhatIGet() {
        return whatIGet;
    }

    public void setWhatIGet(String whatIGet) {
        this.whatIGet = whatIGet;
    }

    public String getCourseCountReviews() {
        return courseCountReviews;
    }

    public void setCourseCountReviews(String courseCountReviews) {
        this.courseCountReviews = courseCountReviews;
    }

    public String getCourseRating() {
        return courseRating;
    }

    public void setCourseRating(String courseRating) {
        this.courseRating = courseRating;
    }

    public String getCourseViewCount() {
        return courseViewCount;
    }

    public void setCourseViewCount(String courseViewCount) {
        this.courseViewCount = courseViewCount;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getPublic() {
        return _public;
    }

    public void setPublic(String _public) {
        this._public = _public;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTotalVideoHours() {
        return totalVideoHours;
    }

    public void setTotalVideoHours(String totalVideoHours) {
        this.totalVideoHours = totalVideoHours;
    }

    public String getTotalArticals() {
        return totalArticals;
    }

    public void setTotalArticals(String totalArticals) {
        this.totalArticals = totalArticals;
    }

    public String getAccessModule() {
        return accessModule;
    }

    public void setAccessModule(String accessModule) {
        this.accessModule = accessModule;
    }

    public String getCertificateOfCompletion() {
        return certificateOfCompletion;
    }

    public void setCertificateOfCompletion(String certificateOfCompletion) {
        this.certificateOfCompletion = certificateOfCompletion;
    }

    public String getLaunguage() {
        return launguage;
    }

    public void setLaunguage(String launguage) {
        this.launguage = launguage;
    }

    public String getFeatureVideo() {
        return featureVideo;
    }

    public void setFeatureVideo(String featureVideo) {
        this.featureVideo = featureVideo;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

}
