package com.example.c196carolreid.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "terms")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int TermID;
    private String termName;
    private double termPrice;

    public Term(int TermID, String termName, double termPrice) {
        this.TermID = TermID;
        this.termName = termName;
        this.termPrice = termPrice;
    }

    public Term() {
    }

    public int getTermID() {
        return TermID;
    }

    public void setTermID(int termID) {
        this.TermID = termID;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public double getTermPrice() {
        return termPrice;
    }

    public void setTermPrice(double termPrice) {
        this.termPrice = termPrice;
    }
}
