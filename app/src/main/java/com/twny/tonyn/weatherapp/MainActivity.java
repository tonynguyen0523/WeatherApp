package com.twny.tonyn.weatherapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.twny.tonyn.weatherapp.Utility.RetrieveDataCallbacks;

public class MainActivity extends FragmentActivity implements RetrieveDataCallbacks {

    public static final String TAG = "MainActivity";

    private TextView mTestResult;
    private Button mTestButton;
    private NetworkFragment mNetworkFragment;
    private boolean mDownloading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTestResult = findViewById(R.id.test_result);
        mTestButton = findViewById(R.id.test_button);
        mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDownload();
            }
        });

        mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager(), "33027");
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!mDownloading && mNetworkFragment != null){
            // Execute the async download.
            mNetworkFragment.startDownload();
            mDownloading = true;
        }
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
            mDownloading = true;
        }
    }

    @Override
    public void updateFromDownload(Object result) {
        String stringResult = result.toString();
        mTestResult.setText(stringResult);
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
}
