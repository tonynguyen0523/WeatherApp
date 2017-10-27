package com.twny.tonyn.weatherapp.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonyn on 10/26/2017.
 */

public class ForecastDatabaseResponse {

    List<Forecast> forecasts;

    public ForecastDatabaseResponse(){
        forecasts = new ArrayList<>();
    }

    public int getForecastListSize(){
        return forecasts.size();
    }

    public List<Forecast> getForecasts(){
        return forecasts;
    }
}
