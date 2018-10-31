package com.example.bhoomi.lms.Teacher.Model;

public class CategoryModel {

    private String cat_name, cat_id;

    public CategoryModel() {
    }

    public CategoryModel(String categoryName, String categoryId) {
        this.cat_name = categoryName;
        this.cat_id  = categoryId;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }
}
