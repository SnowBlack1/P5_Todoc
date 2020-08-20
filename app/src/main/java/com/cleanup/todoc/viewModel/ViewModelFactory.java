package com.cleanup.todoc.viewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.concurrent.Executor;

//public class ViewModelFactory implements ViewModelProvider.Factory{
//
//    private final TaskRepository taskDataSource;
//    private final ProjectRepository projectDataSource;
//    private final Executor executor;
//
//    public ViewModelFactory(TaskRepository taskDataSource, ProjectRepository projectDataSource, Executor executor) {
//        this.taskDataSource = taskDataSource;
//        this.projectDataSource = projectDataSource;
//        this.executor = executor;
//    }
//
//
//    @NonNull
//    @Override
//    public <T extends ViewModel> T create(Class<T> modelClass) {
//        if (modelClass.isAssignableFrom(TodocViewModel.class)) {
//            return (T) new TodocViewModel(taskDataSource, projectDataSource, executor);
//        }
//        throw new IllegalArgumentException("Unknown ViewModel class");
//    }
//
//}
