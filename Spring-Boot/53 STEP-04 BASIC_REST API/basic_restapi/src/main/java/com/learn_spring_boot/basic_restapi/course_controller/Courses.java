package com.learn_spring_boot.basic_restapi.course_controller;

public class Courses {
    int courseId;
    String courseName;
    String author;

    public Courses(int courseId, String courseName, String author) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.author = author;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
