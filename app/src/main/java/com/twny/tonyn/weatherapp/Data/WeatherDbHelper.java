package com.twny.tonyn.weatherapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.twny.tonyn.weatherapp.Data.WeatherContract.*;

/**
 * Created by tonyn on 10/19/2017.
 */

public class WeatherDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "weather.db";

    public WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + LocationEntry.TABLE_NAME + " (" +
                LocationEntry._ID + " INTEGER PRIMARY KEY," +
                LocationEntry.COLUMN_LOCATION_SETTING + " TEXT UNIQUE NOT NULL," +
                LocationEntry.COLUMN_CITY_NAME + " TEXT NOT NULL," +
                LocationEntry.COLUMN_COORD_LONG + " REAL NOT NULL," +
                LocationEntry.COLUMN_COORD_LAT + " REAL NOT NULL" + ");";

        final String SQL_CREATE_CURRENT_FORECAST_TABLE = "CREATE TABLE " + CurrentForecastEntry.TABLE_NAME + " (" +
                CurrentForecastEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CurrentForecastEntry.COLUMN_LOC_KEY + " INTEGER NOT NULL," +
                CurrentForecastEntry.COLUMN_DATE + " INTEGER NOT NULL," +
                CurrentForecastEntry.COLUMN_MAIN_DESC + " TEXT NOT NULL," +
                CurrentForecastEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL," +
                CurrentForecastEntry.COLUMN_TEMPERATURE + " REAL NOT NULL, " +

                " FOREIGN KEY (" + CurrentForecastEntry.COLUMN_LOC_KEY + ") REFERENCES " +
                LocationEntry.TABLE_NAME + " (" + LocationEntry._ID + "), " +

                " UNIQUE (" + CurrentForecastEntry.COLUMN_DATE + ", " +
                CurrentForecastEntry.COLUMN_LOC_KEY + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_LOCATION_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_CURRENT_FORECAST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LocationEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CurrentForecastEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
