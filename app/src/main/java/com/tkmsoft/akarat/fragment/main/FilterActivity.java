package com.tkmsoft.akarat.fragment.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.tkmsoft.akarat.activities.MainActivity;
import com.tkmsoft.akarat.R;
import com.tkmsoft.akarat.adapter.BathroomAdapter;
import com.tkmsoft.akarat.adapter.BedroomAdapter;
import com.tkmsoft.akarat.adapter.SpinnerCityAdapter;
import com.tkmsoft.akarat.adapter.SpinnerDiscirt;
import com.tkmsoft.akarat.adapter.TypeAdapter;
import com.tkmsoft.akarat.network.api.Api;
import com.tkmsoft.akarat.model.BathroomModel;
import com.tkmsoft.akarat.model.BedroomModel;
import com.tkmsoft.akarat.model.CityModel;
import com.tkmsoft.akarat.model.typeModel;
import com.tkmsoft.akarat.network.MyRetrofitClient;
import com.tkmsoft.akarat.util.InitSpinner;
import com.tkmsoft.akarat.util.ListSharePreference;
import com.tkmsoft.akarat.util.MyApp;
import com.tkmsoft.akarat.util.MyContextWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.BindViews;
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
    @BindView(R.id.distLinearLayout)
    LinearLayout distLinearLayout;
    Spinner citySpinner, distinctSpinner, bathRoomSpinner, bedRoomSpinner, typespinner;
    ImageButton imageButton;
    ArrayList<String> cityList, discList, typeList, bathRoomsList, bedRoomList;
    ArrayList<Integer> cityIdList, distinctIdList;
    private String typeCode, districtCode, cityCode, bathCode, roomsCode, garageCode, priceMin, PriceMax, distanceMin, distanceMax;
    private ListSharePreference.Set setSharedPreference;
    private ListSharePreference.Get getSharedPreference;
    private InitSpinner initSpinner;
    private ArrayList<CityModel.DataBean.CitiesBean> citiesModelList;
    private Api apiBase;
    @BindViews({R.id.yesRB, R.id.noRB, R.id.allRB})
    List<RadioButton> radioButtonList;
    RadioButton yesRB, noRB, allRB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        initSpinner = new InitSpinner(this);
        setLayoutLanguage();
        imageButton = findViewById(R.id.closeBtn);
        apiBase = MyRetrofitClient.getBase().create(Api.class);
        iniui();
    }

    private void setLayoutLanguage() {
        if (getLang().equals("ar"))
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        else
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
    }

    private String getLang() {
        return getSharedPreference.getLanguage();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        setSharedPreference = new ListSharePreference.Set(newBase);
        getSharedPreference = new ListSharePreference.Get(newBase);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, getLang()));
    }

    private void initTypeSpinner() {//no3
        String[] typeStringNames = getResources().getStringArray(R.array.type);
        typeList = new ArrayList<>();
        typeList.add(getString(R.string.type));
        typeList.addAll(Arrays.asList(typeStringNames));

        initSpinner.setSpinner(typespinner, typeList).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    typeCode = String.valueOf(i - 1);
                } else {
                    typeCode = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void initBathroomSpinner() {
        String[] typeStringNames = getResources().getStringArray(R.array.bathroom);
        bathRoomsList = new ArrayList<>();
        bathRoomsList.add(getString(R.string.bathrooms));
        bathRoomsList.addAll(Arrays.asList(typeStringNames));

        initSpinner.setSpinner(bathRoomSpinner, bathRoomsList).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    bathCode = String.valueOf(i);
                } else {
                    bathCode = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void initRoomSpinner() {

        String[] typeStringNames = getResources().getStringArray(R.array.bedroom);
        bedRoomList = new ArrayList<>();
        bedRoomList.add(getString(R.string.bedroom_));
        bedRoomList.addAll(Arrays.asList(typeStringNames));

        initSpinner.setSpinner(bedRoomSpinner, bedRoomList).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    roomsCode = String.valueOf(i);
                } else {
                    roomsCode = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void iniui() {
        citySpinner = findViewById(R.id.city_spinner);
        distinctSpinner = findViewById(R.id.distinct_spinner);
        bathRoomSpinner = findViewById(R.id.bathroom_spinner);
        bedRoomSpinner = findViewById(R.id.bedroom_spinner);
        typespinner = findViewById(R.id.type_spinner);
        yesRB = radioButtonList.get(0);
        noRB = radioButtonList.get(1);
        allRB = radioButtonList.get(2);

        //city
        cityList = new ArrayList<>();
        cityList.add(getString(R.string.city));
        cityIdList = new ArrayList<>();
        cityIdList.add(0);
        initCitySpinner();
        serverCity();

        initPriceRaneBar();
        initDistanceRangeBar();

        //type
        initTypeSpinner();

        initBathroomSpinner();
        initRoomSpinner();
        initCitySpinner();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        yesRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if  (b){
                    garageCode = "1";
                }
            }
        });

        noRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    garageCode = "0";
                }
            }
        });

        allRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    garageCode = null;
                }
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
//        Toast.makeText(this, "before: " + cityCode, Toast.LENGTH_SHORT).getShow();
        myIntent.setClassName(MyApp.getContext().getPackageName(), Objects.requireNonNull(MainActivity.class.getCanonicalName()));
        startActivity(myIntent);
    }

    private void initCitySpinner() {
        initSpinner.setSpinner(citySpinner, cityList).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    cityCode = String.valueOf(cityIdList.get(i));
                    distLinearLayout.setVisibility(View.VISIBLE);
                    initDistinctSpinner();
                } else {
                    cityCode = null;
                    districtCode = null;
                    distLinearLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void initDistinctSpinner() {
        discList = new ArrayList<>();
        discList.add(getString(R.string.distinct));

        distinctIdList = new ArrayList<>();
        distinctIdList.add(0);

        for (int i = 0; i < citiesModelList.size(); i++) {
            if (String.valueOf(cityCode).equals(String.valueOf(citiesModelList.get(i).getId()))) {
                List<CityModel.DataBean.CitiesBean.DisrictsBean> districtsBeanList = citiesModelList.get(i).getDisricts();
                if (districtsBeanList != null) {
                    for (int k = 0; k < districtsBeanList.size(); k++) {
                        discList.add(districtsBeanList.get(k).getName());
                        distinctIdList.add(districtsBeanList.get(k).getId());
                    }
                    break;
                }
            }
        }

        initSpinner.setSpinner(distinctSpinner, discList).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    districtCode = String.valueOf(distinctIdList.get(i));
                } else {
                    districtCode = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void serverCity() {
        callGetCity().enqueue(new Callback<CityModel>() {
            @Override
            public void onResponse(@NonNull Call<CityModel> call, @NonNull Response<CityModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    CityModel.StatusBean statusBean = response.body().getStatus();
                    if (statusBean != null) {
                        if (statusBean.getType().equals("success")) {
                            CityModel.DataBean dataBean = response.body().getData();
                            if (dataBean != null) {
                                citiesModelList = dataBean.getCities();
                                if (citiesModelList != null) {
                                    for (int i = 0; i < citiesModelList.size(); i++) {
                                        cityList.add(citiesModelList.get(i).getName());
                                        cityIdList.add(citiesModelList.get(i).getId());
                                    }
                                    initCitySpinner();
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CityModel> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private Call<CityModel> callGetCity() {
        return apiBase.getcity();
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
