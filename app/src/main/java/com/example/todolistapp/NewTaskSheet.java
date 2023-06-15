package com.example.todolistapp;

import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.todolistapp.databinding.FragmentNewTaskSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.time.LocalTime;

public class NewTaskSheet extends BottomSheetDialogFragment {
    private final TaskItem taskItem;
    private FragmentNewTaskSheetBinding binding;
    private TaskViewModel taskViewModel;
    private LocalTime dueTime;

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dueTime = LocalTime.of(hourOfDay, minute);
            }
            updateTimeButtonText();
        }
    };

    public NewTaskSheet(TaskItem taskItem) {
        this.taskItem = taskItem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewTaskSheetBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        taskViewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);

        if (taskItem != null) {
            binding.taskTitle.setText("Edit Task");
            Editable editable = (Editable) Editable.Factory.getInstance();
            binding.name.setText(((Editable.Factory) editable).newEditable(taskItem.name));
            binding.desc.setText(((Editable.Factory) editable).newEditable(taskItem.desc));
            if (taskItem.getDueTime() != null) {
                dueTime = taskItem.getDueTime();
                updateTimeButtonText();
            }
        } else {
            binding.taskTitle.setText("New Task");
        }

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAction();
            }
        });

        binding.timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePicker();
            }
        });
    }

    private void openTimePicker() {
        if (dueTime == null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dueTime = LocalTime.now();
            }
        TimePickerDialog dialog = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dialog = new TimePickerDialog(requireActivity(), timeSetListener, dueTime.getHour(), dueTime.getMinute(), true);
        }
        dialog.setTitle("Task Due");
        dialog.show();
    }

    private void updateTimeButtonText() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.timePickerButton.setText(String.format("%02d:%02d", dueTime.getHour(), dueTime.getMinute()));
        }
    }

    private void saveAction() {
        String name = binding.name.getText().toString();
        String desc = binding.desc.getText().toString();
        if (taskItem == null) {
            TaskItem newTask = new TaskItem(name, desc, dueTime, null);
            taskViewModel.addTaskItem(newTask);
        } else {
            taskViewModel.updateTaskItem(taskItem.getId(), name, desc, dueTime);
        }
        binding.name.setText("");
        binding.desc.setText("");
        dismiss();
    }
}
