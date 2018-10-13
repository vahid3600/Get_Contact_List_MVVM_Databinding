package com.example.user.contactslistapp.data.sourse.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.user.contactslistapp.data.model.dbmodel.ContactDBModel;

@Database(entities = {ContactDBModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room
                            .databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "contact_db")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public abstract ContactModelDao contactModelDao();

}
