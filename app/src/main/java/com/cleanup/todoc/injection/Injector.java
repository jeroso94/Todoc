package com.cleanup.todoc.injection;

import android.content.Context;

import com.cleanup.todoc.database.dao.TodocDatabase;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by JeroSo94 on 30/11/2021.
 */
public class Injector {

    public static TaskDataRepository provideTaskDataSource(Context context) {
        TodocDatabase mDB = TodocDatabase.getInstance(context);
        return new TaskDataRepository(mDB.mTaskDao());
    }

    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }


    public static MainViewModelFactory provideMainViewModelFactory(Context context) {
        TaskDataRepository mTaskDataSource = provideTaskDataSource(context);
        Executor executor = provideExecutor();

        return new MainViewModelFactory(mTaskDataSource, executor);
    }
}
