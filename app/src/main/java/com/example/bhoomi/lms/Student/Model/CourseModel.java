package com.example.bhoomi.lms.Student.Model;

public class CourseModel {

    private String image, title, desc, rating, numOfpeople, currentprice, lastprice;
    private int img;

    public CourseModel(Integer integer) {
        this.img = integer;
    }

    public CourseModel() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getNumOfpeople() {
        return numOfpeople;
    }

    public void setNumOfpeople(String numOfpeople) {
        this.numOfpeople = numOfpeople;
    }

    public String getCurrentprice() {
        return currentprice;
    }

    public void setCurrentprice(String currentprice) {
        this.currentprice = currentprice;
    }

    public String getLastprice() {
        return lastprice;
    }

    public void setLastprice(String lastprice) {
        this.lastprice = lastprice;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
