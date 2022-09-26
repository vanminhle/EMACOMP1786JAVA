package com.example.comp1786_lebinhminh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class TripsDatabaseHelper extends SQLiteOpenHelper {
    private final SQLiteDatabase database;

    private static final String TABLE_NAME = "trips";

    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String DESTINATION_COLUMN = "destination";
    public static final String VEHICLE_COLUMN = "vehicle";
    public static final String ISASSESSMENT_COLUMN = "requireAssessment";
    public static final String DATEOFTRIP_COLUMN = "dateOfTrip";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String STATUS_COLUMN = "status";

    private static final String DATABASE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT)",
            TABLE_NAME, ID_COLUMN, NAME_COLUMN, DESTINATION_COLUMN, VEHICLE_COLUMN, ISASSESSMENT_COLUMN,
            DATEOFTRIP_COLUMN, DESCRIPTION_COLUMN, STATUS_COLUMN
    );

    public TripsDatabaseHelper(Context context){
        super(context, TABLE_NAME, null, 1);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        Log.v(this.getClass().getName(), TABLE_NAME +
                "database upgrade to version" + newVersion + " - old data lost"
        );
        onCreate(sqLiteDatabase);
    }

    public void insertTrip(String strTripName, String strTripDestination, String strTripVehicle,
                           String strRiskAssessment, String strDateOfTrip, String strTripDescription,
                           String strStatus){
        ContentValues rowValues = new ContentValues();

        rowValues.put(NAME_COLUMN, strTripName);
        rowValues.put(DESTINATION_COLUMN, strTripDestination);
        rowValues.put(VEHICLE_COLUMN, strTripVehicle);
        rowValues.put(ISASSESSMENT_COLUMN, strRiskAssessment);
        rowValues.put(DATEOFTRIP_COLUMN, strDateOfTrip);
        rowValues.put(DESCRIPTION_COLUMN, strTripDescription);
        rowValues.put(STATUS_COLUMN, strStatus);

        database.insertOrThrow(TABLE_NAME, null, rowValues);
    }

    public ArrayList<Trip> getTrips(){
        Cursor results = database.query(TABLE_NAME,
                new String[]{ID_COLUMN, NAME_COLUMN, DESTINATION_COLUMN, VEHICLE_COLUMN, ISASSESSMENT_COLUMN,
                        DATEOFTRIP_COLUMN, DESTINATION_COLUMN, STATUS_COLUMN,
                },
                null, null, null, null, NAME_COLUMN
        );

        ArrayList<Trip> trip_list = new ArrayList<>();

        results.moveToFirst();
        while(!results.isAfterLast()){
            String id = results.getString(0);
            String tripName = results.getString(1);
            String tripDestination = results.getString(2);
            String vehicle = results.getString(3);
            String requireAssessment = results.getString(4);
            String dateOfTrip = results.getString(5);
            String description = results.getString(6);
            String status = results.getString(7);

            trip_list.add(new Trip(id, tripName, tripDestination, vehicle, requireAssessment,
                    dateOfTrip, description, status));

            results.moveToNext();
        }
        results.close();
        return trip_list;
    }

    public Cursor getTripId(String id) {
        return this.getWritableDatabase().query(TABLE_NAME,null,"id=?",
                new String[]{String.valueOf(id)},null,null,null);
    }

    public void updateTrip(String id, String strTripName, String strTripDestination, String strTripVehicle,
                           String strRiskAssessment, String strDateOfTrip, String strTripDescription,
                           String strStatus) {
        ContentValues rowValues = new ContentValues();

        rowValues.put(NAME_COLUMN, strTripName);
        rowValues.put(DESTINATION_COLUMN, strTripDestination);
        rowValues.put(VEHICLE_COLUMN, strTripVehicle);
        rowValues.put(ISASSESSMENT_COLUMN, strRiskAssessment);
        rowValues.put(DATEOFTRIP_COLUMN, strDateOfTrip);
        rowValues.put(DESCRIPTION_COLUMN, strTripDescription);
        rowValues.put(STATUS_COLUMN, strStatus);

        database.update(TABLE_NAME, rowValues, "id = ?", new String[]{String.valueOf(id)});
    }

    public void deleteAllTrips(){
        database.delete("trips", null, null);
    }
}



