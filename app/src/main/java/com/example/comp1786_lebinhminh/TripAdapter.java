package com.example.comp1786_lebinhminh;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Objects;

public class TripAdapter extends ArrayAdapter<Trip> {
    private final Context mContext;
    private final int mResource;

    public TripAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Trip> objects){
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

        TextView idTripText = Objects.requireNonNull(convertView).findViewById(R.id.trip_id);
        TextView nameTripText = Objects.requireNonNull(convertView).findViewById(R.id.trip_name);
        TextView dateTripText = Objects.requireNonNull(convertView).findViewById(R.id.date_of_the_trip);
        TextView destinationTripText = Objects.requireNonNull(convertView).findViewById(R.id.trip_destination);
        TextView statusText = Objects.requireNonNull(convertView).findViewById(R.id.trip_status);
        TextView riskAssessmentText = Objects.requireNonNull(convertView).findViewById(R.id.trip_risk_assessment);

        idTripText.setText(getItem(position).getTripId());
        nameTripText.setText(getItem(position).getTripName());
        nameTripText.setText(getItem(position).getTripName());
        dateTripText.setText(getItem(position).getDateOfTrip());
        destinationTripText.setText(getItem(position).getTripDestination());
        statusText.setText(String.format("Status: %s", getItem(position).getStatus()));
        riskAssessmentText.setText(String.format("Risk Assessment: %s", getItem(position).getRequireAssessment()));

        return convertView;
    }
}
