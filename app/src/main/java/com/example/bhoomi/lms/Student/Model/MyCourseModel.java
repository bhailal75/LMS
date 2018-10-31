package com.example.bhoomi.lms.Student.Model;

public class MyCourseModel {

    private String course_name;
    private int course_imges;

    public MyCourseModel(Integer integer) {
        this.course_imges = integer;
    }

    public MyCourseModel() {
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public int getCourse_imges() {
        return course_imges;
    }

    public void setCourse_imges(int course_imges) {
        this.course_imges = course_imges;
    }
}
