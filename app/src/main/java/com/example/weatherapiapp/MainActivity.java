package com.example.weatherapiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity
{

    Button btn_getWeatherByName;
    EditText et_dataInput;
    ListView lv_weatherReports;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

//      assign values to each control on the layout

        btn_getWeatherByName = findViewById(R.id.btn_getWeatherByCityName);

        et_dataInput = findViewById(R.id.et_dataInput);

        lv_weatherReports = findViewById(R.id.lv_weatherReports);

        WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);

//      click listeners for each button

        btn_getWeatherByName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                weatherDataService.getCityForecastByName(et_dataInput.getText().toString(), new WeatherDataService.ForecastByNameResponseListener() {
                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReport) 
                    {
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, weatherReport);
                        lv_weatherReports.setAdapter(arrayAdapter);
                    }

                    @Override
                    public void onError(String message) 
                    {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}