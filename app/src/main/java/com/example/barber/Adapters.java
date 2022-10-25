package com.example.barber;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.List;

class TodoTaskViewAdapter extends RecyclerView.Adapter<TodoTaskViewAdapter.ViewHolder> {
    static class ViewHolder extends RecyclerView.ViewHolder {
        private final View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }

        public void setContent(TodoTask toDoTask) {
            TextView txtDescription = this.view.findViewById(R.id.txtDescription_todo);
            txtDescription.setText(toDoTask.getDescription());

            TextView txtDone = this.view.findViewById(R.id.txtDone_todo);
            txtDone.setText(toDoTask.isDone() ? "Done" : "Pending");
        }
    }

    private List<DocumentSnapshot> toDoTaskDocuments;

    public TodoTaskViewAdapter(List<DocumentSnapshot> toDoTaskDocuments) {
        this.toDoTaskDocuments = toDoTaskDocuments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.todo_view_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        TodoTask toDoTask = this.toDoTaskDocuments.get(position).toObject(TodoTask.class);
        viewHolder.setContent(toDoTask);
    }

    @Override
    public int getItemCount() {
        return this.toDoTaskDocuments.size();
    }
}

class AppointmentViewAdapter extends RecyclerView.Adapter<AppointmentViewAdapter.ViewHolder> {
    static class ViewHolder extends RecyclerView.ViewHolder {
        private final View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }

        public void setContent(Appointment appointment) {
            TextView txtDescription = this.view.findViewById(R.id.txtDescription_appointment);
            txtDescription.setText(appointment.getDescription());

            TextView txtDone = this.view.findViewById(R.id.txtDone_appointment);
            txtDone.setText(appointment.isDone() ? "Done" : "Pending");

            TextView txtDate = this.view.findViewById(R.id.txtDate_appointment);
            SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM HH:mm");
            txtDate.setText(formatter.format(appointment.getDate()));
        }
    }

    private List<DocumentSnapshot> appointmentDocuments;

    public AppointmentViewAdapter(List<DocumentSnapshot> appointmentDocuments) {
        this.appointmentDocuments = appointmentDocuments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.appointment_view_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Appointment appointment = this.appointmentDocuments.get(position).toObject(Appointment.class);
        viewHolder.setContent(appointment);
    }

    @Override
    public int getItemCount() {
        return this.appointmentDocuments.size();
    }
}