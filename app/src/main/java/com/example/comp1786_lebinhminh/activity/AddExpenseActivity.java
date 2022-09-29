package com.example.comp1786_lebinhminh.activity;

import androidx.annotation.NonNull;
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

import com.example.comp1786_lebinhminh.AppDatabaseHelper;
import com.example.comp1786_lebinhminh.R;
import com.example.comp1786_lebinhminh.fragment.TimeExpensePickerFragment;

import java.time.LocalDate;

public class AddExpenseActivity extends AppCompatActivity {
    String[] type_expense = {"Travel", "Food", "Other"};
    String currentTripId;

    AutoCompleteTextView typeExpenseValue;
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        typeExpenseValue = findViewById(R.id.dropdown_select_type_expense);
        adapterItems = new ArrayAdapter<>(this,R.layout.expense_type_list_item, type_expense);
        typeExpenseValue.setAdapter(adapterItems);

        currentTripId = this.getIntent().getStringExtra("tripId");
    }

    //date time picker
    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new TimeExpensePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void updateDate(LocalDate date){
        TextView dateText = findViewById(R.id.input_time_of_the_expense);
        dateText.setText(date.toString());
    }

    //get inputs
    private void getInputs(){
        EditText expenseAmount = findViewById(R.id.input_amount);
        EditText timeOfExpense = findViewById(R.id.input_time_of_the_expense);
        EditText comment= findViewById(R.id.input_comment);

        String strTypeExpense = typeExpenseValue.getText().toString().trim();
        String numExpenseAmount = expenseAmount.getText().toString().trim();
        String strTimeOfExpense = timeOfExpense.getText().toString();
        String strComment = comment.getText().toString().trim();
        
        fieldValidationCheck(strTypeExpense, numExpenseAmount, strTimeOfExpense, strComment);
    }

    //display form submit
    private void fieldValidationCheck(String strTypeExpense, String numExpenseAmount, String strTimeOfExpense,
                                      String strComment){
        if(TextUtils.isEmpty(strTypeExpense) | TextUtils.isEmpty(numExpenseAmount) |
                TextUtils.isEmpty(strTimeOfExpense) | TextUtils.isEmpty(strComment)){
            Toast.makeText(this, "You must entered all required field!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(numExpenseAmount.length() > 8) {
            Toast.makeText(this, "Expense Amount must lower than 8 numbers", Toast.LENGTH_SHORT).show();
            return;
        }
        if(strComment.length() > 150) {
            Toast.makeText(this, "Comment must lower than 150 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        displaySubmitSuccess(strTypeExpense, numExpenseAmount, strTimeOfExpense, strComment);
    }

    private void displaySubmitSuccess(String strTypeExpense, String numExpenseAmount, String strTimeOfExpense,
                                   String strComment){

        AppDatabaseHelper appDb = new AppDatabaseHelper(this);
        appDb.addExpense(currentTripId, strTypeExpense, numExpenseAmount, strTimeOfExpense, strComment);

        finish();
        Toast.makeText(this, "Trip Expense Added Successfully", Toast.LENGTH_SHORT).show();
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_expense_top_menu, menu);
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