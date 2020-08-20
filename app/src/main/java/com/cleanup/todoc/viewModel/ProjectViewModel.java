package com.cleanup.todoc.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.repositories.ProjectRepository;

import java.util.List;

public class ProjectViewModel extends AndroidViewModel {

    private ProjectRepository mProjectRepository;

    public ProjectViewModel(@NonNull Application application) {
        super(application);
        this.mProjectRepository = DI.provideProjectDataSource(application.getApplicationContext());
    }

    public void insert(Project project) {
        this.mProjectRepository.insertProject(project);
    }

    public void update(Project project) {
        this.mProjectRepository.updateProject(project);
    }

    public void delete(Project project) {
        this.mProjectRepository.deleteProject(project);
    }

    public LiveData<List<Project>> getAllProjects() {
        return this.mProjectRepository.getAllProjects();
    }
}
