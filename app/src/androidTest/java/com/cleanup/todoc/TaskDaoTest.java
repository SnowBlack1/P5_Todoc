package com.cleanup.todoc;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
 public class TaskDaoTest {

 private static long TASK_ID = 2L;
 private static long TASK_UPDATED_ID = 2L;
 private static long PROJECT_ID = 2L;
 private static Project PROJECT_TEST = new Project(PROJECT_ID, "TestProject1", 0xFFFFFF);
 private static Task TASK_TEST = new Task(TASK_ID, PROJECT_TEST.getId(), "TestTask", new Date().getTime());
 private TodocDatabase database;

 @Rule
 //swaps the background executor used by the Architecture
 //Components with a different one (background executor) which executes each task synchronously
 public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();



 @Before
 public void initDb() {
  this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
          TodocDatabase.class)
          .allowMainThreadQueries()
          .build();

  this.database.mProjectDao().insertProject(PROJECT_TEST);
 }

 @After
 public void closeDb() {
  database.close();
 }

 @Test
 public void getTasksWhenNoTaskInserted() throws InterruptedException {
  List<Task> tasks = LiveDataTestUtil.getValue(this.database.mTaskDao().getAllTasks());
  assertTrue(tasks.isEmpty());
 }

 @Test
 public void insertAndGetTask() throws InterruptedException {
  this.database.mTaskDao().insertTask(TASK_TEST);
  List<Task> tasks = LiveDataTestUtil.getValue(this.database.mTaskDao().getAllTasks());
  assertTrue(tasks.get(0).getId() == TASK_TEST.getId()
          && tasks.get(0).getName().equals(TASK_TEST.getName())
          && tasks.get(0).getProjectId() == TASK_TEST.getProjectId()
          && tasks.get(0).getCreationDate() == TASK_TEST.getCreationDate());
 }

 @Test
 public void insertAndUpdateTask() throws InterruptedException {
  this.database.mTaskDao().insertTask(TASK_TEST);
  TASK_TEST.setId(TASK_UPDATED_ID);
  this.database.mTaskDao().updateTask(TASK_TEST);
  List<Task> tasks = LiveDataTestUtil.getValue(this.database.mTaskDao().getAllTasks());
  assertEquals(TASK_UPDATED_ID, tasks.get(0).getId());
 }

 @Test
 public void insertAndDeleteTask() throws InterruptedException {
  this.database.mTaskDao().insertTask(TASK_TEST);
  this.database.mTaskDao().deleteTask(TASK_TEST);
  List<Task> tasks = LiveDataTestUtil.getValue(this.database.mTaskDao().getAllTasks());
  assertTrue(tasks.isEmpty());
 }
}
