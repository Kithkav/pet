package com.example.barber;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class AddNewTaskDialogFragment extends BottomSheetDialogFragment {
    public static final String TAG = "AddNewTaskDialogFragment";

    private TextView dteDueDate;
    private EditText txtDescription;
    private Button btnSaveTask;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dteDueDate = view.findViewById(R.id.set_due_tv);
        txtDescription = view.findViewById(R.id.task_edittext);
        btnSaveTask = view.findViewById(R.id.save_btn);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Calendar calendar = Calendar.getInstance();
        int MONTH = calendar.get(Calendar.MONTH);
        int YEAR = calendar.get(Calendar.YEAR);
        int DAY = calendar.get(Calendar.DATE);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                dteDueDate.setText(dayOfMonth + "/" + month + "/" + year);
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        }, YEAR, MONTH, DAY);

        dteDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(txtDescription.getText())) {
                    Toast.makeText(context, "Empty task not Allowed !!", Toast.LENGTH_SHORT).show();
                } else {
                    Task newTask = new Task(txtDescription.getText().toString(), calendar.getTime());
                    if (context instanceof ToDoTaskViewActivity) {
                        ((ToDoTaskViewActivity) context).addTaskToDatabase(newTask);
                    } else if (context instanceof AppointmentViewActivity) {
                        ((AppointmentViewActivity) context).addTaskToDatabase(newTask);
                    }

                    dismiss();
                }

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
