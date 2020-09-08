package com.example.covid_19;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.covid_19.activity.CountryDataActivity;
import com.example.covid_19.activity.SignUpActivity;
import com.example.covid_19.adapter.CountryAdapter;
import com.example.covid_19.model.CountryData;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int SELECTION_CODE = 100;
    private TextView tvTotalCases, tvActiveCases, tvTotalDeaths, tvRecovered, tvCountryName, tvTodayCases, tvTodayDeaths, tvTodayRecovered, tvCritical, tvTotalTests;
    private PieChart pieChart;
    private ImageView ibCountries;
    private ImageButton imageButton;
    private List<CountryData> countryDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bind();

        fetchGlobalData();

        fetchCountriesData();

        ibCountries.setOnClickListener((View.OnClickListener) MainActivity.this);
        imageButton.setOnClickListener(this);
    }

    private void fetchGlobalData() {
        String url = "https://disease.sh/v3/covid-19/all";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    tvTotalCases.setText(jsonObject.getString("cases"));
                    tvActiveCases.setText(jsonObject.getString("active"));
                    tvTotalDeaths.setText(jsonObject.getString("deaths"));
                    tvRecovered.setText(jsonObject.getString("recovered"));
                    tvTodayCases.setText(jsonObject.getString("todayCases"));
                    tvTodayRecovered.setText(jsonObject.getString("todayRecovered"));
                    tvTodayDeaths.setText(jsonObject.getString("todayDeaths"));
                    tvCritical.setText(jsonObject.getString("critical"));
                    tvTotalTests.setText(jsonObject.getString("tests"));

                    setUpPieChart();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }

    private void setUpPieChart() {
        pieChart.clearChart();
        pieChart.addPieSlice(new PieModel("Total Cases", Integer.parseInt(tvTotalCases.getText().toString()), Color.parseColor("#F57F17")));
        pieChart.addPieSlice(new PieModel("Active Cases", Integer.parseInt(tvActiveCases.getText().toString()), Color.parseColor("#42A5F5")));
        pieChart.addPieSlice(new PieModel("Total Death Cases", Integer.parseInt(tvTotalDeaths.getText().toString()), Color.parseColor("#D32F2F")));
        pieChart.addPieSlice(new PieModel("Recovered Cases", Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#388E3C")));
    }

    private void bind() {
        tvTotalCases = findViewById(R.id.tv_total_cases);
        tvActiveCases = findViewById(R.id.tv_active_cases);
        tvTotalDeaths = findViewById(R.id.tv_total_deaths);
        tvRecovered = findViewById(R.id.tv_recovered);
        tvTodayCases = findViewById(R.id.tv_today_cases);
        tvTodayDeaths = findViewById(R.id.tv_today_deaths);
        tvTodayRecovered = findViewById(R.id.tv_today_recovered);
        tvCritical = findViewById(R.id.tv_critical);
        tvTotalTests = findViewById(R.id.tv_total_tests);
        pieChart = findViewById(R.id.pie_chart);
        ibCountries = findViewById(R.id.ib_countries);
        tvCountryName = findViewById(R.id.tv_country_name);
        imageButton = findViewById(R.id.ib_menu);
        countryDataList = new ArrayList<>();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ib_countries){
            Intent intent = new Intent(MainActivity.this, CountryDataActivity.class);
            intent.putExtra("countryList", (Serializable) countryDataList);
            startActivityForResult(intent, SELECTION_CODE);
        } else if(view.getId() == R.id.ib_menu){
            startActivity(new Intent(MainActivity.this, SignUpActivity.class));
        }
    }

    private void fetchCountriesData() {
        String url = "https://disease.sh/v3/covid-19/countries";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        JSONObject object = jsonObject.getJSONObject("countryInfo");

                        countryDataList.add(new CountryData(jsonObject.getString("cases"),
                                jsonObject.getString("active"), jsonObject.getString("deaths"),
                                jsonObject.getString("recovered"), object.getString("flag"), jsonObject.getString("country"),
                                jsonObject.getString("todayCases"), jsonObject.getString("todayDeaths"),
                                jsonObject.getString("todayRecovered"), jsonObject.getString("critical"), jsonObject.getString("tests")));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == SELECTION_CODE) {

            if (resultCode == RESULT_OK) {
                CountryData countryData = (CountryData) data.getSerializableExtra("country");
                tvCountryName.setText(countryData.getCountryName());
                tvTotalCases.setText(countryData.getTotalCases());
                tvActiveCases.setText(countryData.getTotalActiveCases());
                tvTotalDeaths.setText(countryData.getTotalDeaths());
                tvRecovered.setText(countryData.getTotalRecovered());
                tvTodayCases.setText(countryData.getTodayCases());
                tvTodayRecovered.setText(countryData.getTodayRecovered());
                tvTodayDeaths.setText(countryData.getTodayDeaths());
                tvCritical.setText(countryData.getCritical());
                tvTotalTests.setText(countryData.getTotalTests());
                Glide.with(this).load(countryData.getFlagUrl()).into(ibCountries);
                setUpPieChart();

            }
        }
    }
}