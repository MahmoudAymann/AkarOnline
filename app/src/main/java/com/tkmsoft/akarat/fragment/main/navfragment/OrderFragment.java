package com.tkmsoft.akarat.fragment.main.navfragment;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
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
import com.tkmsoft.akarat.activities.MainActivity;
import com.tkmsoft.akarat.R;
import com.tkmsoft.akarat.adapter.BathroomAdapter;
import com.tkmsoft.akarat.adapter.BedroomAdapter;
import com.tkmsoft.akarat.adapter.GrageAdapter;
import com.tkmsoft.akarat.adapter.SpinnerCityAdapter;
import com.tkmsoft.akarat.adapter.SpinnerDepertmentAdapter;
import com.tkmsoft.akarat.adapter.SpinnerDiscirt;
import com.tkmsoft.akarat.fragment.main.home.HomeFragment;
import com.tkmsoft.akarat.network.api.Api;
import com.tkmsoft.akarat.interfaces.MainViewCallBack;
import com.tkmsoft.akarat.model.AkarsModel;
import com.tkmsoft.akarat.model.BathroomModel;
import com.tkmsoft.akarat.model.BedroomModel;
import com.tkmsoft.akarat.model.CityModel;
import com.tkmsoft.akarat.model.GrageModel;
import com.tkmsoft.akarat.model.OrderModel;
import com.tkmsoft.akarat.network.MyRetrofitClient;
import com.tkmsoft.akarat.util.BaseBackPressedListener;
import com.tkmsoft.akarat.util.InitSpinner;
import com.tkmsoft.akarat.util.ListSharePreference;
import com.tkmsoft.akarat.util.MoveToFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    SpinnerCityAdapter spinnerAdapter;
    SpinnerDiscirt spinnerAdapter1;
    SpinnerDepertmentAdapter spinnerDepertmentAdapter;
    Spinner citySpinner, distinctSpinner, deptSpinner, bathroom, bedroom, typespinner, gragespinner;
    int city_pos;
    int distinctCode, dept, bath, bed, type, grage;
    int discirt1, depertt1, bath1, bed1, type1, grage1, idcity1;
    EditText address, price, area;
    Button submit;
    private int cityCode;
    private int discirt_pos;
    ListSharePreference.Set setSharedPreference;
    ListSharePreference.Get getSharedPreference;
    MainViewCallBack mMainViewsCallBack;
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
    private FragmentActivity mContext;
    private String mLanguage;
    private Api apiBase;
    private MoveToFragment moveToFragment;
    private InitSpinner initSpinner;
    ArrayList<String> cityList, discList, deptList, typeList;
    ArrayList<Integer> cityIdList, distinctIdList, deptIdList, typeIdList;
    private ArrayList<CityModel.DataBean.CitiesBean> citiesModelList;
    @BindView(R.id.distLinearLayout)
    LinearLayout distLinearLayout;

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        if (context instanceof FragmentActivity)
            mContext = (FragmentActivity) context;
        try {
            mMainViewsCallBack = (MainViewCallBack) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "error");
        }
        super.onAttach(context);
        setSharedPreference = new ListSharePreference.Set(mContext);
        getSharedPreference = new ListSharePreference.Get(mContext);
        moveToFragment = new MoveToFragment(mContext);
        initSpinner = new InitSpinner(mContext);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLanguage = getSharedPreference.getLanguage();
        apiBase = MyRetrofitClient.getBase().create(Api.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, root);
        mapView.onCreate(savedInstanceState);
        iniui(root);

        return root;
    }

    private void iniui(View root) {
        setViews(root);


        serverCity();
        serviceDept();
        iniTypeSpinner();
        iniGarageSpinner();
        iniRoomSpinner();
        iniBathroomSpinner();
        inibutton();

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

    private void setViews(View root) {
        citySpinner = root.findViewById(R.id.city_spinner);
        distinctSpinner = root.findViewById(R.id.distinct_spinner);
        deptSpinner = root.findViewById(R.id.dept_spinner);
        bathroom = root.findViewById(R.id.bathroom_spinner);
        bedroom = root.findViewById(R.id.bedroom_spinner);
        typespinner = root.findViewById(R.id.type_spinner);
        gragespinner = root.findViewById(R.id.garage_spinner);
        submit = root.findViewById(R.id.submitBtn);
        address = root.findViewById(R.id.addressET);
        price = root.findViewById(R.id.priceET);
        area = root.findViewById(R.id.areaET);
        linearLayout = root.findViewById(R.id.distLinearLayout);
        progressBar = root.findViewById(R.id.progerss3);
    }

    @OnClick(R.id.mapBtn)
    public void onMapClick() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_map, null);
        mapView = mView.findViewById(R.id.map1);

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
        Api api = MyRetrofitClient.getOrder().create(Api.class);
        Call<OrderModel> akarsModelCall = api.getorder(token, nameET.getText().toString(), String.valueOf(type), String.valueOf(dept),
                String.valueOf(cityCode), String.valueOf(distinctCode), String.valueOf(bed1), String.valueOf(bath1),
                String.valueOf(grage), areaa, pric, add, String.valueOf(check), String.valueOf(la), String.valueOf(lo));
        akarsModelCall.enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(@NonNull Call<OrderModel> call, @NonNull Response<OrderModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus().getType().equals("success")) {
                            Toast.makeText(getActivity(), response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();

                            moveToFragment.moveInMain(new HomeFragment());

                        } else
                            Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), R.string.fail, Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<OrderModel> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);

            }
        });
    }

    //Dept
    private void serviceDept() {
        callGetDept().enqueue(new Callback<AkarsModel>() {
            @Override
            public void onResponse(@NonNull Call<AkarsModel> call, @NonNull Response<AkarsModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    AkarsModel.StatusBean statusBean = response.body().getStatus();
                    if (statusBean != null) {
                        if (statusBean.getType().equals("success")) {
                            AkarsModel.DataBean dataBean = response.body().getData();
                            if (dataBean != null) {
                                List<AkarsModel.DataBean.CategoriesBean> categoriesList = dataBean.getCategories();
                                if (categoriesList != null) {
                                    for (int i = 0; i < categoriesList.size(); i++) {
                                        deptList.add(categoriesList.get(i).getName());
                                        deptIdList.add(categoriesList.get(i).getId());
                                    }
                                    initDeptSpinner();
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AkarsModel> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private Call<AkarsModel> callGetDept() {
        return apiBase.getCategories();
    }

    private void initDeptSpinner() {
        initSpinner.setSpinner(deptSpinner, deptList).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    dept = deptIdList.get(i);
                } else {
                    dept = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
////////////////////////


    //City
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
                                    iniCitySpinner();
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

    private void iniCitySpinner() {
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    cityCode = cityIdList.get(i);
                    distLinearLayout.setVisibility(View.VISIBLE);
                    initDistinctSpinner();
                } else {
                    cityCode = -1;
                    distinctCode = -1;
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
            if (cityCode == citiesModelList.get(i).getId()) {
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
                    distinctCode = distinctIdList.get(i);
                } else {
                    distinctCode = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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


    private void iniTypeSpinner() {//no3
        String[] typeStringNames = getResources().getStringArray(R.array.type);
        typeList = new ArrayList<>(Arrays.asList(typeStringNames));


        initSpinner.setSpinner(typespinner, typeList).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    type = i - 1;
                } else {
                    type = -1;
                }
            }
        });


    }

    private void iniDistinctSpinner(
            final ArrayList<CityModel.DataBean.CitiesBean.DisrictsBean> disricts) {
        discList = new ArrayList<>();
        discList.clear();
        discList.add(0, getString(R.string.chooseDistrict));

        for (int i = 0; i < disricts.size(); i++) {
            discList.add(disricts.get(i).getName());
        }
        spinnerAdapter1 = new SpinnerDiscirt(getActivity(), discList);
        distinctSpinner.setAdapter(spinnerAdapter1);
        distinctSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                discirt1 = i;
                if (i > 0) {
                    discirt_pos = i;
                    distinctCode = disricts.get(i - 1).getId();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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

    private void fireBackButtonEvent() {
        ((MainActivity) mContext).setOnBackPressedListener(new BaseBackPressedListener(mContext) {
            @Override
            public void onBackPressed() {
                moveToFragment.moveInMain(new HomeFragment());
            }
        });
    }//end back pressed

}
