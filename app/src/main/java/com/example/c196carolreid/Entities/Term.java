package com.example.c196carolreid.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "terms")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int TermID;
    private String termName;
    private String termStart;
    private String termEnd;

    public Term(int TermID, String termName, String termStart, String termEnd) {
        this.TermID = TermID;
        this.termName = termName;
        this.termStart = termStart;
        this.termEnd = termEnd;
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

    public String getTermStart() {
        return termStart;
    }

    public void setTermStart(String termStart) {
        this.termStart = termStart;
    }

    public String getTermEnd() { return termEnd; }

    public void setTermEnd(String termEnd) { this.termEnd = termEnd; }
}
