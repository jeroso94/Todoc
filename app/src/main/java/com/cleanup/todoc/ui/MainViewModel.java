package com.cleanup.todoc.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by JeroSo94 on 29/11/2021.
 */

public class MainViewModel extends ViewModel {

    // REPOSITORIES
    private final TaskDataRepository mTaskDataSource;
    private final Executor mExecutor;

    public MainViewModel(TaskDataRepository taskDataSource, Executor executor){
        mTaskDataSource = taskDataSource;
        this.mExecutor = executor;
    }

    // -------------
    // FOR TASK
    // -------------

    public LiveData<List<Task>> getAllTasks() {
        return mTaskDataSource.getAllTasks();
    }

    public void insertTask(Task task) {
        mExecutor.execute(() -> mTaskDataSource.insertTask(task));
    }

    public void deleteTask(Task task) {
        mExecutor.execute(() -> mTaskDataSource.deleteTask(task));
    }
}
