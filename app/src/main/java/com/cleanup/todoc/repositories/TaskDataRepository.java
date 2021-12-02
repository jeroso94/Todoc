package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

/**
 * Created by JeroSo94 on 29/11/2021.
 */
public class TaskDataRepository {
    private final TaskDao mTaskDao;

    public TaskDataRepository(TaskDao taskDao) { this.mTaskDao = taskDao; }

    // --- GET ALL TASKS ---
    public LiveData<List<Task>> getAllTasks() { return this.mTaskDao.getAllTasks(); }

    // --- GET TASKS BY PROJECTID ---
    public LiveData<List<Task>> getTasksByProjectId(long projectId) { return this.mTaskDao.getTasksByProjectId(projectId); }

    // --- CREATE TASK ---
    public void insertTask(Task task) { mTaskDao.insertTask(task); }

    // --- UPDATE TASK ---
    public void updateTask(Task task){ mTaskDao.updateTask(task); }

    // --- DELETE TASK ---
    public void deleteTask(Task task) { mTaskDao.deleteTask(task); }

}
