package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.todolistapp.databinding.ActivityMainBinding;
import com.example.todolistapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements TaskItemClickListener {
    private ActivityMainBinding binding;
    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        binding.newTaskButton.setOnClickListener(v -> {
            NewTaskSheet newTaskSheet = new NewTaskSheet(null);
            newTaskSheet.show(getSupportFragmentManager(), "newTaskTag");
        });

        setRecyclerView();
    }

    private void setRecyclerView() {
        TaskItemClickListener mainActivity = this;
        taskViewModel.getTaskItems().observe(this, taskItems -> {
            binding.todoListRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            binding.todoListRecyclerView.setAdapter(new TaskItemAdapter(taskItems, mainActivity));
        });
    }

    @Override
    public void editTaskItem(TaskItem taskItem) {
        NewTaskSheet newTaskSheet = new NewTaskSheet(taskItem);
        newTaskSheet.show(getSupportFragmentManager(), "newTaskTag");
    }

    @Override
    public void completeTaskItem(TaskItem taskItem) {
        taskViewModel.setCompleted(taskItem);
    }
}
