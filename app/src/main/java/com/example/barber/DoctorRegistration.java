package com.example.barber;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorRegistration extends AppCompatActivity {
    private TextView regPageQuestion;
    ImageView signup_back_button;

    private EditText doctorName, gender, emailAddress;
    private Button registerbtn;

    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;



    // creating a variable for
    // our object class
    EmployeeInfo employeeInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);

        regPageQuestion = findViewById(R.id.regPageQuestion);
        regPageQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorRegistration.this, Signup.class);
                startActivity(intent);
            }
        });

        signup_back_button = findViewById(R.id.signup_back_button);
        signup_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorRegistration.this, SelectRegistration.class);
                startActivity(i);
            }
        });

        // initializing our edittext and button
        doctorName = findViewById(R.id.registrationFullName);
        gender = findViewById(R.id.gender);
        emailAddress = findViewById(R.id.emailAddress);

        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Users");

        employeeInfo = new EmployeeInfo();
        registerbtn = findViewById(R.id.registerbtn);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting text from our edittext fields.
                String DoctorName = doctorName.getText().toString();
                String DoctorGender = gender.getText().toString();
                String DoctorEmailAddress = emailAddress.getText().toString();

                // below line is for checking weather the
                // edittext fields are empty or not.
                if (TextUtils.isEmpty(DoctorName) && TextUtils.isEmpty(DoctorGender) && TextUtils.isEmpty(DoctorEmailAddress)) {
                    // if the text fields are empty
                    // then show the below message.
                    Toast.makeText(DoctorRegistration.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {
                    // else call the method to add
                    // data to our database.
                    addDatatoFirebase(DoctorName,DoctorGender,DoctorEmailAddress);
                }
            }
        });


    }

    private void addDatatoFirebase(String DoctorName, String DoctorGender, String DoctorEmailAddress) {
        // below 3 lines of code is used to set
        // data in our object class.
        employeeInfo.setDoctorName(DoctorName);
        employeeInfo.setGender(DoctorGender);
        employeeInfo.setEmailAddress(DoctorEmailAddress);

        // we are use add value event listener method
        // which is called with database reference.

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.setValue(employeeInfo);

                // after adding this data we are showing toast message.
                Toast.makeText(DoctorRegistration.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(DoctorRegistration.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}