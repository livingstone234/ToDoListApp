package com.example.todolistapp;

public interface TaskItemClickListener {
    void editTaskItem(TaskItem taskItem);
    void completeTaskItem(TaskItem taskItem);
}
