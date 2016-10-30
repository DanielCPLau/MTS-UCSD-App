package com.example.daniel.mts;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;

public class DisplayListOfStops extends ListActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_stops);
        Bundle b = getIntent().getExtras();
        String id = b.getString("SelectedProperty");
        Line lin = new Line(id);
        String[] stopIds = lin.listOfStopsId;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.stops_rowlayout, R.id.stoptxt, stopIds);

        // Bind to our new adapter.
        setListAdapter(adapter);
    }
    public class stopAdapter extends ArrayAdapter {
        public stopAdapter(Context context, int resource, int textViewResourceId, Object[] objects) {
            super(context, resource, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = super.getView(position, convertView, parent);

            // alternating grey and white row backgrounds
            if (position % 2 == 1) {
                view.setBackgroundColor(Color.WHITE);
            } else {
                view.setBackgroundColor(Color.parseColor("#F7F7F7"));
            }

            return view;
        }
    }

}

