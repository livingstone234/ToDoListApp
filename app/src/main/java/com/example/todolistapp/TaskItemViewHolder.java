package com.example.todolistapp;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolistapp.databinding.TaskItemCellBinding;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TaskItemViewHolder extends RecyclerView.ViewHolder {
    private Context context;
    private TaskItemCellBinding binding;
    private TaskItemClickListener clickListener;
    private DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    public TaskItemViewHolder(Context context, TaskItemCellBinding binding, TaskItemClickListener clickListener) {
        super(binding.getRoot());
        this.context = context;
        this.binding = binding;
        this.clickListener = clickListener;
    }

    public void bindTaskItem(TaskItem taskItem) {
        binding.name.setText(taskItem.name);

        if (taskItem.isCompleted()) {
            binding.name.setPaintFlags(binding.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            binding.dueTime.setPaintFlags(binding.dueTime.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        binding.completeButton.setImageResource(taskItem.imageResource());
        binding.completeButton.setColorFilter(taskItem.imageColor(context));

        binding.completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.completeTaskItem(taskItem);
            }
        });

        binding.taskCellContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.editTaskItem(taskItem);
            }
        });

        if (taskItem.dueTime != null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.dueTime.setText(timeFormat.format(taskItem.dueTime));
            }
        else
            binding.dueTime.setText("");
    }
}

