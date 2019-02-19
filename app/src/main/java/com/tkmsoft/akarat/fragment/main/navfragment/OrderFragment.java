package com.tkmsoft.akarat.fragment.main.navfragment;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.tkmsoft.akarat.activities.MainActivity;
import com.tkmsoft.akarat.R;
import com.tkmsoft.akarat.fragment.main.home.HomeFragment;
import com.tkmsoft.akarat.network.api.Api;
import com.tkmsoft.akarat.interfaces.MainViewCallBack;
import com.tkmsoft.akarat.model.AkarsModel;
import com.tkmsoft.akarat.model.CityModel;
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

    Spinner citySpinner, distinctSpinner, deptSpinner, bathRoomSpinner, bedRoomSpinner, typespinner, garageSpinner;
    int distinctCode, deptCode, bathRoomCode, bedCode, typeCode, garageCode, cityCode;
    EditText addressET, priceET, areaET;
    Button submit;
    ListSharePreference.Set setSharedPreference;
    ListSharePreference.Get getSharedPreference;
    MainViewCallBack mMainViewsCallBack;
    private ProgressBar progressBar;
    LinearLayout linearLayout;
    MapView mapView;
    GoogleMap map2;
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
    private Api apiBase, apiOrder;
    private MoveToFragment moveToFragment;
    private InitSpinner initSpinner;
    ArrayList<String> cityList, discList, deptList, typeList, garageList, bathRoomsList, bedRoomList;
    ArrayList<Integer> cityIdList, distinctIdList, deptIdList;
    private ArrayList<CityModel.DataBean.CitiesBean> citiesModelList;
    @BindView(R.id.distLinearLayout)
    LinearLayout distLinearLayout;
    private Bundle bundle;
    private String TAG = getClass().getSimpleName();

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
        fireBackButtonEvent();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLanguage = getSharedPreference.getLanguage();
        apiBase = MyRetrofitClient.getBase().create(Api.class);
        apiOrder = MyRetrofitClient.getOrder().create(Api.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, root);
        initUI(root);
        bundle = savedInstanceState;
        return root;
    }

    private void initUI(View root) {
        setViews(root);
        setSpinners();
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

    private void setSpinners() {
        //city
        cityList = new ArrayList<>();
        cityList.add(getString(R.string.city));
        cityIdList = new ArrayList<>();
        cityIdList.add(0);
        initCitySpinner();
        serverCity();

        //type
        initTypeSpinner();

        //category
        deptList = new ArrayList<>();
        deptList.add(getString(R.string.dept));
        deptIdList = new ArrayList<>();
        deptIdList.add(0);
        initDeptSpinner();
        serviceDept();

        //garage
        initGarageSpinner();

        //bath
        initBathroomSpinner();

        //bedrooms
        initRoomSpinner();
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
                    typeCode = i - 1;
                } else {
                    typeCode = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    private void setViews(View root) {
        citySpinner = root.findViewById(R.id.city_spinner);
        distinctSpinner = root.findViewById(R.id.distinct_spinner);
        deptSpinner = root.findViewById(R.id.dept_spinner);
        bathRoomSpinner = root.findViewById(R.id.bathroom_spinner);
        bedRoomSpinner = root.findViewById(R.id.bedroom_spinner);
        typespinner = root.findViewById(R.id.type_spinner);
        garageSpinner = root.findViewById(R.id.garage_spinner);
        submit = root.findViewById(R.id.submitBtn);
        addressET = root.findViewById(R.id.addressET);
        priceET = root.findViewById(R.id.priceET);
        areaET = root.findViewById(R.id.areaET);
        linearLayout = root.findViewById(R.id.distLinearLayout);
        progressBar = root.findViewById(R.id.progerss3);
    }

    @OnClick(R.id.mapBtn)
    public void onMapClick() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_map, null);
        mapView = mView.findViewById(R.id.map1);
        mapView.onCreate(bundle);

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
                        map2.addMarker(new MarkerOptions().position(latLng).title(addressET.getText().toString()));

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

    @OnClick(R.id.submitBtn)
    void onSubmitClick() {
        if (isEditTxtEmpty(nameET)) {
            if (typeCode != -1) {
                if (deptCode != -1) {
                    if (cityCode != -1) {
                        if (distinctCode != -1) {
                            if (isEditTxtEmpty(addressET)) {
                                if (isEditTxtEmpty(priceET)) {
                                    if (bathRoomCode != -1) {
                                        if (bedCode != -1) {
                                            if (garageCode != -1) {
                                                if (isEditTxtEmpty(areaET)) {
                                                    if (check == 1)
                                                        serverSend();
                                                    else
                                                        Toast.makeText(mContext, "" + getString(R.string.check_privacy), Toast.LENGTH_SHORT).show();
                                                } else
                                                    Toast.makeText(mContext, "" + getString(R.string.enter_area), Toast.LENGTH_SHORT).show();
                                            } else
                                                Toast.makeText(mContext, "" + getString(R.string.enter_garage), Toast.LENGTH_SHORT).show();
                                        } else
                                            Toast.makeText(mContext, "" + getString(R.string.choose_bedroom), Toast.LENGTH_SHORT).show();
                                    } else
                                        Toast.makeText(mContext, "" + getString(R.string.choose_bathroom), Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(mContext, "" + getString(R.string.enter_price), Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(mContext, "" + getString(R.string.enter_address), Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(mContext, "" + getString(R.string.chooseDistrict), Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(mContext, "" + getString(R.string.enter_city), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(mContext, "" + getString(R.string.choosedepartment), Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(mContext, "" + getString(R.string.choosetype), Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(mContext, "" + getString(R.string.enter_name), Toast.LENGTH_SHORT).show();
    }

    private boolean isEditTxtEmpty(EditText etText) {
        return etText.getText().toString().trim().length() != 0;
    }

    private void serverSend() {
        progressBar.setVisibility(View.VISIBLE);
        callSendOrder().enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(@NonNull Call<OrderModel> call, @NonNull Response<OrderModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus().getType().equals("success")) {
                            Toast.makeText(mContext, response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();

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
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);

            }
        });
    }

    private Call<OrderModel> callSendOrder() {
        String token = getSharedPreference.gettokenId();
        String price = priceET.getText().toString();
        String address = addressET.getText().toString();
        String area = areaET.getText().toString();
        String name = nameET.getText().toString();
        Log.d(TAG, "callSendOrder: " + "\nname: " + name + "\ntype: " + typeCode + "\ndept: " + deptCode +
                "\ncity: " + cityCode + "\ndistinct: " + distinctCode + "\nbed: " + bedCode + "\nbath: " + bathRoomCode +
                "\ngarage: " + garageCode + "\nareaET: " + area + "\npriceET: " + price + "\naddressET: " + address +
                "\ncheck: " + check + "\nlat: " + la + "\nlong: " + lo);

        return apiOrder.getorder(token, name, typeCode, deptCode,
                cityCode, distinctCode, bedCode, bathRoomCode,
                garageCode, area, price, address, String.valueOf(check), la, lo);
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
                    deptCode = deptIdList.get(i);
                } else {
                    deptCode = -1;
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

    private void initCitySpinner() {
        initSpinner.setSpinner(citySpinner, cityList).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void initGarageSpinner() {
        String[] typeStringNames = getResources().getStringArray(R.array.garage);
        garageList = new ArrayList<>();
        garageList.add(getString(R.string.grage));
        garageList.addAll(Arrays.asList(typeStringNames));


        initSpinner.setSpinner(garageSpinner, garageList).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    garageCode = i - 1;
                } else {
                    garageCode = -1;
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
                    bathRoomCode = i;
                } else {
                    bathRoomCode = -1;
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
                    bedCode = i;
                } else {
                    bedCode = -1;
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
