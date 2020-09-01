package com.cleanup.todoc.database;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;

import androidx.annotation.NonNull;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.Random;

@Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {

    //Singleton
    private static volatile TodocDatabase INSTANCE;

    //DAO
    public abstract ProjectDao mProjectDao();
    public abstract TaskDao mTaskDao();

    public static TodocDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TodocDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodocDatabase.class, "TodocDatabase.db")
                            .addCallback(prepopulateDatabase())
                            //.allowMainThreadQueries()
                            .build();
                }
            }

        }
        return INSTANCE;
    }

    private static void addRandomProject(SupportSQLiteDatabase db, int id, String tableName, String projectName) {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        ContentValues project = new ContentValues();
        project.put("id", id);
        project.put("name", projectName);
        project.put("color", color);

        db.insert(tableName, OnConflictStrategy.IGNORE, project);
    }

    private static Callback prepopulateDatabase() { //creation project w/ color/id/name
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                addRandomProject(db, 1, "Project", "Projet Tartampion");
                addRandomProject(db, 2, "Project", "Projet Lucidia");
                addRandomProject(db, 3, "Project", "Projet Circus");
            }
        };
    }
}
