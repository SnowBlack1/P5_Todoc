package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAllTasks();

    /**
     * Creates a new task & Room generates an id for us (autoGenerate = true)
     */
    @Insert
    long insertTask(Task task);

    /**
     * Updates a task.
     * Need an id so that Room finds it in the database & updates it.
     */
    @Update
    int updateTask(Task task);

    @Delete
    int deleteTask(Task task);


}
