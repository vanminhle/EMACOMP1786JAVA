package com.example.comp1786_lebinhminh.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.comp1786_lebinhminh.AppDatabaseHelper;
import com.example.comp1786_lebinhminh.R;
import com.example.comp1786_lebinhminh.adapter.TripAdapter;
import com.example.comp1786_lebinhminh.model.Trip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class SearchTripActivity extends AppCompatActivity {
    String[] search_options = {"Name", "Destination"};

    AutoCompleteTextView searchOptionsValue;
    ArrayAdapter<String> adapterItems;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_search_trip);

        listView = findViewById(R.id.list_result_trips);

        searchOptionsValue = findViewById(R.id.dropdown_select_search_options);
        adapterItems = new ArrayAdapter<>(this,R.layout.search_option_item, search_options);
        searchOptionsValue.setText(adapterItems.getItem(0));
        searchOptionsValue.setAdapter(adapterItems);

        EditText searchField = findViewById(R.id.input_search);
        String strSearchOption = searchOptionsValue.getText().toString().trim();

        FloatingActionButton floatingTripExpensesButton = findViewById(R.id.search_trip);
        floatingTripExpensesButton.setOnClickListener(view -> {
            String searchValue = searchField.getText().toString();
            String column = searchColumn(strSearchOption);

            AppDatabaseHelper appDb = new AppDatabaseHelper(this);
            ArrayList<Trip> trips = appDb.searchTrip(searchValue, column);

            TripAdapter tripAdapter = new TripAdapter(this, R.layout.trips_list_row, trips);
            listView.setAdapter(tripAdapter);
        });

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            String tripId =((TextView)view.findViewById(R.id.trip_id)).getText().toString();

            Intent intent = new Intent(this, EditTripActivity.class);
            intent.putExtra("tripId", tripId);
            startActivity(intent);
        });
    }

    private String searchColumn(String searchOptions){
        String column = null;

        if(Objects.equals(searchOptions, search_options[0])){
            column = "name";
        }
        if(Objects.equals(searchOptions, search_options[1])){
            column = "destination";
        }

        return column;
    }

}