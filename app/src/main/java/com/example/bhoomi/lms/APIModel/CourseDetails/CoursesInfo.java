package com.example.bhoomi.lms.APIModel.CourseDetails;
import java.util.List;

import com.example.bhoomi.lms.APIModel.CourseDetails.SectionInfo;
import com.example.bhoomi.lms.APIModel.Tier.TierData;
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
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("course_title_ar")
    @Expose
    private String courseTitleAr;
    @SerializedName("tier_id")
    @Expose
    private String tierId;
    @SerializedName("this_course_includes")
    @Expose
    private String thisCourseIncludes;
    @SerializedName("what_will_i_learn")
    @Expose
    private String whatWillILearn;
    @SerializedName("course_new_price")
    @Expose
    private Integer courseNewPrice;
    @SerializedName("price_tier")
    @Expose
    private TierData priceTier;
    @SerializedName("video_ids")
    @Expose
    private String videoIds;
    @SerializedName("doc_ids")
    @Expose
    private String docIds;
    @SerializedName("is_wish")
    @Expose
    private String isWish;
    @SerializedName("is_subscribe")
    @Expose
    private String isSubscribe;
    @SerializedName("course_image")
    @Expose
    private String courseImage;
    @SerializedName("course_video")
    @Expose
    private String courseVideo;
    @SerializedName("section_info")
    @Expose
    private List<SectionInfo> sectionInfo = null;

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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCourseTitleAr() {
        return courseTitleAr;
    }

    public void setCourseTitleAr(String courseTitleAr) {
        this.courseTitleAr = courseTitleAr;
    }

    public String getTierId() {
        return tierId;
    }

    public void setTierId(String tierId) {
        this.tierId = tierId;
    }

    public String getThisCourseIncludes() {
        return thisCourseIncludes;
    }

    public void setThisCourseIncludes(String thisCourseIncludes) {
        this.thisCourseIncludes = thisCourseIncludes;
    }

    public String getWhatWillILearn() {
        return whatWillILearn;
    }

    public void setWhatWillILearn(String whatWillILearn) {
        this.whatWillILearn = whatWillILearn;
    }

    public Integer getCourseNewPrice() {
        return courseNewPrice;
    }

    public void setCourseNewPrice(Integer courseNewPrice) {
        this.courseNewPrice = courseNewPrice;
    }

    public TierData getPriceTier() {
        return priceTier;
    }

    public void setPriceTier(TierData priceTier) {
        this.priceTier = priceTier;
    }

    public String getVideoIds() {
        return videoIds;
    }

    public void setVideoIds(String videoIds) {
        this.videoIds = videoIds;
    }

    public String getDocIds() {
        return docIds;
    }

    public void setDocIds(String docIds) {
        this.docIds = docIds;
    }

    public String getIsWish() {
        return isWish;
    }

    public void setIsWish(String isWish) {
        this.isWish = isWish;
    }

    public String getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(String isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }

    public String getCourseVideo() {
        return courseVideo;
    }

    public void setCourseVideo(String courseVideo) {
        this.courseVideo = courseVideo;
    }

    public List<SectionInfo> getSectionInfo() {
        return sectionInfo;
    }

    public void setSectionInfo(List<SectionInfo> sectionInfo) {
        this.sectionInfo = sectionInfo;
    }

}









//
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//import java.util.List;
//
//public class CoursesInfo {
////// add user_id pending/////
//    @SerializedName("course_id")
//    @Expose
//    private String courseId;
//    @SerializedName("course_title")
//    @Expose
//    private String courseTitle;
//    @SerializedName("course_intro")
//    @Expose
//    private String courseIntro;
//    @SerializedName("course_description")
//    @Expose
//    private String courseDescription;
//    @SerializedName("course_price")
//    @Expose
//    private String coursePrice;
//    @SerializedName("category_id")
//    @Expose
//    private String categoryId;
//    @SerializedName("course_requirement")
//    @Expose
//    private String courseRequirement;
//    @SerializedName("target_audience")
//    @Expose
//    private String targetAudience;
//    @SerializedName("what_i_get")
//    @Expose
//    private String whatIGet;
//    @SerializedName("course_count_reviews")
//    @Expose
//    private String courseCountReviews;
//    @SerializedName("course_rating")
//    @Expose
//    private String courseRating;
//    @SerializedName("course_view_count")
//    @Expose
//    private String courseViewCount;
//    @SerializedName("created_by")
//    @Expose
//    private String createdBy;
//    @SerializedName("public")
//    @Expose
//    private String _public;
//    @SerializedName("active")
//    @Expose
//    private String active;
//    @SerializedName("parent_category_id")
//    @Expose
//    private String parentCategoryId;
//    @SerializedName("created_date")
//    @Expose
//    private String createdDate;
//    @SerializedName("discount")
//    @Expose
//    private String discount;
//    @SerializedName("this_course_includes")
//    @Expose
//    private String thisCourseIncludes;
//    @SerializedName("what_will_i_learn")
//    @Expose
//    private String whatWillILearn;
//    @SerializedName("course_image")
//    @Expose
//    private String courseImage;
//    @SerializedName("total_video_hours")
//    @Expose
//    private String total_video_hours;
//    @SerializedName("is_wish")
//    @Expose
//    private String isWish;
//    @SerializedName("total_articals")
//    @Expose
//    private String total_articals;
//    @SerializedName("section_info")
//    @Expose
//    private List<SectionInfo> sectionInfo = null;
//
//    public String getCourseId() {
//        return courseId;
//    }
//
//    public void setCourseId(String courseId) {
//        this.courseId = courseId;
//    }
//
//    public String getCourseTitle() {
//        return courseTitle;
//    }
//
//    public void setCourseTitle(String courseTitle) {
//        this.courseTitle = courseTitle;
//    }
//
//    public String getCourseIntro() {
//        return courseIntro;
//    }
//
//    public void setCourseIntro(String courseIntro) {
//        this.courseIntro = courseIntro;
//    }
//
//    public String getCourseDescription() {
//        return courseDescription;
//    }
//
//    public void setCourseDescription(String courseDescription) {
//        this.courseDescription = courseDescription;
//    }
//
//    public String getCoursePrice() {
//        return coursePrice;
//    }
//
//    public void setCoursePrice(String coursePrice) {
//        this.coursePrice = coursePrice;
//    }
//
//    public String getCategoryId() {
//        return categoryId;
//    }
//
//    public void setCategoryId(String categoryId) {
//        this.categoryId = categoryId;
//    }
//
//    public String getCourseRequirement() {
//        return courseRequirement;
//    }
//
//    public void setCourseRequirement(String courseRequirement) {
//        this.courseRequirement = courseRequirement;
//    }
//
//    public String getTargetAudience() {
//        return targetAudience;
//    }
//
//    public void setTargetAudience(String targetAudience) {
//        this.targetAudience = targetAudience;
//    }
//
//    public String getWhatIGet() {
//        return whatIGet;
//    }
//
//    public void setWhatIGet(String whatIGet) {
//        this.whatIGet = whatIGet;
//    }
//
//    public String getCourseCountReviews() {
//        return courseCountReviews;
//    }
//
//    public void setCourseCountReviews(String courseCountReviews) {
//        this.courseCountReviews = courseCountReviews;
//    }
//
//    public String getCourseRating() {
//        return courseRating;
//    }
//
//    public void setCourseRating(String courseRating) {
//        this.courseRating = courseRating;
//    }
//
//    public String getCourseViewCount() {
//        return courseViewCount;
//    }
//
//    public void setCourseViewCount(String courseViewCount) {
//        this.courseViewCount = courseViewCount;
//    }
//
//    public String getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(String createdBy) {
//        this.createdBy = createdBy;
//    }
//
//    public String getPublic() {
//        return _public;
//    }
//
//    public void setPublic(String _public) {
//        this._public = _public;
//    }
//
//    public String getActive() {
//        return active;
//    }
//
//    public void setActive(String active) {
//        this.active = active;
//    }
//
//    public String getParentCategoryId() {
//        return parentCategoryId;
//    }
//
//    public void setParentCategoryId(String parentCategoryId) {
//        this.parentCategoryId = parentCategoryId;
//    }
//
//    public String getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(String createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public String getDiscount() {
//        return discount;
//    }
//
//    public void setDiscount(String discount) {
//        this.discount = discount;
//    }
//
//    public String getThisCourseIncludes() {
//        return thisCourseIncludes;
//    }
//
//    public void setThisCourseIncludes(String thisCourseIncludes) {
//        this.thisCourseIncludes = thisCourseIncludes;
//    }
//
//    public String getWhatWillILearn() {
//        return whatWillILearn;
//    }
//
//    public void setWhatWillILearn(String whatWillILearn) {
//        this.whatWillILearn = whatWillILearn;
//    }
//
//    public String getCourseImage() {
//        return courseImage;
//    }
//
//    public void setCourseImage(String courseImage) {
//        this.courseImage = courseImage;
//    }
//
//    public List<SectionInfo> getSectionInfo() {
//        return sectionInfo;
//    }
//
//    public void setSectionInfo(List<SectionInfo> sectionInfo) {
//        this.sectionInfo = sectionInfo;
//    }
//
//    public String getTotal_video_hours() {
//        return total_video_hours;
//    }
//
//    public void setTotal_video_hours(String total_video_hours) {
//        this.total_video_hours = total_video_hours;
//    }
//
//    public String getTotal_articals() {
//        return total_articals;
//    }
//
//    public void setTotal_articals(String total_articals) {
//        this.total_articals = total_articals;
//    }
//
//    public String getIsWish() {
//        return isWish;
//    }
//
//    public void setIsWish(String isWish) {
//        this.isWish = isWish;
//    }
//}
