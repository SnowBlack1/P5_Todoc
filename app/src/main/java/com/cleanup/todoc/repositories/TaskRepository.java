package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskRepository {

    private TaskDao taskDao;
    private Executor mExecutor;

    //CONSTRUCTOR
    public TaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
        this.mExecutor = Executors.newSingleThreadExecutor();
    }

    //GET
    public LiveData<List<Task>> getAllTasks() {
        return this.taskDao.getAllTasks();
    }

    //INSERT
    public void insertTask(Task task) {
        this.mExecutor.execute(() -> taskDao.insertTask(task));
    }

    //UPDATE
    public void updateTask(Task task) {
        this.mExecutor.execute(() -> taskDao.updateTask(task));
    }

    //DELETE
    public void deleteTask(Task task) {
        this.mExecutor.execute(() -> taskDao.deleteTask(task));
    }
}
