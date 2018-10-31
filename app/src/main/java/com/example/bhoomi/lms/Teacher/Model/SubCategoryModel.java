package com.example.bhoomi.lms.Teacher.Model;

public class SubCategoryModel {

    private String sub_cat_name, sub_cat_id;

    public SubCategoryModel() {
    }

    public SubCategoryModel(String categoryName, String categoryId) {
        this.sub_cat_name = categoryName;
        this.sub_cat_id = categoryId;
    }

    public String getSub_cat_name() {
        return sub_cat_name;
    }

    public void setSub_cat_name(String sub_cat_name) {
        this.sub_cat_name = sub_cat_name;
    }

    public String getSub_cat_id() {
        return sub_cat_id;
    }

    public void setSub_cat_id(String sub_cat_id) {
        this.sub_cat_id = sub_cat_id;
    }
}
