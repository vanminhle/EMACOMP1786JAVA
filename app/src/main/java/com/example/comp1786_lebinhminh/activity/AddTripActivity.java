package com.example.comp1786_lebinhminh.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comp1786_lebinhminh.MainActivity;
import com.example.comp1786_lebinhminh.R;
import com.example.comp1786_lebinhminh.database.TripsDatabaseHelper;
import com.example.comp1786_lebinhminh.fragment.DatePickerFragment;

import java.time.LocalDate;

public class AddTripActivity extends AppCompatActivity {
    String[] risk_assessment = {"No", "Yes"};
    String[] status = {"Initial", "Pending", "Done"};

    AutoCompleteTextView riskAssessmentValue;
    AutoCompleteTextView statusValue;
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        riskAssessmentValue = findViewById(R.id.dropdown_select_risk_assessment);
        adapterItems = new ArrayAdapter<>(this,R.layout.assessment_list_item, risk_assessment);
        riskAssessmentValue.setAdapter(adapterItems);

        statusValue = findViewById(R.id.dropdown_select_status);
        adapterItems = new ArrayAdapter<>(this,R.layout.status_list_item, status);
        statusValue.setAdapter(adapterItems);
    }

    //date time picker
    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void updateDate(LocalDate dob){
        TextView dobText = findViewById(R.id.input_date_of_the_trip);
        dobText.setText(dob.toString());
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


        fieldValidationCheck(strTripName, strTripDestination, strTripVehicle, strRiskAssessment,
                strTripDescription, strStatus, strDateOfTrip);
    }

    //display form submit
    private void fieldValidationCheck(String strTripName, String strTripDestination, String strTripVehicle,
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

        displaySubmitForm(strTripName, strTripDestination, strTripVehicle, strRiskAssessment,
                strTripDescription,strStatus, strDateOfTrip);
    }

    private void displaySubmitForm(String strTripName, String strTripDestination, String strTripVehicle,
                                   String strRiskAssessment,
                                   String strTripDescription, String strStatus, String strDateOfTrip){

        TripsDatabaseHelper db = new TripsDatabaseHelper(this);
        db.insertTrip(strTripName, strTripDestination, strTripVehicle, strRiskAssessment, strDateOfTrip,
                strTripDescription, strStatus);

        Intent intent = new Intent(this, MainActivity.class);

        new AlertDialog.Builder(this).setTitle("Added Trip Successfully").setMessage(
                "Trip Name : " + strTripName +
                "\n" + "Trip Destination : " + strTripDestination +
                "\n" + "Trip Vehicle : " + strTripVehicle +
                "\n" + "Trip Risk Assessment : " + strRiskAssessment +
                "\n" + "Date of Trip : " + strDateOfTrip +
                "\n" + "Trip Description : " + strTripDescription +
                "\n" + "Trip Status : " + strStatus
        ).setNeutralButton("OK", (dialogInterface, i) -> {
            finish();
            startActivity(intent);
        }).show();

    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.item_done){
            getInputs();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}