package com.example.c196carolreid.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "course")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private String courseName;
    private double coursePrice;
    private int TermID;

    public Course(int courseID, String courseName, double coursePrice, int TermID) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.coursePrice = coursePrice;
        this.TermID = TermID;
    }

    public Course() {
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(double coursePrice) {
        this.coursePrice = coursePrice;
    }

    public int getTermID() {
        return TermID;
    }

    public void setTermID(int termID) {
        this.TermID = termID;
    }
}
