package com.example.kan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventAdapter extends ArrayAdapter<Event> {

    private Context context;
    private ArrayList<Event> eventList;

    public EventAdapter(Context context, ArrayList<Event> eventList) {
        super(context, 0, eventList);
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate layout for each event
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);
        }

        // Get the current event
        Event event = eventList.get(position);

        // Set up event name
        TextView eventNameTextView = convertView.findViewById(R.id.event_name);
        eventNameTextView.setText(event.getName());

        // Set up the "Join" button
        Button joinButton = convertView.findViewById(R.id.join_button);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the registration activity, passing the event ID or other necessary details
                Intent intent = new Intent(context, RegistrationActivity.class);
                intent.putExtra("eventID", event.getEventID()); // Pass the event ID to the registration activity
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}