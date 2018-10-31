package com.example.bhoomi.lms.APIModel.SearchBy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseInfo {


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
    private Object courseCountReviews;
    @SerializedName("course_rating")
    @Expose
    private String courseRating;
    @SerializedName("course_view_count")
    @Expose
    private Object courseViewCount;
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
    private Object discount;
    @SerializedName("this_course_include")
    @Expose
    private String thisCourseInclude;
    @SerializedName("created_by_name")
    @Expose
    private String createdByName;
    @SerializedName("course_new_price")
    @Expose
    private String courseNewPrice;
    @SerializedName("total_user_rate")
    @Expose
    private String totalUserRate;
    @SerializedName("course_image")
    @Expose
    private String courseImage;

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

    public Object getCourseCountReviews() {
        return courseCountReviews;
    }

    public void setCourseCountReviews(Object courseCountReviews) {
        this.courseCountReviews = courseCountReviews;
    }

    public String getCourseRating() {
        return courseRating;
    }

    public void setCourseRating(String courseRating) {
        this.courseRating = courseRating;
    }

    public Object getCourseViewCount() {
        return courseViewCount;
    }

    public void setCourseViewCount(Object courseViewCount) {
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

    public Object getDiscount() {
        return discount;
    }

    public void setDiscount(Object discount) {
        this.discount = discount;
    }

    public String getThisCourseInclude() {
        return thisCourseInclude;
    }

    public void setThisCourseInclude(String thisCourseInclude) {
        this.thisCourseInclude = thisCourseInclude;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getCourseNewPrice() {
        return courseNewPrice;
    }

    public void setCourseNewPrice(String courseNewPrice) {
        this.courseNewPrice = courseNewPrice;
    }

    public String getTotalUserRate() {
        return totalUserRate;
    }

    public void setTotalUserRate(String totalUserRate) {
        this.totalUserRate = totalUserRate;
    }

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }

}
