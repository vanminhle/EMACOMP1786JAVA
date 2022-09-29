package com.example.comp1786_lebinhminh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.comp1786_lebinhminh.R;
import com.example.comp1786_lebinhminh.model.Expense;
import com.example.comp1786_lebinhminh.model.Trip;

import java.util.ArrayList;
import java.util.Objects;

public class ExpenseAdapter extends ArrayAdapter<Expense> {
    private final Context mContext;
    private final int mResource;

    public ExpenseAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Expense> objects){
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(mResource,parent, false);
        }

        TextView expenseTypeText = Objects.requireNonNull(convertView).findViewById(R.id.type_expense);
        TextView timeExpenseText = Objects.requireNonNull(convertView).findViewById(R.id.time_of_the_expense);
        TextView amountExpenseText = Objects.requireNonNull(convertView).findViewById(R.id.expense_amount);
        TextView commentText = Objects.requireNonNull(convertView).findViewById(R.id.comment);

        expenseTypeText.setText(getItem(position).getExpenseType());
        timeExpenseText.setText(getItem(position).getTimeOfExpense());
        amountExpenseText.setText(String.format("%s $", getItem(position).getAmount()));
        commentText.setText(getItem(position).getComment());

        return convertView;
    }
}
