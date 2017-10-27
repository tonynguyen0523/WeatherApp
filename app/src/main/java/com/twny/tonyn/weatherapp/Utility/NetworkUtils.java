package com.twny.tonyn.weatherapp.Utility;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by tonyn on 10/20/2017.
 */

public class NetworkUtils {

    public static final String TAG = "NetworkUtils";

    final static String BASE_URL =
            "http://samples.openweathermap.org/data/2.5/weather?";
    final static String QUERY_PARAM = "zip";
    final static String APPID_PARAM = "appid";


    public static URL buildUrl(String zipCode){
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, zipCode)
                .appendQueryParameter(APPID_PARAM, "b1b15e88fa797225412429c1c50c122a1")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
            Log.d(TAG, url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try{
                InputStream in = urlConnection.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if(hasInput){
                    return scanner.next();
                } else {
                    return null;
                }
            } finally {
                urlConnection.disconnect();
            }
    }
}
