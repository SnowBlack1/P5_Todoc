package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
 public interface TaskDao {

 //@Query("SELECT * FROM Task WHERE projectId = :projectId")
 //LiveData<List<Task>> getTasksWithProjectId (long projectId); // get tasks list (list type = LiveData)

 @Query("SELECT * FROM Task")
 LiveData<List<Task>> getAllTasks();

 @Insert
 long insertTask(Task task); //create a new task + Room generates an id for us

 @Update
 int updateTask (Task task); // update a task + need an id to Room find it in DB & update it

 @Delete
 int deleteTask(Task task);






}
