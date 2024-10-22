package com.example.kan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private Button createButton;
    private Button deleteButton; // Add delete button
    private ArrayList<String> itemList; // To hold event names
    private ArrayList<String> eventIDs; // To hold event IDs for details
    private ArrayAdapter<String> adapter;

    // Firebase database reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        // Initialize ListView and Buttons
        listView = findViewById(R.id.list_view);
        createButton = findViewById(R.id.create_button);
        deleteButton = findViewById(R.id.delete_button); // Initialize delete button

        // Initialize data lists
        itemList = new ArrayList<>();
        eventIDs = new ArrayList<>(); // To store event IDs

        // Set up adapter for ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
        listView.setAdapter(adapter);

        // Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Events");

        // Fetch data from Firebase
        fetchDataFromFirebase();

        // Set OnClickListener for Create Button
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the EventCreator Activity when the button is clicked
                Intent intent = new Intent(MainActivity.this, EventCreator.class);
                startActivity(intent);
            }
        });

        // Set OnClickListener for Delete Button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if an item is selected
                if (listView.getCheckedItemCount() > 0) {
                    int position = listView.getCheckedItemPosition();
                    showDeleteConfirmationDialog(position);
                } else {
                    Toast.makeText(MainActivity.this, "Please select an event to delete.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set item click listener for ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the event ID and name
                String eventID = eventIDs.get(position);
                String eventName = itemList.get(position);

                // Start EventDetailActivity and pass the event ID
                Intent intent = new Intent(MainActivity.this, EventDetailAcitvity.class);
                intent.putExtra("eventID", eventID);
                intent.putExtra("eventName", eventName);
                startActivity(intent);
            }
        });
    }

    // Method to fetch data from Firebase
    private void fetchDataFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemList.clear();
                eventIDs.clear(); // Clear previous data

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String eventName = snapshot.child("eventName").getValue(String.class);
                    String eventID = snapshot.child("eventID").getValue(String.class);

                    if (eventName != null && eventID != null) {
                        itemList.add(eventName);
                        eventIDs.add(eventID);
                    }
                }
                adapter.notifyDataSetChanged(); // Notify adapter to refresh the ListView
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Failed to load events: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to show confirmation dialog for deletion
    private void showDeleteConfirmationDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Event")
                .setMessage("Are you sure you want to delete this event?")
                .setPositiveButton("Yes", (dialog, which) -> deleteEvent(position))
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    // Method to delete an event
    private void deleteEvent(int position) {
        String eventID = eventIDs.get(position);

        // Remove the event from Firebase
        databaseReference.child(eventID).removeValue()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(MainActivity.this, "Event deleted successfully.", Toast.LENGTH_SHORT).show();
                    // Remove the event from the local lists and update the ListView
                    itemList.remove(position);
                    eventIDs.remove(position);
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Failed to delete event: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}