package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;

    /*@Override
    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = {"Edmonton", "Vancouver", "Toronto", "Hamilton", "Denver", "Los Angeles"};

        String[] provinces = {"AB", "BC", "ON", "ON", "CO", "CA"};


        dataList = new ArrayList<City>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        // FloatingActionButton for adding a new city
        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            new AddCityFragment().show(getSupportFragmentManager(),
                    "Add City");
        });

        // Click listener for editing exisiting cities
        cityList.setOnItemClickListener((parent, view, position, id) ->{
            City city = dataList.get(position);
            AddCityFragment fragment = new AddCityFragment();
            fragment.setCityToEdit(city,position);
            fragment.show(getSupportFragmentManager(), "Edit City");
        });
    }

    // Called when adding or editing a city@Override
        public void addOrEditCity(City city, int position) {
            if (position >= 0) {
                // Edit exisiting city
               dataList.set(position, city);
           } else {
                // Add new city
                 dataList.add(city);
            }
             cityAdapter.notifyDataSetChanged();
        }

}