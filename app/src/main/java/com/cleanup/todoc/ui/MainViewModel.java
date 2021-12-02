package com.cleanup.todoc.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by JeroSo94 on 29/11/2021.
 */

public class MainViewModel extends ViewModel {

    // REPOSITORIES
    private final TaskDataRepository mTaskDataSource;
    private final ProjectDataRepository mProjectDataSource;
    private final Executor mExecutor;

    public MainViewModel(TaskDataRepository taskDataSource,ProjectDataRepository projectDataSource, Executor executor){
        mTaskDataSource = taskDataSource;
        mProjectDataSource = projectDataSource;
        this.mExecutor = executor;
    }

    // -------------
    // FOR TASK
    // -------------

    public LiveData<List<Task>> getAllTasks() {
        return mTaskDataSource.getAllTasks();
    }

    public void insertTask(Task task) {
        mExecutor.execute(() -> {
            mTaskDataSource.insertTask(task);
        });
    }

    public void deleteTask(Task task) {
        mExecutor.execute(() -> {
            mTaskDataSource.deleteTask(task);
        });
    }

/**
* IMPLEMENTATION SOURCée 140-ANDFUN AAC et 141-ANDFUN AAC
 public class MainViewModel extends AndroidViewModel {
    private LiveData<List<Task>> tasks;

    public MainViewModel (Application application){
        super(application);
        TodocDatabase mDatabase = TodocDatabase.getInstance(this.getApplication());
        Log.d(TAG, "MainViewModel: Actively retrieving the tasks from the database");
        tasks = mDatabase.mTaskDao().getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return tasks;
    }
*/

/**
 * IMPLEMENTATION SOURCée OPENCLASSROOM PARTIE 3 - CHAPT 2
    // REPOSITORIES
    private final TaskDataRepository mTaskDataSource;
    private final ProjectDataRepository mProjectDatasource;
    private final Executor mExecutor;

    // DATA
    @Nullable
    private LiveData<Task> mTaskLiveData;
    private LiveData<Project> mProjectLiveData;

    // CONSTRUCTOR
    public MainViewModel(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor) {
        this.mTaskDataSource = taskDataSource;
        this.mProjectDatasource = projectDataSource;
        this.mExecutor = executor;
    }

    // -------------
    // FOR PROJECT
    // -------------

    public LiveData<Project> getProjectById(long projectId) { return mProjectDatasource.getProjectById(projectId);  }


    // -------------
    // FOR TASK
    // -------------

    public LiveData<List<Task>> getAllTasks() {
        return mTaskDataSource.getAllTasks();
    }

    public LiveData<List<Task>> getTasksByProjectId(long projectId) {
        return mTaskDataSource.getTasksByProjectId(projectId);
    }

    public void insertTask(Task task) {
        mExecutor.execute(() -> {
            mTaskDataSource.insertTask(task);
        });
    }

    public void deleteTask(Task task) {
        mExecutor.execute(() -> {
            mTaskDataSource.deleteTask(task);
        });
    }

    public void updateTask(Task task) {
        mExecutor.execute(() -> {
            mTaskDataSource.updateTask(task);
        });
    }

 */
}
