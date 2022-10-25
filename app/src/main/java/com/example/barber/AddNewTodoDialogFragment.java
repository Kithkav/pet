package com.example.barber;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTodoDialogFragment extends BottomSheetDialogFragment {
    private EditText txtDescription;
    private Button btnSaveTask;
    private TodoTaskViewActivity context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_todo_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtDescription = view.findViewById(R.id.txtInDescription_todo);
        btnSaveTask = view.findViewById(R.id.btnSave_todo);

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(txtDescription.getText())) {
                    Toast.makeText(context, "Empty appointment not Allowed !!", Toast.LENGTH_SHORT).show();
                } else {
                    TodoTask todoTask = new TodoTask(txtDescription.getText().toString());
                    context.addTodoTaskToDatabase(todoTask);
                    dismiss();
                }

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (TodoTaskViewActivity) context;
    }
}
