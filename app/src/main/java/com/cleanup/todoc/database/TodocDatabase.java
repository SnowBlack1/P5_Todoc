package com.cleanup.todoc.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

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

    //INSTANCE
    public static TodocDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TodocDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TodocDatabase.class,
                            "MyDatabase.db").addCallback(roomCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PrepopulateDatabaseAsync(INSTANCE).execute();
        }
    };

    private static class PrepopulateDatabaseAsync extends AsyncTask<Void, Void, Void> {
        private ProjectDao db;

        private PrepopulateDatabaseAsync(TodocDatabase db) {
           this.db = db.mProjectDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Random rnd = new Random();
            int color1 = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            int color2 = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            int color3 = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

            //db.insertProject(new Project("Tartampion",color1)); // ne fonctionne pas
            db.insertProject(new Project("Lucidia",color2));
            db.insertProject(new Project("Circus",color3));

            return null;
        }
    }

    //a enlever
   //private static Callback prepopulateDatabase() { //creation project w/ color/id/name
   //    return new Callback() {
   //        @Override
   //        public void onCreate(@NonNull SupportSQLiteDatabase db) {
   //            super.onCreate(db);

   //            //dans asynctask , dans doItBackground (jusqu'en bas)
   //            Random rnd = new Random();
   //            int color1 = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
   //            int color2 = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
   //            int color3 = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

   //            ContentValues projectTartampion = new ContentValues();
   //            projectTartampion.put("id", 1);
   //            projectTartampion.put("name", "Projet Tartampion");
   //            projectTartampion.put("color", color1);

   //            ContentValues projectLucidia = new ContentValues();
   //            projectLucidia.put("id", 2);
   //            projectLucidia.put("name", "Projet Lucidia");
   //            projectLucidia.put("color", color2);

   //            ContentValues projectCircus = new ContentValues();
   //            projectCircus.put("id", 3);
   //            projectCircus.put("name", "Projet Circus");
   //            projectCircus.put("color", color3);


   //            db.insert("Project", OnConflictStrategy.IGNORE, projectTartampion);
   //            db.insert("Project", OnConflictStrategy.IGNORE, projectLucidia);
   //            db.insert("Project", OnConflictStrategy.IGNORE, projectCircus);

   //        }
   //    };
   //}
}
