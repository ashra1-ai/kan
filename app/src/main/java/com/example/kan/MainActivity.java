package com.example.kan;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kan.EventAdapter;
import com.example.kan.Participant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView eventsListView;
    private ArrayList<Event> eventList; // To hold events
    private EventAdapter adapter; // Use your custom adapter
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list); // Ensure this matches your layout file

        // Initialize ListView and event list
        eventsListView = findViewById(R.id.events_list_view);
        eventList = new ArrayList<>();

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Events");

        // Set up custom adapter for ListView
        adapter = new EventAdapter(this, eventList);
        eventsListView.setAdapter(adapter); // Fix for 'setAdapte' typo

        // Fetch events from Firebase
        fetchEvents();
    }

    private void fetchEvents() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventList.clear(); // Clear previous data

                // Iterate through each event in the database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Event event = snapshot.getValue(Event.class);
                    if (event != null) {
                        event.setEventID(snapshot.getKey()); // Set eventID from the key
                        event.getWaitingList().clear(); // Clear existing waiting list

                        // Fetching waiting list if exists
                        DataSnapshot waitingListSnapshot = snapshot.child("waitingList");
                        if (waitingListSnapshot.exists()) {
                            for (DataSnapshot participantSnapshot : waitingListSnapshot.getChildren()) {
                                Participant participant = participantSnapshot.getValue(Participant.class);
                                if (participant != null) {
                                    event.addParticipantToWaitingList(participant); // Add participant to waiting list
                                }
                            }
                        }

                        eventList.add(event); // Add event to the list
                    }
                }

                // Notify the adapter to update the ListView
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Failed to load events: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
