package com.example.barber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TodoViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton mFab;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private ListenerRegistration listenerRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_view);

        mFab = findViewById(R.id.floatingActionButton);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recycerlview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(TodoViewActivity.this));
        this.refreshRecyclerView();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager() , AddNewTask.TAG);
            }
        });
    }

    public void refreshRecyclerView() {
        db.collection("users").document(mAuth.getCurrentUser().getEmail()).collection("toDoTasks").get()
            .addOnSuccessListener(querySnapshot -> {
                TodoViewAdapter adapter = new TodoViewAdapter(querySnapshot.getDocuments());
                recyclerView.setAdapter(adapter);
            });
    }
}

class TodoViewAdapter extends RecyclerView.Adapter<TodoViewAdapter.ViewHolder> {
    private List<DocumentSnapshot> toDoTaskDocuments;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final View view;

        public ViewHolder(View view) {
            super(view);
            //TODO: Define click listener for the ViewHolder's View
            this.view = view;
        }

        public void setContent(ToDoTask toDoTask) {
            TextView txtDescription = this.view.findViewById(R.id.txtDescription);
            txtDescription.setText(toDoTask.getDescription());

            TextView txtDone = this.view.findViewById(R.id.txtDone);
            txtDone.setText(toDoTask.isDone() ? "Done" : "Pending");

            TextView txtDate = this.view.findViewById(R.id.txtDate);
            SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM HH:mm");
            txtDate.setText(formatter.format(toDoTask.getDate()));
        }
    }

    public TodoViewAdapter(List<DocumentSnapshot> toDoTaskDocuments) {
        this.toDoTaskDocuments = toDoTaskDocuments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.todo_view_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        ToDoTask toDoTask = this.toDoTaskDocuments.get(position).toObject(ToDoTask.class);
        viewHolder.setContent(toDoTask);
    }

    @Override
    public int getItemCount() {
        return this.toDoTaskDocuments.size();
    }
}