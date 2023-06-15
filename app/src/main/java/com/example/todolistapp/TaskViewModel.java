package com.example.todolistapp;

import android.os.Build;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class TaskViewModel extends ViewModel {
    private MutableLiveData<List<TaskItem>> taskItems = new MutableLiveData<>();

    public TaskViewModel() {
        taskItems.setValue(new ArrayList<>());
    }

    public MutableLiveData<List<TaskItem>> getTaskItems() {
        return taskItems;
    }

    public void addTaskItem(TaskItem newTask) {
        List<TaskItem> list = taskItems.getValue();
        list.add(newTask);
        taskItems.postValue(list);
    }

    public void updateTaskItem(UUID id, String name, String desc, LocalTime dueTime) {
        List<TaskItem> list = taskItems.getValue();
        TaskItem task = null;
        for (TaskItem item : list) {
            if (item.getId().equals(id)) {
                task = item;
                break;
            }
        }
        if (task != null) {
            task.name = name;
            task.desc = desc;
            task.dueTime = dueTime;
        }
        taskItems.postValue(list);
    }

    public void setCompleted(TaskItem taskItem) {
        List<TaskItem> list = taskItems.getValue();
        TaskItem task = null;
        for (TaskItem item : list) {
            if (item.getId().equals(taskItem.getId())) {
                task = item;
                break;
            }
        }
        if (task != null && task.getCompletedDate() == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                task.completedDate = LocalDate.now();
            }
        }
        taskItems.postValue(list);
    }
}

