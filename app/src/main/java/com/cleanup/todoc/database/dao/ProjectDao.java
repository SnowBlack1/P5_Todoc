package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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
