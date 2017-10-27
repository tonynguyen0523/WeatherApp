package com.twny.tonyn.weatherapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.twny.tonyn.weatherapp.Utility.Forecast;
import com.twny.tonyn.weatherapp.Utility.ForecastDatabaseResponse;
import com.twny.tonyn.weatherapp.Utility.RetrieveDataCallbacks;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements RetrieveDataCallbacks {

    public static final String TAG = "MainActivity";

    private TextView mLocation;
    private TextView mErrorMessage;
    private TextView mCurrTemp;
    private TextView mMainDesc;
    private ImageView mWeatherIcon;
    private ProgressBar mProgressBar;

    private NetworkFragment mNetworkFragment;
    private List<Forecast> forecastList;
    private boolean mDownloading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progress_bar);
        mLocation = findViewById(R.id.location_name);
        mErrorMessage = findViewById(R.id.data_error_message);
        mCurrTemp = findViewById(R.id.current_temperature);
        mMainDesc = findViewById(R.id.main_description);
        mWeatherIcon = findViewById(R.id.weather_icon);

        mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager(), "33027");
    }

    @Override
    protected void onStart() {
        super.onStart();
        forecastList = new ArrayList<>();
        startDownload();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.forecastmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.test){
            startDownload();
            Log.d("MENU", "TESTING");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startDownload(){
        if (!mDownloading && mNetworkFragment != null){
            // Execute the async download.
            mNetworkFragment.startDownload();
            mProgressBar.setVisibility(View.VISIBLE);
            mDownloading = true;
        }
    }

    @Override
    public void updateFromDownload(Object result) {

        if(result != null) {
            mProgressBar.setVisibility(View.GONE);
            String stringResult = result.toString();

            // Parse json string using gson
            Gson gson = new GsonBuilder().create();
            Forecast forecastDatabaseResponse = gson.fromJson(stringResult,Forecast.class);

            mLocation.setText(forecastDatabaseResponse.getName());
            mMainDesc.setText(forecastDatabaseResponse.getWeather().get(0).getMain());
            mCurrTemp.setText(Double.toString(forecastDatabaseResponse.getMain().getTemp()));

            int weatherIcon = forecastDatabaseResponse.getWeather().get(0).getId();
            mWeatherIcon.setImageResource(weatherIcon(weatherIcon));
        }
    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }

    @Override
    public void onProgressUpdate(int progressCode, int percentComplete) {
        switch(progressCode) {
            // You can add UI behavior for progress updates here.
            case Progress.ERROR:
                Toast.makeText(this, "Check connectivity.", Toast.LENGTH_SHORT).show();
                Log.d(TAG,"ERROR");
                break;
            case Progress.CONNECT_SUCCESS:
                Log.d(TAG,"CS");
                break;
            case Progress.GET_INPUT_STREAM_SUCCESS:
                Log.d(TAG,"GISS");
                break;
            case Progress.PROCESS_INPUT_STREAM_IN_PROGRESS:
                Log.d(TAG,"PISIP");
                break;
            case Progress.PROCESS_INPUT_STREAM_SUCCESS:
                Log.d(TAG,"PISS");
                break;
        }
    }

    @Override
    public void finishDownloading() {
    }

    public int weatherIcon(int weatherCode){
        int weatherIcon = 0;
        switch (weatherCode){
            case 500:
                weatherIcon = R.drawable.art_rain;
                break;
        }
        return weatherIcon;
    }
}
