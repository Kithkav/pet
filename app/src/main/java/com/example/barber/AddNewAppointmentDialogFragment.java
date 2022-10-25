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

public class AddNewAppointmentDialogFragment extends BottomSheetDialogFragment {
    private TextView dteDueDate;
    private EditText txtDescription;
    private Button btnSaveTask;
    private AppointmentViewActivity context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_appointment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dteDueDate = view.findViewById(R.id.dteDueDate_appointment);
        txtDescription = view.findViewById(R.id.txtInDescription_appointment);
        btnSaveTask = view.findViewById(R.id.btnSave_appointment);

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
                    Toast.makeText(context, "Empty appointment not Allowed !!", Toast.LENGTH_SHORT).show();
                } else {
                    Appointment appointment = new Appointment(txtDescription.getText().toString(), calendar.getTime());
                    context.addAppointmentToDatabase(appointment);
                    dismiss();
                }

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (AppointmentViewActivity) context;
    }
}
