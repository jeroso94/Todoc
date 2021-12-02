package com.cleanup.todoc;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.database.dao.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by JeroSo94 on 24/11/2021.
 */
@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    // FOR DATA
    private TodocDatabase database;

    // DATA SET FOR TEST
    private static long PROJECT_ID = 3L;

    private static String TASK_NAME = "Vider le lave vaisselle";
    private static Task NEW_TASK = new Task(999999L, PROJECT_ID, TASK_NAME, System.currentTimeMillis());

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    /**
     * PROJECT
     */
    @Test
    public void getProject() throws InterruptedException {
        // TEST
        Project project = LiveDataTestUtil.getValue(this.database.mProjectDao().getProjectById(PROJECT_ID));
        assertTrue(project.getName().equals(project.getId() == PROJECT_ID));
    }

    /**
     * TASK
     */
    @Test
    public void getTasksWhenNoTaskInserted() throws InterruptedException {
        // TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.mTaskDao().getAllTasks());
        assertTrue(tasks.isEmpty());
    }

    // Todo: Ajouter 2 tâches supplémentaires dans ce test
    @Test
    public void insertAndGetTasks() throws InterruptedException {
        // BEFORE : Adding demo tasks

        this.database.mTaskDao().insertTask(NEW_TASK);

        // TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.mTaskDao().getAllTasks());
        assertTrue(tasks.size() == 1);
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException {
        // BEFORE : Adding demo task. Next, get the item added & delete it.
        this.database.mTaskDao().insertTask(NEW_TASK);
        Task taskAdded = LiveDataTestUtil.getValue(this.database.mTaskDao().getTasksByProjectId(PROJECT_ID)).get(0);
        this.database.mTaskDao().deleteTask(taskAdded);

        //TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.mTaskDao().getTasksByProjectId(PROJECT_ID));
        assertTrue(tasks.isEmpty());
    }
}
