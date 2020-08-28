package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProjectRepository {

    private ProjectDao projectDao;
    private Executor mExecutor;

    public ProjectRepository(ProjectDao dao) {
        this.projectDao = dao;
        this.mExecutor = Executors.newSingleThreadExecutor();
    }

    //CONSTRUCTOR
    //public ProjectRepository(Context context) {
        //odocDatabase db = TodocDatabase.getInstance(context);
        //projectDao = db.mProjectDao(); // j'ai pas de getDao dans projectRepository
    //}

    //--- GET ---
    public  LiveData<List<Project>> getAllProjects() {
        return this.projectDao.getAllProjects();
    }

    //--- INSERT ---
    public void insertProject(Project project) {
        this.mExecutor.execute(() -> projectDao.insertProject(project));
    }

    //--- UPDATE ---
    public void updateProject(Project project) {
        this.mExecutor.execute(() -> projectDao.updateProject(project));
    }

    //--- DELETE ---
    public void deleteProject(Project project) {
        this.mExecutor.execute(() -> projectDao.deleteProject(project));
    }
}
