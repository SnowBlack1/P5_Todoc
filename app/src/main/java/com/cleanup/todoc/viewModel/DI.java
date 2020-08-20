package com.cleanup.todoc.viewModel;

import android.content.Context;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DI {

    public static TaskRepository provideTaskDataSource(Context context){
        TodocDatabase db = TodocDatabase.getInstance(context);
        return new TaskRepository(db.mTaskDao());
    }

    public static ProjectRepository provideProjectDataSource (Context context) {
        TodocDatabase db = TodocDatabase.getInstance(context);
        return new ProjectRepository(db.mProjectDao()); //constructeur ProjectRepository veut 1 context
    }

    public static Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    //public static ViewModelFactory provideViewModelFactory(Context context){
    //    ProjectRepository dataSourceProject = provideProjectDataSource(context);
    //    TaskRepository dataSourceTask = provideTaskDataSource(context);
    //    Executor executor = provideExecutor();
    //    return new ViewModelFactory(dataSourceTask, dataSourceProject,executor);
    //}

}
