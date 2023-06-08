package com.example.c196carolreid.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "course")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private String courseName;
    private String courseStart;
    private String courseEnd;
    private String status;
    private String CIName;
    private String CIPhone;
    private String CIEmail;
    private String note;
    private int TermID;

    public Course(int courseID, String courseName, String courseStart, String courseEnd, String status, String CIName,
                  String CIPhone, String CIEmail, String note, int TermID) {

        this.courseID = courseID;
        this.courseName = courseName;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.status = status;
        this.CIName = CIName;
        this.CIPhone = CIPhone;
        this.CIEmail = CIEmail;
        this.note = note;
        this.TermID = TermID;
    }

    public Course() {}

    public int getCourseID() { return courseID; }

    public void setCourseID(int courseID) { this.courseID = courseID; }

    public String getCourseName() { return courseName; }

    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getCourseStart() { return courseStart; }

    public void setCourseStart(String courseStart) { this.courseStart = courseStart; }

    public String getCourseEnd() { return courseEnd; }

    public void setCourseEnd(String courseEnd) { this.courseEnd = courseEnd; }

    public String getStatus() {return status; }

    public void setStatus(String status) { this.status = status; }

    public String getCIName() { return CIName; }

    public void setCIName(String CIName) { this.CIName = CIName; }

    public String getCIPhone() { return CIPhone; }

    public void setCIPhone(String CIPhone) { this.CIPhone = CIPhone; }

    public String getCIEmail() { return CIEmail; }

    public void setCIEmail(String CIEmail) { this.CIEmail = CIEmail; }

    public String getNote() { return note; }

    public void setNote(String note) { this.note = note; }

    public int getTermID() {
        return TermID;
    }

    public void setTermID(int termID) {
        this.TermID = termID;
    }
}
