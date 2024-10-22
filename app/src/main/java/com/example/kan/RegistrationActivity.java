package com.example.kan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextName, editTextStudentID;
    private Button buttonRegister;
    private DatabaseReference databaseReference;
    private String eventID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        // Get the event ID passed from MainActivity
        eventID = getIntent().getStringExtra("eventID");

        // Initialize UI elements
        editTextName = findViewById(R.id.editTextName);
        editTextStudentID = findViewById(R.id.editTextStudentID);
        buttonRegister = findViewById(R.id.buttonRegister);

        // Set up Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Events").child(eventID).child("waitingList");

        // Handle the registration button click
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerForEvent();
            }
        });
    }

    private void registerForEvent() {
        String name = editTextName.getText().toString().trim();
        String studentID = editTextStudentID.getText().toString().trim();

        if (name.isEmpty() || studentID.isEmpty()) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save the user details to the waiting list in Firebase
        databaseReference.child(studentID).setValue(name)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(RegistrationActivity.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity after registering
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(RegistrationActivity.this, "Failed to register: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}