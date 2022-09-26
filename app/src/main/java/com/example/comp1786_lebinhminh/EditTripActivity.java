package com.example.comp1786_lebinhminh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;

public class EditTripActivity extends AppCompatActivity {
    String[] risk_assessment = {"No", "Yes"};
    String[] status = {"Initial", "Pending", "Done"};

    AutoCompleteTextView riskAssessmentValue;
    AutoCompleteTextView statusValue;
    ArrayAdapter<String> adapterItems;

    TripsDatabaseHelper tripDb;
    String currentTripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);

        riskAssessmentValue = findViewById(R.id.dropdown_select_risk_assessment);
        adapterItems = new ArrayAdapter<>(this,R.layout.assessment_list_item, risk_assessment);
        riskAssessmentValue.setAdapter(adapterItems);

        statusValue = findViewById(R.id.dropdown_select_status);
        adapterItems = new ArrayAdapter<>(this,R.layout.status_list_item, status);
        statusValue.setAdapter(adapterItems);

        tripDb = new TripsDatabaseHelper(this);
        currentTripId = this.getIntent().getStringExtra("tripId");
        setInputs();
    }

    //date time picker
    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void updateDate(LocalDate date){
        TextView dobText = findViewById(R.id.input_date_of_the_trip);
        dobText.setText(date.toString());
    }

    //set inputs
    private void setInputs(){
        EditText tripName = findViewById(R.id.input_trip_name);
        EditText tripDestination = findViewById(R.id.input_destination);
        EditText tripVehicle = findViewById(R.id.input_vehicle);
        EditText tripDescription = findViewById(R.id.input_description);
        EditText tripDate = findViewById(R.id.input_date_of_the_trip);

        Cursor cursor = tripDb.getTripId(currentTripId);
        if (cursor.moveToFirst()) {
            tripName.setText(cursor.getString(1));
            tripDestination.setText(cursor.getString(2));
            tripVehicle.setText(cursor.getString(3));
            riskAssessmentValue.setText(cursor.getString(4), false);
            tripDate.setText(cursor.getString(5));
            tripDescription.setText(cursor.getString(6));
            statusValue.setText(cursor.getString(7), false);
        }
        cursor.close();
    }

    //get inputs
    private void getInputs(){
        EditText tripName = findViewById(R.id.input_trip_name);
        EditText tripDestination = findViewById(R.id.input_destination);
        EditText tripVehicle = findViewById(R.id.input_vehicle);
        EditText tripDescription = findViewById(R.id.input_description);
        EditText tripDate = findViewById(R.id.input_date_of_the_trip);

        String strTripName = tripName.getText().toString().trim();
        String strTripDestination = tripDestination.getText().toString().trim();
        String strTripVehicle = tripVehicle.getText().toString().trim();
        String strRiskAssessment = riskAssessmentValue.getText().toString().trim();
        String strTripDescription = tripDescription.getText().toString().trim();
        String strStatus = statusValue.getText().toString().trim();
        String strDateOfTrip = tripDate.getText().toString().trim();


        fieldValidationCheck(currentTripId, strTripName, strTripDestination, strTripVehicle, strRiskAssessment,
                strTripDescription, strStatus, strDateOfTrip);
    }

    //display form submit
    private void fieldValidationCheck(String id, String strTripName, String strTripDestination, String strTripVehicle,
                                      String strRiskAssessment, String strTripDescription, String strStatus,
                                      String strDateOfTrip){
        if(TextUtils.isEmpty(strTripName) | TextUtils.isEmpty(strTripDestination) |
                TextUtils.isEmpty(strTripVehicle) | TextUtils.isEmpty(strRiskAssessment) |
                TextUtils.isEmpty(strDateOfTrip)  | TextUtils.isEmpty(strStatus) ){
            Toast.makeText(this, "You must entered all required field!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(strTripName.length() > 40) {
            Toast.makeText(this, "Name must lower than 40 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        if(strTripDestination.length() > 40){
            Toast.makeText(this, "Destination must lower than 40 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        if(strTripVehicle.length() > 40) {
            Toast.makeText(this, "Destination must lower than 40 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        if(strTripDescription.length() > 270) {
            Toast.makeText(this, "Description must lower than 270 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        displaySubmitForm(id, strTripName, strTripDestination, strTripVehicle, strRiskAssessment,
                strTripDescription,strStatus, strDateOfTrip);
    }

    private void displaySubmitForm(String id, String strTripName, String strTripDestination, String strTripVehicle,
                                   String strRiskAssessment,
                                   String strTripDescription, String strStatus, String strDateOfTrip){

        TripsDatabaseHelper db = new TripsDatabaseHelper(this);
        db.updateTrip(id, strTripName, strTripDestination, strTripVehicle, strRiskAssessment, strDateOfTrip,
                strTripDescription, strStatus);

        finish();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

        Toast.makeText(this, "Edit Trip Successfully", Toast.LENGTH_SHORT).show();
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.item_done){
            getInputs();
            return true;
        }
        if(id == R.id.item_delete){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}