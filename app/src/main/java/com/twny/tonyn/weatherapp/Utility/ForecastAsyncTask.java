package com.twny.tonyn.weatherapp.Utility;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;

/**
 * Created by tonyn on 10/25/2017.
 */

//public class ForecastAsyncTask extends AsyncTask<URL, Void, ForecastAsyncTask.Result> {
//
//    private RetrieveDataCallbacks<String> mCallBack;
//
//    ForecastAsyncTask(RetrieveDataCallbacks<String> callback){
//        setCallback(callback);
//    }
//
//    void setCallback(RetrieveDataCallbacks<String> callback){
//        mCallBack = callback;
//    }
//
//    class Result {
//        public String mResultValue;
//        public Exception mException;
//        public Result(String resultValue) {
//            mResultValue = resultValue;
//        }
//        public Result(Exception exception) {
//            mException = exception;
//        }
//    }
//
//    @Override
//    protected void onPreExecute() {
//        if (mCallBack != null) {
//            NetworkInfo networkInfo = mCallBack.getActiveNetworkInfo();
//            if (networkInfo == null || !networkInfo.isConnected() ||
//                    (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
//                            && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
//                // If no connectivity, cancel task and update Callback with null data.
//                mCallBack.updateFromDownload(null);
//                cancel(true);
//            }
//        }
//    }
//
//    @Override
//    protected ForecastAsyncTask.Result doInBackground(URL... urls) {
//        Result result = null;
//        if (!isCancelled() && urls != null && urls.length > 0) {
//
//            try {
//                URL url = urls[0];
//                String resultString = NetworkUtils.getResponseFromHttpUrl(url);
//                if(resultString != null){
//                    result = new Result(resultString);
//                }
//            } catch (IOException e) {
//                result = new Result(e);
//            }
//        }
//        return result;
//    }
//
//    /**
//     * Updates the DownloadCallback with the result.
//     */
//    @Override
//    protected void onPostExecute(Result result) {
//        if (result != null && mCallBack != null) {
//            if (result.mException != null) {
//                mCallBack.updateFromDownload(result.mException.getMessage());
//            } else if (result.mResultValue != null) {
//                mCallBack.updateFromDownload(result.mResultValue);
//            }
//            mCallBack.finishDownloading();
//        }
//    }
//}