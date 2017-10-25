package com.twny.tonyn.weatherapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twny.tonyn.weatherapp.Utility.NetworkUtils;
import com.twny.tonyn.weatherapp.Utility.RetrieveDataCallbacks;

import java.io.IOException;
import java.net.URL;


public class ForecastFragment extends Fragment {

    private static final String TAG = "ForecastFragment";

    private TextView mLocationName;
    private TextView mCurrentTemp;
    private TextView mMainDesc;
    private ImageView mWeatherIcon;
    private Toolbar mToolbar;
    private TextView mErrorText;

//    private ForecastAsyncTask mForecastAsyncTask;

    public ForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastmenu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.test){
//            testQuery();
            Log.d("MENU", "TESTING");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_forecast, container, false);

        mLocationName = rootView.findViewById(R.id.location_name);
        mErrorText = rootView.findViewById(R.id.data_error_message);
//        mCurrentTemp = rootView.findViewById(R.id.current_temperature);
//        mMainDesc = rootView.findViewById(R.id.main_description);
//        mWeatherIcon = rootView.findViewById(R.id.weather_icon);

        mToolbar = rootView.findViewById(R.id.forecast_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        return rootView;
    }

//    private void testQuery(){
//        String testZipCode = "33027";
//        URL testUrl = NetworkUtils.buildUrl(testZipCode);
//        mForecastAsyncTask = new ForecastAsyncTask();
//        mForecastAsyncTask.execute(testUrl);
//    }
}


