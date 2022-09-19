package com.example.comp1786_lebinhminh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {

    String[] risk_assessment = {"No", "Yes"};
    String[] status = {"Initial", "Pending", "Done"};

    AutoCompleteTextView riskAssessmentValue;
    AutoCompleteTextView statusValue;
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void updateDOB(LocalDate dob){
        TextView dobText = findViewById(R.id.input_date_of_the_trip);
        dobText.setText(dob.toString());
    }

    //get inputs
    private void getInputs(){
        EditText tripName = findViewById(R.id.input_trip_name);
        EditText tripDestination = findViewById(R.id.input_destination);
        EditText tripVehicle = findViewById(R.id.input_vehicle);
        riskAssessmentValue = findViewById(R.id.dropdown_select_risk_assessment);
        EditText tripDescription = findViewById(R.id.input_description);
        statusValue = findViewById(R.id.dropdown_select_status);
        EditText tripDate = findViewById(R.id.input_date_of_the_trip);

        String strTripName = tripName.getText().toString();
        String strTripDestination = tripDestination.getText().toString();
        String strTripVehicle = tripVehicle.getText().toString();
        String strRiskAssessment = riskAssessmentValue.getText().toString();
        String strTripDescription = tripDescription.getText().toString();
        String strStatus = statusValue.getText().toString();
        String strDateOfTrip = tripDate.getText().toString();

        displayNextAlert(strTripName, strTripDestination, strTripVehicle, strRiskAssessment,strTripDescription,strStatus, strDateOfTrip);
    }

    private void displayNextAlert(String strTripName, String strTripDestination, String strTripVehicle, String strRiskAssessment,
                                  String strTripDescription, String strStatus, String strDateOfTrip ){
        new AlertDialog.Builder(this).setTitle("Details entered").setMessage("Details entered: " +
                "\n" + strTripName +
                "\n" + strTripDestination +
                "\n" + strTripVehicle +
                "\n" + strRiskAssessment +
                "\n" + strTripDescription +
                "\n" + strStatus +
                "\n" + strDateOfTrip
        ).setNeutralButton("Back", (dialogInterface, i) -> {
        }).show();
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_bar, menu);
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