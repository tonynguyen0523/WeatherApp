package com.twny.tonyn.weatherapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.twny.tonyn.weatherapp.Utility.NetworkUtils;
import com.twny.tonyn.weatherapp.Utility.RetrieveDataCallbacks;

import java.io.IOException;
import java.net.URL;

/**
 * Created by tonyn on 10/25/2017.
 */

public class NetworkFragment extends Fragment {

    public static final String TAG = "NetworkFragment";

    private static final String ZIP_CODE = "UrlKey";

    private RetrieveDataCallbacks mCallback;
    private ForecastAsyncTask mForecastAsyncTask;
    private String mZipCode;

    public static NetworkFragment getInstance(FragmentManager fragmentManager, String zipCode){
        NetworkFragment networkFragment = (NetworkFragment) fragmentManager
                .findFragmentByTag(NetworkFragment.TAG);
        if (networkFragment == null) {
            networkFragment = new NetworkFragment();
            Bundle args = new Bundle();
            args.putString(ZIP_CODE, zipCode);
            networkFragment.setArguments(args);
            fragmentManager.beginTransaction().add(networkFragment, TAG).commit();
        }
        return networkFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mZipCode= getArguments().getString(ZIP_CODE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Host Activity will handle callbacks from task.
        mCallback = (RetrieveDataCallbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // Clear reference to host Activity to avoid memory leak.
        mCallback = null;
    }

    @Override
    public void onDestroy() {
        // Cancel task when Fragment is destroyed.
        cancelDownload();
        super.onDestroy();
    }

    /**
     * Start non-blocking execution of DownloadTask.
     */
    public void startDownload() {
        cancelDownload();
        String testZipCode = "33027";
        URL testUrl = NetworkUtils.buildUrl(testZipCode);
        mForecastAsyncTask = new ForecastAsyncTask(mCallback);
        mForecastAsyncTask.execute(testUrl);
    }

    /**
     * Cancel (and interrupt if necessary) any ongoing DownloadTask execution.
     */
    public void cancelDownload() {
        if (mForecastAsyncTask != null) {
            mForecastAsyncTask.cancel(true);
        }
    }

    /**
     * Implementation of AsyncTask designed to fetch data from the network.
     * */
    public class ForecastAsyncTask extends AsyncTask<URL, Void, ForecastAsyncTask.Result> {

        private RetrieveDataCallbacks<String> mCallBack;

        ForecastAsyncTask(RetrieveDataCallbacks<String> callback){
            setCallback(callback);
        }

        void setCallback(RetrieveDataCallbacks<String> callback){
            mCallBack = callback;
        }

        /**
         * Wrapper class that serves as a union of a result value and an exception. When the download
         * task has completed, either the result value or exception can be a non-null value.
         * This allows you to pass exceptions to the UI thread that were thrown during doInBackground().
         */
        class Result {
            public String mResultValue;
            public Exception mException;
            public Result(String resultValue) {
                mResultValue = resultValue;
            }
            public Result(Exception exception) {
                mException = exception;
            }
        }

        /**
         * Cancel background network operation if we do not have network connectivity.
         */
        @Override
        protected void onPreExecute() {
            if (mCallBack != null) {
                NetworkInfo networkInfo = mCallBack.getActiveNetworkInfo();
                if (networkInfo == null || !networkInfo.isConnected() ||
                        (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                                && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
                    // If no connectivity, cancel task and update Callback with null data.
                    mCallBack.updateFromDownload(null);
                    cancel(true);
                } else {
                    mCallback.onProgressUpdate(0,0);
                }
            }
        }

        /**
         * Defines work to perform on the background thread.
         */
        @Override
        protected ForecastAsyncTask.Result doInBackground(URL... urls) {
            Result result = null;
            if (!isCancelled() && urls != null && urls.length > 0) {
                try {
                    URL url = urls[0];
                    String resultString = NetworkUtils.getResponseFromHttpUrl(url);
                    if(resultString != null){
                        result = new Result(resultString);
                    }
                } catch (IOException e) {
                    result = new Result(e);
                }
            }
            return result;
        }

        /**
         * Updates the DownloadCallback with the result.
         */
        @Override
        protected void onPostExecute(Result result) {
            if (result != null && mCallBack != null) {
                if (result.mException != null) {
                    mCallBack.updateFromDownload(result.mException.getMessage());
                } else if (result.mResultValue != null) {
                    mCallBack.updateFromDownload(result.mResultValue);
                }
                mCallBack.finishDownloading();
            }
        }

        /**
         * Override to add special behavior for cancelled AsyncTask.
         */
        @Override
        protected void onCancelled(Result result) {
        }
    }
}
