package com.cleanup.todoc.injection;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.ui.MainViewModel;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.concurrent.Executor;

/**
 * Created by JeroSo94 on 30/11/2021.
 */
public class MainViewModelFactory implements ViewModelProvider.Factory {

    private final TaskDataRepository mTaskDataSource;
    private final ProjectDataRepository mProjectDataRepository;
    private final Executor mExecutor;


    public MainViewModelFactory(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor) {
        mTaskDataSource = taskDataSource;
        mProjectDataRepository = projectDataSource;
        mExecutor = executor;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(mTaskDataSource, mProjectDataRepository, mExecutor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
