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

public class TodoTaskViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton mFab;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_view);

        mFab = findViewById(R.id.floatingActionButton_todo);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recyclerView_todo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(TodoTaskViewActivity.this));
        this.refreshRecyclerView();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddNewTodoDialogFragment().show(getSupportFragmentManager(), "PET");
            }
        });
    }

    public void addTodoTaskToDatabase(TodoTask toDoTask) {
        db.collection("users").document(mAuth.getCurrentUser().getEmail()).collection("toDoTasks").add(toDoTask);
        Toast.makeText(TodoTaskViewActivity.this, "Todo toDoTask added successfully", Toast.LENGTH_SHORT).show();
        this.refreshRecyclerView();
    }

    private void refreshRecyclerView() {
        db.collection("users").document(mAuth.getCurrentUser().getEmail()).collection("toDoTasks").get()
            .addOnSuccessListener(querySnapshot -> {
                TodoTaskViewAdapter adapter = new TodoTaskViewAdapter(querySnapshot.getDocuments());
                recyclerView.setAdapter(adapter);
            });
    }
}