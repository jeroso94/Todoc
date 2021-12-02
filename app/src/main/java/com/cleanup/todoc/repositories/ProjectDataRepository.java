package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

/**
 * Created by JeroSo94 on 29/11/2021.
 */
public class ProjectDataRepository {
    private final ProjectDao mProjectDao;

    public ProjectDataRepository(ProjectDao projectDao) { this.mProjectDao = projectDao; }

    // --- GET PROJECT BY ID ---
    public LiveData<Project> getProjectById(long projectId) { return this.mProjectDao.getProjectById(projectId); }
}
