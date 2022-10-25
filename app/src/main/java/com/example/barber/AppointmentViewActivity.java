package com.example.barber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AppointmentViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton mFab;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_view);

        mFab = findViewById(R.id.floatingActionButton_appointment);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recyclerView_appointment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AppointmentViewActivity.this));
        this.refreshRecyclerView();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddNewAppointmentDialogFragment().show(getSupportFragmentManager(), "PET");
            }
        });
    }

    public void addAppointmentToDatabase(Appointment appointment) {
        db.collection("users").document(mAuth.getCurrentUser().getEmail()).collection("appointments").add(appointment);
        Toast.makeText(AppointmentViewActivity.this, "Appointment added successfully", Toast.LENGTH_SHORT).show();
        this.refreshRecyclerView();
    }

    private void refreshRecyclerView() {
        db.collection("users").document(mAuth.getCurrentUser().getEmail()).collection("appointments").get()
            .addOnSuccessListener(querySnapshot -> {
                AppointmentViewAdapter adapter = new AppointmentViewAdapter(querySnapshot.getDocuments());
                recyclerView.setAdapter(adapter);
            });
    }
}