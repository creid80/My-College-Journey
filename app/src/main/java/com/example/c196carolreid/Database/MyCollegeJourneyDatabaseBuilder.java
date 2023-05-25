package com.example.c196carolreid.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.c196carolreid.dao.PartDAO;
import com.example.c196carolreid.dao.ProductDAO;
import com.example.c196carolreid.entities.Part;
import com.example.c196carolreid.entities.Product;

@Database(entities = {Product.class, Part.class}, version = 1, exportSchema = false)
public abstract class MyCollegeJourneyDatabaseBuilder extends RoomDatabase {

    public abstract ProductDAO productDAO();
    public abstract PartDAO partDAO();

    private static volatile MyCollegeJourneyDatabaseBuilder INSTANCE;

    static MyCollegeJourneyDatabaseBuilder getDatabase(final Context context) {

        if(INSTANCE == null) {
            synchronized (MyCollegeJourneyDatabaseBuilder.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MyCollegeJourneyDatabaseBuilder.class, "MyCollegeJourney.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
