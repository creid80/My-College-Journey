package com.example.c196carolreid.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196carolreid.Entities.Assessment;
import com.example.c196carolreid.Entities.Course;

import java.util.List;

@Dao
public interface AssessmentDAO {

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM Assessment ORDER BY assessmentID ASC")
    List<Assessment> getAllAssessments();
}
