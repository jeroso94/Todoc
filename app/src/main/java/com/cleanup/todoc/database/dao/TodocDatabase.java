package com.cleanup.todoc.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import static com.cleanup.todoc.model.Project.getAllProjects;

/**
 * Created by JeroSo94 on 24/11/2021.
 */
@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {

        // --- SINGLETON / Création d'un jeton d'accès exclusif ---
        private static volatile TodocDatabase INSTANCE;

        // --- DAO ---
        public abstract TaskDao mTaskDao();
        public abstract ProjectDao mProjectDao();

        // --- INSTANCE / Création d'une instance ---
        public static TodocDatabase getInstance(Context context) {
            if (INSTANCE == null) {
                synchronized (TodocDatabase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                TodocDatabase.class, "MyDatabase.db")
                                .addCallback(prepopulateDatabase())
                                .build();

                        Log.d("TodocDatabase", "getInstance: Base créée");
                    }
                }
            }
            return INSTANCE;
        }

    // --- INSTANCE / Initialisation de l'instance avec une entrée ---
        private static Callback prepopulateDatabase(){
            return new Callback() {

                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);

                    ContentValues projectValues = new ContentValues();
                    for (Project project : getAllProjects()) {
                        projectValues.put("id", project.getId());
                        projectValues.put("name", project.getName());
                        projectValues.put("color", project.getColor());
                        db.insert("Project", OnConflictStrategy.IGNORE, projectValues);
                    }

                    ContentValues taskValues = new ContentValues();
                    taskValues.put("projectId", 3L);
                    taskValues.put("name", "Nettoyer les vitres");
                    taskValues.put("creationTimestamp", System.currentTimeMillis());

                    db.insert("Task", OnConflictStrategy.IGNORE, taskValues);
                }
            };
        }
}
