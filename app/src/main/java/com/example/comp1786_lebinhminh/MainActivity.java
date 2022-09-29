package com.example.comp1786_lebinhminh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import com.example.comp1786_lebinhminh.activity.AddTripActivity;
import com.example.comp1786_lebinhminh.activity.EditTripActivity;
import com.example.comp1786_lebinhminh.activity.SearchTripActivity;
import com.example.comp1786_lebinhminh.adapter.TripAdapter;
import com.example.comp1786_lebinhminh.model.Trip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabaseHelper appDb = new AppDatabaseHelper(this);
        ArrayList<Trip> trips = appDb.getTrips();
        TripAdapter tripAdapter = new TripAdapter(this, R.layout.trips_list_row, trips);

        if(tripAdapter.getCount() != 0) {
            listView = findViewById(R.id.list_trips);
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

        FloatingActionButton floatingTripExpensesButton = findViewById(R.id.search_trip);
        floatingTripExpensesButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SearchTripActivity.class);
            startActivity(intent);
        });
    }

    private void addNewTrip(){
        Intent intent = new Intent(this, AddTripActivity.class);
        startActivity(intent);
    }

    //restart activity
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
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
            AppDatabaseHelper appDb = new AppDatabaseHelper(this);
            appDb.deleteAllTrips();

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