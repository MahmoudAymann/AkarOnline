package com.tkmsoft.akarat.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.tkmsoft.akarat.MainActivity;
import com.tkmsoft.akarat.R;
import com.tkmsoft.akarat.adapter.BathroomAdapter;
import com.tkmsoft.akarat.adapter.BedroomAdapter;
import com.tkmsoft.akarat.adapter.SpinnerCityAdapter;
import com.tkmsoft.akarat.adapter.SpinnerDiscirt;
import com.tkmsoft.akarat.adapter.TypeAdapter;
import com.tkmsoft.akarat.api.Api;
import com.tkmsoft.akarat.model.BathroomModel;
import com.tkmsoft.akarat.model.BedroomModel;
import com.tkmsoft.akarat.model.CityModel;
import com.tkmsoft.akarat.model.typeModel;
import com.tkmsoft.akarat.network.MyRetrofitClient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivity extends AppCompatActivity {
    @BindView(R.id.priceRangeBar)
    CrystalRangeSeekbar priceRangeBar;
    @BindView(R.id.distanceRangeBar)
    CrystalRangeSeekbar distanceRangeBar;
    @BindView(R.id.priceMinET)
    EditText priceMinET;
    @BindView(R.id.priceMaxET)
    EditText priceMaxET;
    @BindView(R.id.distanceMinET)
    EditText areaMinET;
    @BindView(R.id.distanceMaxET)
    EditText areaMaxET;
    SpinnerDiscirt spinnerAdapter1;
    SpinnerCityAdapter spinnerAdapter;
    Spinner city, disc, depertment, bathroom, bedroom, typespinner, gragespinner;
    ImageButton imageButton;
    ArrayList<String> cityArrayList, disccArrayList;
    LinearLayout linearLayout;
    private String typeCode, districtCode, cityCode, bathCode, roomsCode, garageCode, priceMin, PriceMax, distanceMin, distanceMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        imageButton = findViewById(R.id.closeBtn);
        iniui();
    }

    private void iniTypeSpinner() {
        //for rent or sale
        String[] typeStringNames = getResources().getStringArray(R.array.type);
        ArrayList<typeModel> countriesModelsList = new ArrayList<>();
        for (String countries : typeStringNames) {
            typeModel item = new typeModel(countries);
            countriesModelsList.add(item);
        }
        final TypeAdapter typeAdapter = new TypeAdapter(this, countriesModelsList);
        typespinner.setAdapter(typeAdapter);
        typespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0)
                    typeCode = String.valueOf(i - 1);
                else
                    typeCode = null;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void iniBathroomSpinner() {
        String[] typeStringNames = getResources().getStringArray(R.array.bathroom);
        ArrayList<BathroomModel> countriesModelsList = new ArrayList<>();
        for (String countries : typeStringNames) {
            BathroomModel item = new BathroomModel(countries);
            countriesModelsList.add(item);
        }
        BathroomAdapter bathroomAdapter = new BathroomAdapter(FilterActivity.this, countriesModelsList);
        bathroom.setAdapter(bathroomAdapter);
        bathroom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0)
                    bathCode = String.valueOf(i);
                else
                    bathCode = null;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void iniRoomSpinner() {

        String[] typeStringNames = getResources().getStringArray(R.array.bedroom);
        ArrayList<BedroomModel> countriesModelsList = new ArrayList<>();
        for (String countries : typeStringNames) {
            BedroomModel item = new BedroomModel(countries);
            countriesModelsList.add(item);
        }
        BedroomAdapter bedroomAdapter = new BedroomAdapter(this, countriesModelsList);
        bedroom.setAdapter(bedroomAdapter);
        bedroom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0)
                    roomsCode = String.valueOf(i);
                else
                    roomsCode = null;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void iniui() {
        city = findViewById(R.id.spinner1);
        disc = findViewById(R.id.spinner2);
        bathroom = findViewById(R.id.spinner3);
        bedroom = findViewById(R.id.spinner4);
        typespinner = findViewById(R.id.spinner);
        linearLayout = findViewById(R.id.lin3);
        initPriceRaneBar();
        initDistanceRangeBar();
        iniTypeSpinner();
        iniBathroomSpinner();
        iniRoomSpinner();
        initServiceCity();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick(R.id.updateBtn)
    protected void onUpdateClick() {
        Intent myIntent = new Intent();
        myIntent.putExtra("type", typeCode);
        myIntent.putExtra("city", cityCode);
        myIntent.putExtra("district", districtCode);
        myIntent.putExtra("bathRoom", bathCode);
        myIntent.putExtra("rooms", roomsCode);
        myIntent.putExtra("garage", garageCode);
//        myIntent.putExtra("priceMin", priceMinET.getText().toString());
//        myIntent.putExtra("priceMax", priceMaxET.getText().toString());
//        myIntent.putExtra("areaMin", areaMinET.getText().toString());
//        myIntent.putExtra("areaMax", areaMaxET.getText().toString());
        myIntent.putExtra("key", 1);
//        Toast.makeText(this, "before: " + cityCode, Toast.LENGTH_SHORT).show();
        myIntent.setClassName("com.tkmsoft.akarat", MainActivity.class.getCanonicalName());
        startActivity(myIntent);
    }

    private void initServiceCity() {
        Api api = MyRetrofitClient.categories().create(Api.class);
        Call<CityModel> loginModelCall = api.getcity();
        loginModelCall.enqueue(new Callback<CityModel>() {

            @Override
            public void onResponse(@NonNull Call<CityModel> call, @NonNull Response<CityModel> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        try {
                            iniCitySpinner(response.body().getData().getCities());
                        } catch (Exception e) {
                            Toast.makeText(FilterActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CityModel> call, @NonNull Throwable t) {

                Toast.makeText(FilterActivity.this, R.string.errorconnection, Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });


    }

    private void iniCitySpinner(final ArrayList<CityModel.DataBean.CitiesBean> cities) {
        cityArrayList = new ArrayList<>();
        cityArrayList.add(0, getString(R.string.choosecit));

        for (int i = 0; i < cities.size(); i++) {
            cityArrayList.add(cities.get(i).getName());
        }

        spinnerAdapter = new SpinnerCityAdapter(this, cityArrayList);
        city.setAdapter(spinnerAdapter);
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    linearLayout.setVisibility(LinearLayout.VISIBLE);
                    cityCode = String.valueOf(cities.get(i - 1).getId());
                    initServiceDistrict();
                } else {
                    linearLayout.setVisibility(LinearLayout.GONE);
                    districtCode = String.valueOf(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void initServiceDistrict() {
        Api api = MyRetrofitClient.categories().create(Api.class);
        Call<CityModel> loginModelCall = api.getcity();
        loginModelCall.enqueue(new Callback<CityModel>() {
            int pos = Integer.parseInt(cityCode);

            @Override
            public void onResponse(@NonNull Call<CityModel> call, @NonNull Response<CityModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus().getType().equals("success"))
                            iniDistinctSpinner(response.body().getData().getCities().get(pos - 1).getDisricts());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CityModel> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void iniDistinctSpinner(final ArrayList<CityModel.DataBean.CitiesBean.DisrictsBean> disricts) {
        disccArrayList = new ArrayList<>();
        disccArrayList.clear();
        disccArrayList.add(0, getString(R.string.chooseDistrict));

        for (int i = 0; i < disricts.size(); i++) {
            disccArrayList.add(disricts.get(i).getName());
        }
        spinnerAdapter1 = new SpinnerDiscirt(FilterActivity.this, disccArrayList);
        disc.setAdapter(spinnerAdapter1);
        disc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    districtCode = String.valueOf(disricts.get(i - 1).getId());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void initPriceRaneBar() {
        priceRangeBar.setMinValue(5000);
        priceRangeBar.setMaxValue(90000000);
        priceRangeBar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                priceMinET.setText(String.valueOf(minValue));
                priceMaxET.setText(String.valueOf(maxValue));
            }
        });
    }

    private void initDistanceRangeBar() {
        distanceRangeBar.setMinValue(50);
        distanceRangeBar.setMaxValue(1000000);
        distanceRangeBar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                areaMinET.setText(String.valueOf(minValue));
                areaMaxET.setText(String.valueOf(maxValue));
            }
        });
    }

}
