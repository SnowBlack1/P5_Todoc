package com.cleanup.todoc.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.viewModel.DI;
import com.cleanup.todoc.viewModel.ProjectViewModel;
import com.cleanup.todoc.viewModel.TaskViewModel;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <p>Home activity of the application which is displayed when the user opens the app.</p>
 * <p>Displays the list of tasks.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class MainActivity extends AppCompatActivity implements TasksAdapter.DeleteTaskListener {

    private TodocDatabase mDatabase;
    private ProjectDao mProjectDao;
    private TaskDao mTaskDao;
    //private TodocViewModel mViewModel;
    private List<Project> mProjectList;

    /**
     * List of all projects available in the application
     */
    //private final Project[] allProjects = Project.getAllProjects();
    private ArrayList<Project> allProjects = new ArrayList<>();
    /**
     * List of all current tasks of the application
     */
    @NonNull
    //private List<Task> tasks = new ArrayList<>();
    private ArrayList<Task> tasks = new ArrayList<>();

    /**
     * The adapter which handles the list of tasks
     */
    private final TasksAdapter adapter = new TasksAdapter(tasks, allProjects, this);
    //private TasksAdapter adapter;

    /**
     * The ViewModel which handles the tasks repository
     */
    private TaskViewModel taskViewModel;

    /**
     * The ViewModel which handles the projects repository
     */
    private ProjectViewModel projectViewModel;

    /**
     * The sort method to be used to display tasks
     */
    @NonNull
    private SortMethod sortMethod = SortMethod.NONE;

    /**
     * Dialog to create a new task
     */
    @Nullable
    public AlertDialog dialog = null;

    /**
     * EditText that allows user to set the name of a task
     */
    @Nullable
    private EditText dialogEditText = null;

    /**
     * Spinner that allows the user to associate a project to a task
     */
    @Nullable
    private Spinner dialogSpinner = null;

    /**
     * The RecyclerView which displays the list of tasks
     */
    // Suppress warning is safe because variable is initialized in onCreate
    @SuppressWarnings("NullableProblems")
    @NonNull
    private RecyclerView listTasks;

    /**
     * The TextView displaying the empty state
     */
    // Suppress warning is safe because variable is initialized in onCreate
    @SuppressWarnings("NullableProblems")
    @NonNull
    private TextView lblNoTasks;

    //public static final String KEY_SORT_METHOD = "KEY_SORT_METHOD";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Access to the ViewModel
        //mViewModel = ViewModelProviders.of(this).get(TodocViewModel.class);

        //configureViewModel();
        //getTasks();

        listTasks = findViewById(R.id.list_tasks);
        lblNoTasks = findViewById(R.id.lbl_no_task);

        initData();
        //checkSavedInstanceState(savedInstanceState);

        listTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listTasks.setAdapter(adapter);

        findViewById(R.id.fab_add_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTaskDialog();
            }
        });

        // mViewModel.getProjects().observe(this, projects -> {
        // mProjectList = projects;
        //});
    }

    //@Override
    //public void onSaveInstanceState(Bundle savedInstanceState) {
    //    savedInstanceState.putInt(KEY_SORT_METHOD, sortMethod.getMode());
    //    super.onSaveInstanceState(savedInstanceState);
    //}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filter_alphabetical) {
            sortMethod = SortMethod.ALPHABETICAL;
        } else if (id == R.id.filter_alphabetical_inverted) {
            sortMethod = SortMethod.ALPHABETICAL_INVERTED;
        } else if (id == R.id.filter_oldest_first) {
            sortMethod = SortMethod.OLD_FIRST;
        } else if (id == R.id.filter_recent_first) {
            sortMethod = SortMethod.RECENT_FIRST;
        }
        updateTasks();

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDeleteTask(Task task) {
        //tasks.remove(task);
        //deleteTask(task);
        this.taskViewModel.delete(task);
        updateTasks();
    }

    /**
     * Initiates the Tasks and Projects data
     */
    private void initData() {
        this.taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        this.taskViewModel.getAllTasks().observe(this, t -> {
            tasks.clear();
            tasks.addAll(t);
            updateTasks();
        });

        this.projectViewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);
        this.projectViewModel.getAllProjects().observe(this, p -> {
            allProjects.clear();
            allProjects.addAll(p);
        });
    }

    //private void checkSavedInstanceState(@Nullable Bundle savedInstanceState) {
    //    if (savedInstanceState != null) {
    //        switch (savedInstanceState.getInt(KEY_SORT_METHOD)) {
    //            case 1:
    //                sortMethod = SortMethod.ALPHABETICAL;
    //                break;
    //            case 2:
    //                sortMethod = SortMethod.ALPHABETICAL_INVERTED;
    //                break;
    //            case 3:
    //                sortMethod = SortMethod.RECENT_FIRST;
    //                break;
    //            case 4:
    //                sortMethod = SortMethod.OLD_FIRST;
    //                break;
    //            default:
    //                sortMethod = SortMethod.NONE;
    //                break;
    //        }
    //        updateTasks();
    //        savedInstanceState.clear();
    //    }
    //}

    /**
     * Called when the user clicks on the positive button of the Create Task Dialog.
     *
     * @param dialogInterface the current displayed dialog
     */
    private void onPositiveButtonClick(DialogInterface dialogInterface) {
        // If dialog is open
        if (dialogEditText != null && dialogSpinner != null) {
            // Get the name of the task
            String taskName = dialogEditText.getText().toString();

            // Get the selected project to be associated to the task
            Project taskProject = null;
            if (dialogSpinner.getSelectedItem() instanceof Project) {
                taskProject = (Project) dialogSpinner.getSelectedItem();
            }

            // If a name has not been set
            if (taskName.trim().isEmpty()) {
                dialogEditText.setError(getString(R.string.empty_task_name));
            }
            // If both project and name of the task have been set
            else if (taskProject != null) {
                // TODO: Replace this by id of persisted task
                //long id = (long) (Math.random() * 50000);


                Task task = new Task(
                        taskProject.getId(),
                        taskName,
                        new Date().getTime()
                );

                addTask(task);

                dialogInterface.dismiss();
            }
            // If name has been set, but project has not been set (this should never occur)
            else {
                dialogInterface.dismiss();
            }
        }
        // If dialog is already closed
        else {
            dialogInterface.dismiss();
        }
    }

    /**
     * Shows the Dialog for adding a Task
     */
    private void showAddTaskDialog() {
        final AlertDialog dialog = getAddTaskDialog();

        dialog.show();

        dialogEditText = dialog.findViewById(R.id.txt_task_name);
        dialogSpinner = dialog.findViewById(R.id.project_spinner);

        populateDialogSpinner();
    }

    /**
     * Adds the given task to the list of created tasks.
     *
     * @param task the task to be added to the list
     */
    private void addTask(@NonNull Task task) {
        //tasks.add(task);
        //updateTasks();
        this.taskViewModel.insert(task);
        updateTasks();
    }

    /**
     * Updates the list of tasks in the UI
     */
    private void updateTasks() {
        if (tasks.size() == 0) {
            lblNoTasks.setVisibility(View.VISIBLE);
            listTasks.setVisibility(View.GONE);
        } else {
            lblNoTasks.setVisibility(View.GONE);
            listTasks.setVisibility(View.VISIBLE);
            switch (sortMethod) {
                case ALPHABETICAL:
                    Collections.sort(tasks, new Task.TaskAZComparator());
                    break;
                case ALPHABETICAL_INVERTED:
                    Collections.sort(tasks, new Task.TaskZAComparator());
                    break;
                case RECENT_FIRST:
                    Collections.sort(tasks, new Task.TaskRecentComparator());
                    break;
                case OLD_FIRST:
                    Collections.sort(tasks, new Task.TaskOldComparator());
                    break;

            }
            adapter.updateTasks(tasks);
        }
    }

    //private void deleteTask(Task task){
    //    this.mViewModel.deleteTask(task.getId());
    //}
//
    //private void configureViewModel(){
    //    ViewModelFactory mViewModelFactory = DI.provideViewModelFactory(this);
    //    this.mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(TodocViewModel.class);
    //    this.mViewModel.initProjects();
    //}
//
    //private void getTasks(){
    //    this.mViewModel.getTasks().observe(this, this::updateTasksList);
    //    this.mViewModel.getTasks().observe(this, this::updateTasks);
    //}
//
    //private void updateTasksList(ArrayList<Task> tasks){
    //    this.tasks = tasks;
    //    this.adapter.updateTasks(tasks);
    //}

    /**
     * Returns the dialog allowing the user to create a new task.
     *
     * @return the dialog allowing the user to create a new task
     */
    @NonNull
    private AlertDialog getAddTaskDialog() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.Dialog);

        alertBuilder.setTitle(R.string.add_task);
        alertBuilder.setView(R.layout.dialog_add_task);
        alertBuilder.setPositiveButton(R.string.add, null);
        alertBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialogEditText = null;
                dialogSpinner = null;
                dialog = null;
            }
        });

        dialog = alertBuilder.create();

        // This instead of listener to positive button in order to avoid automatic dismiss
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        onPositiveButtonClick(dialog);
                    }
                });
            }
        });

        return dialog;
    }

    /**
     * Sets the data of the Spinner with projects to associate to a new task
     */
    private void populateDialogSpinner() {
        final ArrayAdapter<Project> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allProjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (dialogSpinner != null) {
            dialogSpinner.setAdapter(adapter);
        }
    }

    /**
     * List of all possible sort methods for task
     */
    private enum SortMethod {
        /**
         * Sort alphabetical by name
         */
        ALPHABETICAL,
        /**
         * Inverted sort alphabetical by name
         */
        ALPHABETICAL_INVERTED,
        /**
         * Lastly created first
         */
        RECENT_FIRST,
        /**
         * First created first
         */
        OLD_FIRST,
        /**
         * No sort
         */
        NONE
    }
}
