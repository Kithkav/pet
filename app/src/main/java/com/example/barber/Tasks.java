package com.example.barber;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.List;

class TaskViewAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<DocumentSnapshot> toDoTaskDocuments;

    public TaskViewAdapter(List<DocumentSnapshot> toDoTaskDocuments) {
        this.toDoTaskDocuments = toDoTaskDocuments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.todo_view_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Task task = this.toDoTaskDocuments.get(position).toObject(Task.class);
        viewHolder.setContent(task);
    }

    @Override
    public int getItemCount() {
        return this.toDoTaskDocuments.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder {
    private final View view;

    public ViewHolder(View view) {
        super(view);
        this.view = view;
    }

    public void setContent(Task task) {
        TextView txtDescription = this.view.findViewById(R.id.txtDescription);
        txtDescription.setText(task.getDescription());

        TextView txtDone = this.view.findViewById(R.id.txtDone);
        txtDone.setText(task.isDone() ? "Done" : "Pending");

        TextView txtDate = this.view.findViewById(R.id.txtDate);
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM HH:mm");
        txtDate.setText(formatter.format(task.getDate()));
    }
}