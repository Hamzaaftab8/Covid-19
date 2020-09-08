package com.example.covid_19.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.covid_19.MainActivity;
import com.example.covid_19.R;
import com.example.covid_19.adapter.CountryAdapter;
import com.example.covid_19.model.CountryData;

import java.util.ArrayList;
import java.util.List;

public class CountryDataActivity extends AppCompatActivity implements ItemClickListener{

    RecyclerView recyclerView;
    List<CountryData> countryDataList;
    CountryAdapter countryAdapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_data);

        bind();

        setUpRecyclerView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                countryAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    private void setUpRecyclerView() {
        countryAdapter = new CountryAdapter(countryDataList, CountryDataActivity.this, (ItemClickListener) CountryDataActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(countryAdapter);
    }

    private void bind() {
        recyclerView = findViewById(R.id.rvCountry);
        countryDataList = new ArrayList<>();
        countryDataList = (List<CountryData>) getIntent().getSerializableExtra("countryList");
        searchView = findViewById(R.id.searchview);
    }

    @Override
    public void onClick(View view, int position) {
        CountryData countryData = countryDataList.get(position);
        Intent intent = new Intent();
        intent.putExtra("country", countryData);
        setResult(RESULT_OK, intent);
        finish();
    }
}