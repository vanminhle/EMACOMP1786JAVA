package com.example.comp1786_lebinhminh.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.comp1786_lebinhminh.AppDatabaseHelper;
import com.example.comp1786_lebinhminh.MainActivity;
import com.example.comp1786_lebinhminh.R;
import com.example.comp1786_lebinhminh.adapter.ExpenseAdapter;
import com.example.comp1786_lebinhminh.model.Expense;

import java.util.ArrayList;

public class TripExpenseActivity extends AppCompatActivity {

    ListView listView;
    String currentTripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_expense);

        currentTripId = this.getIntent().getStringExtra("tripId");

        AppDatabaseHelper appDb = new AppDatabaseHelper(this);
        ArrayList<Expense> expenses = appDb.getTripExpense(currentTripId);
        ExpenseAdapter expenseAdapter = new ExpenseAdapter(this, R.layout.expenses_list_row, expenses);
        if(expenseAdapter.getCount() != 0) {
            listView = findViewById(R.id.listExpenses);
            listView.setAdapter(expenseAdapter);
        } else {
            TextView textNoExpense = findViewById(R.id.no_expenses);
            textNoExpense.setVisibility(View.VISIBLE);
        }
    }

    private void addNewExpense(){
        Intent intent = new Intent(this, AddExpenseActivity.class);
        intent.putExtra("tripId", currentTripId);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.expenses_top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.item_add_expense){
            addNewExpense();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}