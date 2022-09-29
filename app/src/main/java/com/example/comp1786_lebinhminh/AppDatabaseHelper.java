package com.example.comp1786_lebinhminh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.comp1786_lebinhminh.model.Expense;
import com.example.comp1786_lebinhminh.model.Trip;

import java.util.ArrayList;

public class AppDatabaseHelper extends SQLiteOpenHelper {
    private final SQLiteDatabase database;

    //trip
    private static final String TABLE_TRIPS = "trips";

    public static final String TRIP_ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String DESTINATION_COLUMN = "destination";
    public static final String VEHICLE_COLUMN = "vehicle";
    public static final String ISASSESSMENT_COLUMN = "requireAssessment";
    public static final String DATEOFTRIP_COLUMN = "dateOfTrip";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String STATUS_COLUMN = "status";

    private static final String TABLE_TRIPS_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT)",
            TABLE_TRIPS, TRIP_ID_COLUMN, NAME_COLUMN, DESTINATION_COLUMN, VEHICLE_COLUMN, ISASSESSMENT_COLUMN,
            DATEOFTRIP_COLUMN, DESCRIPTION_COLUMN, STATUS_COLUMN
    );

    //expenses
    private static final String TABLE_EXPENSES = "expenses";

    public static final String EXPENSE_ID_COLUMN = "id";
    public static final String ID_OF_TRIP_COLUMN = "tripId";
    public static final String EXPENSE_TYPE_COLUMN = "expenseType";
    public static final String AMOUNT_COLUMN = "amount";
    public static final String TIME_OF_EXPENSE_COLUMN = "timeOfExpense";
    public static final String COMMENT_COLUMN = "comment";

    private static final String TABLE_EXPENSES_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s INTEGER, " +
                    "%s TEXT, " +
                    "%s INTEGER, " +
                    "%s TEXT, " +
                    "%s TEXT)",
            TABLE_EXPENSES, EXPENSE_ID_COLUMN, ID_OF_TRIP_COLUMN, EXPENSE_TYPE_COLUMN, AMOUNT_COLUMN,
            TIME_OF_EXPENSE_COLUMN, COMMENT_COLUMN
    );


    public AppDatabaseHelper(Context context){
        super(context, "ExpenseManagementDb", null, 1);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_TRIPS_CREATE);
        sqLiteDatabase.execSQL(TABLE_EXPENSES_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIPS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);

        onCreate(sqLiteDatabase);
    }

    //trip
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

        database.insertOrThrow(TABLE_TRIPS, null, rowValues);
    }

    public ArrayList<Trip> getTrips(){
        Cursor results = database.query(TABLE_TRIPS,
                new String[]{TRIP_ID_COLUMN, NAME_COLUMN, DESTINATION_COLUMN, VEHICLE_COLUMN, ISASSESSMENT_COLUMN,
                        DATEOFTRIP_COLUMN, DESTINATION_COLUMN, STATUS_COLUMN,
                },
                null, null, null, null, TRIP_ID_COLUMN + " DESC"
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
        return this.getWritableDatabase().query(TABLE_TRIPS,null,"id=?",
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

        database.update(TABLE_TRIPS, rowValues, "id = ?", new String[]{String.valueOf(id)});
    }

    public void deleteTrip(String id){
        database.delete(TABLE_TRIPS,  " id = ?", new String[]{String.valueOf(id)});
        database.delete(TABLE_EXPENSES,  " tripId = ?", new String[]{String.valueOf(id)});
    }

    public void deleteAllTrips(){
        database.delete("trips", null, null);
    }

    //expenses
    public void addExpense(String tripId, String expenseType, String amount,
                           String timeOfExpense, String comment){
        ContentValues rowValues = new ContentValues();

        rowValues.put(ID_OF_TRIP_COLUMN, tripId);
        rowValues.put(EXPENSE_TYPE_COLUMN, expenseType);
        rowValues.put(AMOUNT_COLUMN, amount);
        rowValues.put(TIME_OF_EXPENSE_COLUMN, timeOfExpense);
        rowValues.put(COMMENT_COLUMN, comment);

        database.insertOrThrow(TABLE_EXPENSES, null, rowValues);
    }

    public ArrayList<Expense> getTripExpense(String idOfTrip){
        Cursor results = database.query(TABLE_EXPENSES,
                new String[]{EXPENSE_ID_COLUMN, ID_OF_TRIP_COLUMN, EXPENSE_TYPE_COLUMN, AMOUNT_COLUMN, TIME_OF_EXPENSE_COLUMN,
                        COMMENT_COLUMN,
                },
                ID_OF_TRIP_COLUMN + " = ?", new String[]{idOfTrip},
                null, null, EXPENSE_ID_COLUMN + " DESC"
        );

        ArrayList<Expense> expense_list = new ArrayList<>();

        results.moveToFirst();
        while(!results.isAfterLast()){
            String id = results.getString(0);
            String tripId = results.getString(1);
            String expenseType = results.getString(2);
            int amount = results.getInt(3);
            String timeOfExpense = results.getString(4);
            String comment = results.getString(5);

            expense_list.add(new Expense(id, tripId, expenseType, amount, timeOfExpense,
                    comment));

            results.moveToNext();
        }
        results.close();
        return expense_list;
    }
}



