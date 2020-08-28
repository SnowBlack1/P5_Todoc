package com.cleanup.todoc.viewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.TaskRepository;
import com.cleanup.todoc.service.DI;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository mTaskRepository;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        this.mTaskRepository = DI.provideTaskDataSource(application.getApplicationContext());
    }

    public void insert( Task task) {
        this.mTaskRepository.insertTask(task);
    }

    public void update( Task task) {
        this.mTaskRepository.updateTask(task);
    }

    public void delete(final Task task) {
        this.mTaskRepository.deleteTask(task);
    }

    public LiveData<List<Task>> getAllTasks() {
        return this.mTaskRepository.getAllTasks();
    }
}
