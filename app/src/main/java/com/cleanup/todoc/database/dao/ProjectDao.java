package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    //@Insert (onConflict = OnConflictStrategy.REPLACE) //Overwrite a project who already exists with the same id from the project we want to insert
    //void createProject (Project project);


    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getAllProjects();

    @Insert
    long insertProject(Project project);

    @Update
    int updateProject(Project project);

    @Delete
    int deleteProject(Project project);
}
