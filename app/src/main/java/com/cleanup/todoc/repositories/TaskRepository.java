package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {

    private TaskDao taskDao;

    //CONSTRUCTOR
    public TaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    //GET
    public LiveData<List<Task>> getTaskWithProjectId(long projectId) {
        return this.taskDao.getTasksWithProjectId(projectId);
    }

    public LiveData<List<Task>> getTask() {
        return this.taskDao.getTasks();
    }
    // --- CREATE ---
    public void createTask(Task task) {
        this.taskDao.insertTask(task);
    }

    //INSERT
    public void insertTask(Task task) {
        this.taskDao.insertTask(task);
    }

    //UPDATE
    public void updateTask(Task task) {
        this.taskDao.updateTask(task);
    }

    //DELETE
    public void deleteTask(long taskId) {
        this.taskDao.deleteTask(taskId);
    }
}
