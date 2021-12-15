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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by JeroSo94 on 24/11/2021.
 */
@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    // FOR DATA
    private TodocDatabase mDatabase;

    // DATA SET FOR TEST
    private static final long PROJECT_ID = 3L;
    private static final Project PROJECT_DEMO = new Project(PROJECT_ID, "Circus", 0xFFA3CED2);

    private static final String TASK_NAME = "Vider le lave vaisselle";
    private static final Task NEW_TASK = new Task(999999L, PROJECT_ID, TASK_NAME, System.currentTimeMillis());

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }


    /**
     * PROJECT
     * 12/14 17:49:50: Test passed ! - insertAndGetProject()
     *
     * @throws InterruptedException
     */
    @Test
    public void insertAndGetProject() throws InterruptedException {
        // WHEN : Adding a new project
        mDatabase.mProjectDao().createProject(PROJECT_DEMO);
        Project project = LiveDataTestUtil.getValue(mDatabase.mProjectDao().getProjectById(PROJECT_ID));

        // THEN
        assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() == PROJECT_ID);
    }



    /**
     * TASK
     */
    /** 12/15 15:39:54: Launching 'getTasksWhenNoTask...()' - Test passed !
     *
     * @throws InterruptedException
     */
    @Test
    public void getTasksWhenNoTaskInserted() throws InterruptedException {
        // WHEN
        List<Task> tasks = LiveDataTestUtil.getValue(mDatabase.mTaskDao().getAllTasks());

        // THEN
        assertTrue(tasks.isEmpty());
    }


    /** 12/15 15:49:13: Launching 'insertAndGetTasks()' - Test passed !
     *
     * @throws InterruptedException
     */
    // Todo: Ajouter 2 tâches supplémentaires dans ce test
    @Test
    public void insertAndGetTasks() throws InterruptedException {
        // GIVEN : Adding a new project to satisfy Foreign Key constraint
        mDatabase.mProjectDao().createProject(PROJECT_DEMO);

        // WHEN
        // Adding demo tasks
        this.mDatabase.mTaskDao().insertTask(NEW_TASK);
        List<Task> tasks = LiveDataTestUtil.getValue(mDatabase.mTaskDao().getAllTasks());

        // THEN
        assertEquals(1, tasks.size());
    }

    /** 12/15 16:08:07: Launching 'insertAndDeleteTask()' - Test passed !
     *
     * @throws InterruptedException
     */
    @Test
    public void insertAndDeleteTask() throws InterruptedException {
        // GIVEN : Adding a new project to satisfy Foreign Key constraint
        mDatabase.mProjectDao().createProject(PROJECT_DEMO);

        // WHEN
        // Adding demo task & Get it
        this.mDatabase.mTaskDao().insertTask(NEW_TASK);
        Task taskAdded = LiveDataTestUtil.getValue(mDatabase.mTaskDao().getTasksByProjectId(PROJECT_ID)).get(0);
        // Delete the demo task
        this.mDatabase.mTaskDao().deleteTask(taskAdded);
        List<Task> tasks = LiveDataTestUtil.getValue(mDatabase.mTaskDao().getTasksByProjectId(PROJECT_ID));

        //THEN
        assertTrue(tasks.isEmpty());
    }
}
