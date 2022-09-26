package com.example.comp1786_lebinhminh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TripsDatabaseHelper tripDb = new TripsDatabaseHelper(this);
        ArrayList<Trip> trips = tripDb.getTrips();

        TripAdapter tripAdapter = new TripAdapter(this, R.layout.trips_list_row, trips);

        if(tripAdapter.getCount() != 0) {
            listView = findViewById(R.id.listTrips);
            listView.setAdapter(tripAdapter);

            listView.setOnItemClickListener((adapterView, view, position, id) -> {
                String tripId =((TextView)view.findViewById(R.id.trip_id)).getText().toString();

                Intent intent = new Intent(this, EditTripActivity.class);
                intent.putExtra("tripId", tripId);
                startActivity(intent);
            });
        } else {
            TextView textNoTrips = findViewById(R.id.no_trips);
            textNoTrips.setVisibility(View.VISIBLE);
        }
    }

    private void addNewTrip(){
        Intent intent = new Intent(this, AddTripActivity.class);
        startActivity(intent);
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.trips_top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.item_add_trip){
            addNewTrip();
            return true;
        }
        if(id == R.id.item_delete_all){
            TripsDatabaseHelper tripDb = new TripsDatabaseHelper(this);
            tripDb.deleteAllTrips();

            finish();
            overridePendingTransition( 0, 0);
            startActivity(getIntent());
            overridePendingTransition( 0, 0);

            Toast.makeText(this, "Deleted all Trips successfully", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}