package com.tkmsoft.akarat.navfragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tkmsoft.akarat.MainActivity;
import com.tkmsoft.akarat.R;
import com.tkmsoft.akarat.adapter.BathroomAdapter;
import com.tkmsoft.akarat.adapter.BedroomAdapter;
import com.tkmsoft.akarat.adapter.GrageAdapter;
import com.tkmsoft.akarat.adapter.SpinnerCityAdapter;
import com.tkmsoft.akarat.adapter.SpinnerDepertmentAdapter;
import com.tkmsoft.akarat.adapter.SpinnerDiscirt;
import com.tkmsoft.akarat.adapter.TypeAdapter;
import com.tkmsoft.akarat.api.Api;
import com.tkmsoft.akarat.interfaces.IoCallBack;
import com.tkmsoft.akarat.model.AkarsModel;
import com.tkmsoft.akarat.model.BathroomModel;
import com.tkmsoft.akarat.model.BedroomModel;
import com.tkmsoft.akarat.model.CityModel;
import com.tkmsoft.akarat.model.GrageModel;
import com.tkmsoft.akarat.model.OrderModel;
import com.tkmsoft.akarat.model.typeModel;
import com.tkmsoft.akarat.network.MyRetrofitClient;
import com.tkmsoft.akarat.util.ListSharePreference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    SpinnerCityAdapter spinnerAdapter;
    ArrayList<String> cityArrayList, disccArrayList, depert;
    SpinnerDiscirt spinnerAdapter1;
    SpinnerDepertmentAdapter spinnerDepertmentAdapter;
    Spinner city, disc, depertment, bathroom, bedroom, typespinner, gragespinner;
    int city_pos;
    int discirt, depertt, bath, bed, type, grage;
    int discirt1, depertt1, bath1, bed1, type1, grage1, idcity1;
    EditText address, price, area;
    Button submit;
    private int city_id;
    private int discirt_pos;
    ListSharePreference.Set setSharedPreference;
    ListSharePreference.Get getSharedPreference;
    IoCallBack mMainViewsCallBack;
    private ProgressBar progressBar;
    LinearLayout linearLayout;
    MapView mapView;
    GoogleMap map2;
    Marker marker;
    double la = 0, lo = 0;
    @BindView(R.id.mapBtn)
    Button bmap;
    @BindView(R.id.nameET)
    EditText nameET;
    @BindView(R.id.checkBox)
    CheckBox checkBox;
    private int check;


    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, root);
        iniui(root);
        bmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_map, null);
                mapView = mView.findViewById(R.id.map1);
                mapView.onCreate(savedInstanceState);

                mapView.onResume();// needed to get the map to display immediately

                try {
                    MapsInitializer.initialize(getActivity().getApplicationContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        map2 = googleMap;

                        map2.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                            @Override
                            public void onMapClick(LatLng latLng) {
                                map2.clear();
                                map2.addMarker(new MarkerOptions().position(latLng).title(address.getText().toString()));

                                la = latLng.latitude;
                                lo = latLng.longitude;
                                Toast.makeText(getActivity(), R.string.youchooseplace, Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
        return root;
    }

    private void iniui(View root) {
        setSharedPreference = new ListSharePreference.Set(OrderFragment.this.getActivity().getApplicationContext());
        getSharedPreference = new ListSharePreference.Get(OrderFragment.this.getActivity().getApplicationContext());
        city = root.findViewById(R.id.city_spinner);
        disc = root.findViewById(R.id.distinct_spinner);
        depertment = root.findViewById(R.id.dept_spinner);
        bathroom = root.findViewById(R.id.bathroom_spinner);
        bedroom = root.findViewById(R.id.bedroom_spinner);
        typespinner = root.findViewById(R.id.type_spinner);
        gragespinner = root.findViewById(R.id.garage_spinner);
        submit = root.findViewById(R.id.submitBtn);
        address = root.findViewById(R.id.addressET);
        price = root.findViewById(R.id.priceET);
        area = root.findViewById(R.id.areaET);
        linearLayout = root.findViewById(R.id.lin4);
        progressBar = root.findViewById(R.id.progerss3);

        try {
            try {
                initServiceCity();
            } catch (Exception e) {
                Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            iniservicedepert();
            iniTypeSpinner();
            iniGarageSpinner();
            iniRoomSpinner();
            iniBathroomSpinner();
            inibutton();
        } catch (Exception e) {

        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {

                    check = 1;

                } else {

                    check = 0;

                }
            }

        });

    }

    private void inibutton() {

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (type1 == -1 || discirt1 == 0 || depertt1 == -1 || idcity1 == 0 || bed1 == -1 || bath1 == -1 || grage1 == -1 || address == null || price == null || area == null) {

                    Toast.makeText(getActivity(), R.string.pleasecompletform, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                } else {
                    try {
                        progressBar.setVisibility(View.VISIBLE);
                        serivcebutton();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }

                }

            }
        });


    }

    private void serivcebutton() {
        String token = getSharedPreference.gettokenId();
        String pric = price.getText().toString();
        String add = address.getText().toString();
        String areaa = area.getText().toString();
        Api api = MyRetrofitClient.order().create(Api.class);
        Call<OrderModel> akarsModelCall = api.getorder(token, nameET.getText().toString(), String.valueOf(type), String.valueOf(depertt),
                String.valueOf(city_id), String.valueOf(discirt), String.valueOf(bed1), String.valueOf(bath1),
                String.valueOf(grage), areaa, pric, add, String.valueOf(check), String.valueOf(la), String.valueOf(lo));
        akarsModelCall.enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(@NonNull Call<OrderModel> call, @NonNull Response<OrderModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus().getType().equals("success")) {
                            Toast.makeText(getActivity(), response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent();
                            myIntent.setClassName("com.tkmsoft.akarat", MainActivity.class.getCanonicalName());
                            startActivity(myIntent);
                            getActivity().finish();
                        }else
                            Toast.makeText(getActivity(), ""+response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), R.string.fail, Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<OrderModel> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);

            }
        });


    }

    private void iniservicedepert() {

        Api api = MyRetrofitClient.categories().create(Api.class);
        Call<AkarsModel> akarsModelCall = api.getCategories();
        akarsModelCall.enqueue(new Callback<AkarsModel>() {
            @Override
            public void onResponse(@NonNull Call<AkarsModel> call, @NonNull Response<AkarsModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {
                        try {
                            iniDeptSpinner(response.body().getData().getCategories());
                        } catch (Exception e) {
                        }
                    } else {
                        Toast.makeText(getActivity(), R.string.notdata, Toast.LENGTH_SHORT).show();

                    }

                } else {

                    Toast.makeText(getActivity(), R.string.fail, Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<AkarsModel> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), R.string.errorconnection, Toast.LENGTH_LONG).show();
                t.printStackTrace();


            }
        });


    }


    private void initServiceCity() {

        Api api = MyRetrofitClient.categories().create(Api.class);
        Call<CityModel> loginModelCall = api.getcity();
        loginModelCall.enqueue(new Callback<CityModel>() {

            @Override
            public void onResponse(Call<CityModel> call, Response<CityModel> response) {

                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {
                        try {
                            iniCitySpinner(response.body().getData().getCities());
                        } catch (Exception e) {

                        }

                    } else {


                        Toast.makeText(getActivity(), R.string.notdata, Toast.LENGTH_SHORT).show();

                    }

                } else {


                    Toast.makeText(getActivity(), R.string.fail, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CityModel> call, Throwable t) {


                Toast.makeText(getActivity(), R.string.errorconnection, Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });


    }

    private void iniCitySpinner(final ArrayList<CityModel.DataBean.CitiesBean> cities) {
        cityArrayList = new ArrayList<>();
        //cityArrayList.clear();
        cityArrayList.add(0, getString(R.string.choosecit));

        for (int i = 0; i < cities.size(); i++) {
            cityArrayList.add(cities.get(i).getName());
        }

        spinnerAdapter = new SpinnerCityAdapter(getActivity(), cityArrayList);
        city.setAdapter(spinnerAdapter);
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idcity1 = i;
                if (i > 0) {
                    city_pos = i;
                    city_id = cities.get(i - 1).getId();
                    iniservicedicrit();
                }
                if (i == 0) {
                    linearLayout.setVisibility(LinearLayout.GONE);
                } else {
                    linearLayout.setVisibility(LinearLayout.VISIBLE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void iniservicedicrit() {
        Api api = MyRetrofitClient.categories().create(Api.class);
        Call<CityModel> loginModelCall = api.getcity();
        loginModelCall.enqueue(new Callback<CityModel>() {

            @Override
            public void onResponse(Call<CityModel> call, Response<CityModel> response) {

                if (response.isSuccessful()) {

                    if (response.body().getData() != null) {

                        iniDistinctSpinner(response.body().getData().getCities().get(city_pos - 1).getDisricts());

                    } else {
                        Toast.makeText(getActivity(), "not data", Toast.LENGTH_SHORT).show();
                    }

                } else {


                    Toast.makeText(getActivity(), "fail", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CityModel> call, Throwable t) {


                Toast.makeText(getActivity(), "error connection", Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });
    }


    private void iniGarageSpinner() {
        String[] typeStringNames = getResources().getStringArray(R.array.garage);
        ArrayList<GrageModel> countriesModelsList = new ArrayList<>();
        for (String countries : typeStringNames) {
            GrageModel item = new GrageModel(countries);
            countriesModelsList.add(item);
        }
        GrageAdapter grageAdapter = new GrageAdapter(getActivity(), countriesModelsList);
        gragespinner.setAdapter(grageAdapter);
        gragespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                i = i - 1;
                grage1 = i;
                if (i != -1) {
                    grage = i;
                } else {

                    grage = 0;
                }


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
        BathroomAdapter bathroomAdapter = new BathroomAdapter(getActivity(), countriesModelsList);
        bathroom.setAdapter(bathroomAdapter);
        bathroom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bath1 = i;
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
        BedroomAdapter bedroomAdapter = new BedroomAdapter(getActivity(), countriesModelsList);
        bedroom.setAdapter(bedroomAdapter);
        bedroom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                bed1 = i;


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void iniDeptSpinner(final ArrayList<AkarsModel.DataBean.CategoriesBean> categories) {


        depert = new ArrayList<>();
        depert.add(0, getString(R.string.choosedepartment));

        for (int i = 0; i < categories.size(); i++) {
            depert.add(categories.get(i).getName());
        }
        spinnerDepertmentAdapter = new SpinnerDepertmentAdapter(getActivity(), depert);
        depertment.setAdapter(spinnerDepertmentAdapter);
        depertment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                i = i - 1;
                depertt1 = i;
                if (i != -1) {
                    depertt = categories.get(i).getId();
                } else {

                    depertt = categories.get(0).getId();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


    private void iniTypeSpinner() {
        String[] typeStringNames = getResources().getStringArray(R.array.type);
        ArrayList<typeModel> countriesModelsList = new ArrayList<>();
        for (String countries : typeStringNames) {
            typeModel item = new typeModel(countries);
            countriesModelsList.add(item);
        }
        TypeAdapter typeAdapter = new TypeAdapter(getActivity(), countriesModelsList);
        typespinner.setAdapter(typeAdapter);
        typespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                i = i - 1;
                type1 = i;
                if (i != -1) {
                    type = i;
                } else {

                    type = 0;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
        spinnerAdapter1 = new SpinnerDiscirt(getActivity(), disccArrayList);
        disc.setAdapter(spinnerAdapter1);
        disc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                discirt1 = i;
                if (i > 0) {
                    discirt_pos = i;
                    discirt = disricts.get(i - 1).getId();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        try {
            mMainViewsCallBack = (IoCallBack) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "error");
        }

//        fireBackButtonEvent();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMainViewsCallBack = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMainViewsCallBack.setFilterBtn(false);
        mMainViewsCallBack.setCallBackTitle(getString(R.string.request_a_building));
    }


}
