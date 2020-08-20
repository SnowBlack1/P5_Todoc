package com.cleanup.todoc.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;

//extends AndroidViewModel
//public class TodocViewModel extends ViewModel  {
//
//    // REPOSITORIES
//    private  TaskRepository taskRepoDataSource;
//    private  ProjectRepository projectRepoDataSource;
//    private Executor executor;
//
//    //DATA
//    private LiveData<List<Project>> currentProjects;
//
//    public TodocViewModel(TaskRepository taskRepoDataSource, ProjectRepository projectRepoDataSource, Executor executor) {
//        this.taskRepoDataSource = taskRepoDataSource;
//        this.projectRepoDataSource = projectRepoDataSource;
//        this.executor = executor;
//    }
//
//    //public TodocViewModel(@NonNull Application application) {
//    //    super(application);
//    //    taskDataSource = DI.provideTaskDataSource(application);
//    //    projectDataSource = DI.provideProjectDataSource(application);
////
//    //}
//
//    public void initProjects(){
//        if (this.currentProjects != null){
//            return;
//        }
//        currentProjects = projectRepoDataSource.getProjects();
//    }
//
//
//    // FOR PROJECT
//    public LiveData<List<Project>> getProjects() {
//        return projectRepoDataSource.getProjects();
//    }
//
//    // FOR TASK
//    public LiveData<List<Task>> getTasks() {
//        return taskRepoDataSource.getTask();
//    }
//
//    public void createTask(final Task task) {
//         taskRepoDataSource.createTask(task);
//    }
//
//    public void deleteTask(long taskId) {
//        taskRepoDataSource.deleteTask(taskId);
//    }
//
//    public void updateTask(Task task) {
//        taskRepoDataSource.updateTask(task);
//    }
//}
