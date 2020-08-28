package com.cleanup.todoc;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
 public class ProjectDaoTest {
    private static long PROJECT_ID = 2L;
    private static long PROJECT_UPDATED_ID = 2L; //Given id when project updated
    private static Project PROJECT_TEST = new Project(PROJECT_ID, "TestProject", 0xFFFFFF);
    private TodocDatabase database;

    @Rule
    //swaps the background executor used by the Architecture
    //Components with a different one which executes each task synchronously
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void getProjectsWhenNoProjectInserted() throws InterruptedException {
        List<Project> projects = LiveDataTestUtil.getValue(this.database.mProjectDao().getAllProjects());
        assertTrue(projects.isEmpty());
    }

    @Test
    public void insertAndGetProject() throws InterruptedException {
        this.database.mProjectDao().insertProject(PROJECT_TEST);
        List<Project> projects = LiveDataTestUtil.getValue(this.database.mProjectDao().getAllProjects());
        assertTrue(projects.get(0).getId() == PROJECT_TEST.getId()
                && projects.get(0).getName().equals(PROJECT_TEST.getName())
                && projects.get(0).getColor() == PROJECT_TEST.getColor());
    }

    @Test
    public void insertAndUpdateProject() throws InterruptedException {
        this.database.mProjectDao().insertProject(PROJECT_TEST);
        PROJECT_TEST.setId(PROJECT_UPDATED_ID);
        this.database.mProjectDao().updateProject(PROJECT_TEST);
        List<Project> projects = LiveDataTestUtil.getValue(this.database.mProjectDao().getAllProjects());
        assertEquals(PROJECT_UPDATED_ID, projects.get(0).getId());
    }

    @Test
    public void insertAndDeleteProject() throws InterruptedException {
        this.database.mProjectDao().insertProject(PROJECT_TEST);
        this.database.mProjectDao().deleteProject(PROJECT_TEST);
        List<Project> projects = LiveDataTestUtil.getValue(this.database.mProjectDao().getAllProjects());
        assertTrue(projects.isEmpty());
    }
}
