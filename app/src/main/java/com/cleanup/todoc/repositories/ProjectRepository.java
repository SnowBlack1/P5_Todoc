package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectRepository {

    private ProjectDao projectDao;

    //CONSTRUCTOR
    public ProjectRepository(Context context) {
        TodocDatabase db = TodocDatabase.getInstance(context);
        projectDao = (ProjectDao) getProjects(); // ??????
    }

    //--- GET ---
    public LiveData<Project> getProjectWithId(long projectId) {
        return this.projectDao.getProjectWithId(projectId);
    }

    public LiveData<List<Project>> getProjects() {
        return this.projectDao.getProjects();
    }

    //--- INSERT ---
    public void insertProject(Project project) {
        this.projectDao.insertProject(project);
    }

    //--- UPDATE ---
    public void updateProject(Project project) {
        this.projectDao.updateProject(project);
    }

    //--- DELETE ---
    public void deleteProject(long idProject) {
        this.projectDao.deleteProject(idProject);
    }
}
