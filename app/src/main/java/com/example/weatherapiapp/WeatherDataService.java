package com.example.weatherapiapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataService
{
    Context context;

    public WeatherDataService(Context context)
    {
        this.context = context;
    }

    public interface ForecastByNameResponseListener
    {
        void onResponse(List<WeatherReportModel> weatherReport);

        void onError(String message);
    }

    public void getCityForecastByName(String cityName, ForecastByNameResponseListener forecastByNameResponseListener)
    {
        List<WeatherReportModel> weatherReport = new ArrayList<>();

        String url = "https://goweather.herokuapp.com/weather/" + cityName;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                            JSONArray jsonArray = response.getJSONArray("forecast");


                            for(int i = 0; i < jsonArray.length(); i++)
                            {
                                WeatherReportModel x = new WeatherReportModel(jsonArray.getJSONObject(i).getString("day"), jsonArray.getJSONObject(i).getString("temperature"), jsonArray.getJSONObject(i).getString("wind"));

                                weatherReport.add(x);
                            }
                        }
                        catch (JSONException e)
                        {
                            throw new RuntimeException(e);
                        }
                        forecastByNameResponseListener.onResponse(weatherReport);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        forecastByNameResponseListener.onError("Error occured" + error.toString());
                    }
                });
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
