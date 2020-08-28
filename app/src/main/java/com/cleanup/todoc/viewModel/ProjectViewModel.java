package com.cleanup.todoc.viewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.service.DI;

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
