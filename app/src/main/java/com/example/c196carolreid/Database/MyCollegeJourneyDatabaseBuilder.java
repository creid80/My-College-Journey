package com.example.c196carolreid.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.c196carolreid.DAO.CourseDAO;
import com.example.c196carolreid.DAO.TermDAO;
import com.example.c196carolreid.Entities.Course;
import com.example.c196carolreid.Entities.Term;

@Database(entities = {Term.class, Course.class}, version = 4,exportSchema = false)
public abstract class MyCollegeJourneyDatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();

    private static volatile MyCollegeJourneyDatabaseBuilder INSTANCE;

    static MyCollegeJourneyDatabaseBuilder getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (MyCollegeJourneyDatabaseBuilder.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),MyCollegeJourneyDatabaseBuilder.class,"MyCollegeJourneyDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
