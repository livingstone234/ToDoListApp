package com.example.todolistapp;

import android.content.Context;
import androidx.core.content.ContextCompat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class TaskItem {
    String name;
    String desc;
    LocalTime dueTime;
    LocalDate completedDate;
    private UUID id;

    public TaskItem(String name, String desc, LocalTime dueTime, LocalDate completedDate) {
        this.name = name;
        this.desc = desc;
        this.dueTime = dueTime;
        this.completedDate = completedDate;
        this.id = UUID.randomUUID();
    }

    public boolean isCompleted() {
        return completedDate != null;
    }

    public int imageResource() {
        return isCompleted() ? R.drawable.checked_24 : R.drawable.unchecked_24;
    }

    public int imageColor(Context context) {
        return isCompleted() ? purple(context) : black(context);
    }

    private int purple(Context context) {
        return ContextCompat.getColor(context, R.color.purple_500);
    }

    private int black(Context context) {
        return ContextCompat.getColor(context, R.color.black);
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public LocalTime getDueTime() {
        return dueTime;
    }

    public LocalDate getCompletedDate() {
        return completedDate;
    }

    public UUID getId() {
        return id;
    }
}

