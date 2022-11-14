package com.example.barber;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {
        TextView txtSignIn;
        EditText edtFullName, edtEmail, edtMobile, edtPassword, edtConfirmPassword;
        ProgressBar progressBar;
        Button btnSignUp;
        String txtFullName, txtEmail, txtMobile, txtPassword, txtConfirmPassword;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"; //test@gmail.com
        private FirebaseAuth mAuth;
        private FirebaseFirestore db;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signup);

            txtSignIn = findViewById(R.id.txtSignIn);
            edtFullName = findViewById(R.id.edtSignUpFullName);
            edtEmail = findViewById(R.id.edtSignUpEmail);
            edtMobile = findViewById(R.id.edtSignUpMobile);
            edtPassword = findViewById(R.id.edtSignUpPassword);
            edtConfirmPassword = findViewById(R.id.edtSignUpConfirmPassword);
            progressBar = findViewById(R.id.signUpProgressBar);
            btnSignUp = findViewById(R.id.btnSignUp);

            // Initialize Firebase Auth
            mAuth = FirebaseAuth.getInstance();
            // Initialize Firebase Firestore
            db = FirebaseFirestore.getInstance();

            txtSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SignupActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    txtFullName = edtFullName.getText().toString();
                    txtEmail = edtEmail.getText().toString().trim();
                    txtMobile = edtMobile.getText().toString().trim();
                    txtPassword = edtPassword.getText().toString().trim();
                    txtConfirmPassword = edtConfirmPassword.getText().toString().trim();

                    if (!TextUtils.isEmpty(txtFullName)) {
                        if (!TextUtils.isEmpty(txtEmail)) {
                            if (txtEmail.matches(emailPattern)) {
                                if (!TextUtils.isEmpty(txtMobile)) {
                                    if (txtMobile.length() == 9) {
                                        if (!TextUtils.isEmpty(txtPassword)) {
                                            if (!TextUtils.isEmpty(txtConfirmPassword)) {
                                                if (txtConfirmPassword.equals(txtPassword)) {
                                                    SignUpUser();
                                                } else {
                                                    edtConfirmPassword.setError("Confirm Password and Password should be same.");
                                                }
                                            } else {
                                                edtConfirmPassword.setError("Confirm Password Field can't be empty");
                                            }
                                        } else {
                                            edtPassword.setError("Password Field can't be empty");
                                        }
                                    } else {
                                        edtMobile.setError("Enter a valid Mobile");
                                    }
                                } else {
                                    edtMobile.setError("Mobile Number Field can't be empty");
                                }
                            } else {
                                edtEmail.setError("Enter a valid Email Address");
                            }
                        } else {
                            edtEmail.setError("Email Field can't be empty");
                        }
                    } else {
                        edtFullName.setError("Full Name Field can't be empty");
                    }
                }
            });

        }

        private void SignUpUser() {
            progressBar.setVisibility(View.VISIBLE);
            btnSignUp.setVisibility(View.INVISIBLE);

            mAuth.createUserWithEmailAndPassword(txtEmail, txtPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    //Create a new user entry in firestore
                    User newUser = new User(txtFullName, txtMobile);
                    db.collection("users").document(mAuth.getCurrentUser().getEmail()).set(newUser);

                    //Redirect user to main activity
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignupActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    btnSignUp.setVisibility(View.VISIBLE);
                }
            });

        }
    }