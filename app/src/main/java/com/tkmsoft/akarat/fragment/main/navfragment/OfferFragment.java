package com.tkmsoft.akarat.fragment.main.navfragment;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;
import com.tkmsoft.akarat.activities.MainActivity;
import com.tkmsoft.akarat.R;
import com.tkmsoft.akarat.adapter.SpinnerCityAdapter;
import com.tkmsoft.akarat.adapter.SpinnerDepertmentAdapter;
import com.tkmsoft.akarat.adapter.SpinnerDiscirt;
import com.tkmsoft.akarat.fragment.main.home.HomeFragment;
import com.tkmsoft.akarat.network.api.Api;
import com.tkmsoft.akarat.interfaces.MainViewCallBack;
import com.tkmsoft.akarat.model.AddModel;
import com.tkmsoft.akarat.model.AkarsModel;
import com.tkmsoft.akarat.model.CityModel;
import com.tkmsoft.akarat.network.MyRetrofitClient;
import com.tkmsoft.akarat.util.BaseBackPressedListener;
import com.tkmsoft.akarat.util.InitSpinner;
import com.tkmsoft.akarat.util.ListSharePreference;
import com.tkmsoft.akarat.util.MoveToFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfferFragment extends Fragment {
    private static final int IMG_CODE1 = 10001;
    private static final int IMG_CODE2 = 10002;
    private static final int IMG_CODE3 = 10003;
    protected static String image_path1, image_path2, image_path3;
    SpinnerCityAdapter spinnerAdapter;
    ArrayList<String> cityArrayList, disccArrayList, depert;
    SpinnerDiscirt spinnerAdapter1;
    SpinnerDepertmentAdapter spinnerDepertmentAdapter;
    Spinner citySpinner, distinctSpinner, deptSpinner, bathRoomSpinner, bedRoomSpinner, typespinner, garageSpinner;
    EditText addressET, priceET, areaET, aboutET;
    Button submit;
    Button bmap;
    int check;
    ImageView imageView1, imageView2, imageView3;
    ListSharePreference.Set setSharedPreference;
    ListSharePreference.Get getSharedPreference;
    CheckBox checkBox;
    private ProgressBar progressBar;
    private MainViewCallBack mMainViewsCallBack;
    MapView mapView;
    GoogleMap map2;
    double la = 0, lo = 0;
    LinearLayout linearLayout;
    @BindView(R.id.nameET)
    EditText nameET;
    private FragmentActivity mContext;
    private String mLanguage;
    private Api apiBase, apiShow;
    private MoveToFragment moveToFragment;
    private InitSpinner initSpinner;
    private Bundle bundle;
    ArrayList<String> cityList, discList, deptList, typeList, garageList, bathRoomsList, bedRoomList;
    ArrayList<Integer> cityIdList, distinctIdList, deptIdList;
    int distinctCode, deptCode, bathRoomCode, bedCode, typeCode, garageCode, cityCode;
    private ArrayList<CityModel.DataBean.CitiesBean> citiesModelList;
    @BindView(R.id.distLinearLayout)
    LinearLayout distLinearLayout;
    private String TAG = getClass().getSimpleName();

    public OfferFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLanguage = getSharedPreference.getLanguage();
        apiBase = MyRetrofitClient.getBase().create(Api.class);
        apiShow = MyRetrofitClient.getShow().create(Api.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_offer, container, false);
        ButterKnife.bind(this, root);
        bundle = savedInstanceState;
        initUI(root);
        return root;
    }

    private void initUI(View root) {
       setViews(root);
        setSpinners();


        imageView1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pickImage(IMG_CODE1);
                    }
                }
        );


        imageView2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pickImage(IMG_CODE2);

                    }
                }
        );

        imageView3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pickImage(IMG_CODE3);

                    }
                }
        );
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
        aboutET = root.findViewById(R.id.descriptionET);
        imageView1 = root.findViewById(R.id.img1_item);
        imageView2 = root.findViewById(R.id.img2_item);
        imageView3 = root.findViewById(R.id.img3_item);
        checkBox = root.findViewById(R.id.checkBox);
        progressBar = root.findViewById(R.id.progerss2);
        bmap = root.findViewById(R.id.mapBtn);
        linearLayout = root.findViewById(R.id.distLinearLayout);
    }

    private void submitAddItem() {
        if (image_path1 != null && image_path2 == null && image_path3 == null) {
            Toast.makeText(getActivity(), R.string.choose_2_imgs, Toast.LENGTH_SHORT).show();
        } else if (image_path1 != null && image_path2 != null && image_path3 == null) {
            serverOffer2();
        } else if (image_path1 != null && image_path2 != null && image_path3 != null) {
            serverOfferAll();
        } else if (image_path2 == null && image_path1 != null && image_path3 != null) {
            Toast.makeText(mContext, R.string.choose_1and_2, Toast.LENGTH_SHORT).show();
        } else if (image_path1 == null && image_path2 != null && image_path3 != null) {
            Toast.makeText(mContext, R.string.choose_1and_2, Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.closeImg1)
    protected void onClose1Click() {
        image_path1 = null;
        Picasso.get().load(R.drawable.place_holder).into(imageView1);
    }

    @OnClick(R.id.closeImg2)
    protected void onClose2Click() {
        image_path2 = null;
        Picasso.get().load(R.drawable.place_holder).into(imageView2);
    }

    @OnClick(R.id.closeImg3)
    protected void onClose3Click() {
        image_path3 = null;
        Picasso.get().load(R.drawable.place_holder).into(imageView3);
    }


    public static String getRealPathFromUri(Uri contentURI, Context context) {
        try {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(contentURI, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imagePath = cursor.getString(columnIndex);
            cursor.close();
            return imagePath;
        } catch (Exception ignored) {
            return null;
        }
    }

    private void pickImage(int keyImg) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, keyImg);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_CODE1 && resultCode == RESULT_OK && data != null) {
            try {
                Uri uri = data.getData();
                image_path1 = getRealPathFromUri(uri, getActivity());
                showImageInView(uri, IMG_CODE1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (requestCode == IMG_CODE2 && resultCode == RESULT_OK && data != null) {
            try {
                Uri uri = data.getData();
                image_path2 = getRealPathFromUri(uri, getActivity());
                showImageInView(uri, IMG_CODE2);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        if (requestCode == IMG_CODE3 && resultCode == RESULT_OK && data != null) {
            try {
                Uri uri = data.getData();
                image_path3 = getRealPathFromUri(uri, getActivity());
                showImageInView(uri, IMG_CODE3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//end onActivityResult

    private void showImageInView(Uri image_path, int imgCode) {
        if (imgCode == IMG_CODE1)
            Picasso.get().load(image_path).into(imageView1);
        else if (imgCode == IMG_CODE2)
            Picasso.get().load(image_path).into(imageView2);
        else if (imgCode == IMG_CODE3)
            Picasso.get().load(image_path).into(imageView3);
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mContext.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            112);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                // Permission has already been granted
                submitAddItem();
            }
        } else {
            submitAddItem();
        }
    }


    private void serverOfferAll() {
        progressBar.setVisibility(View.VISIBLE);
        String name = getSharedPreference.getname();
        String token = getSharedPreference.gettokenId();
        String pric = priceET.getText().toString();
        String add = addressET.getText().toString();
        String areaa = areaET.getText().toString();
        String aboutt = aboutET.getText().toString();
        File file1;
        file1 = new File(image_path1);
        File file2;
        file2 = new File(image_path2);
        File file3;
        file3 = new File(image_path3);
        RequestBody mFile1 = RequestBody.create(MediaType.parse("image/*"), file1);
        RequestBody mFile2 = RequestBody.create(MediaType.parse("image/*"), file2);
        RequestBody mFile3 = RequestBody.create(MediaType.parse("image/*"), file3);
        RequestBody token1 = RequestBody.create(MediaType.parse("text/plain"), token);
        RequestBody name1 = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody type1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(typeCode));
        RequestBody depertt1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(deptCode));
        RequestBody city_id1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(cityCode));
        RequestBody discirt1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(distinctCode));
        RequestBody bed2 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(bedCode));
        RequestBody bath2 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(bathRoomCode));
        RequestBody grage1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(garageCode));
        RequestBody areaa1 = RequestBody.create(MediaType.parse("text/plain"), areaa);
        RequestBody price = RequestBody.create(MediaType.parse("text/plain"), pric);
        RequestBody add1 = RequestBody.create(MediaType.parse("text/plain"), add);
        RequestBody condition = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(check));
        RequestBody lat = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(la));
        RequestBody long1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(lo));
        RequestBody about1 = RequestBody.create(MediaType.parse("text/plain"), aboutt);
        MultipartBody.Part image1 = MultipartBody.Part.createFormData("imgs[0]", file1.getName(), mFile1);
        MultipartBody.Part image2 = MultipartBody.Part.createFormData("imgs[1]", file2.getName(), mFile2);
        MultipartBody.Part image3 = MultipartBody.Part.createFormData("imgs[2]", file3.getName(), mFile3);
        Api api = MyRetrofitClient.getShow().create(Api.class);
        Call<AddModel> akarsModelCall = api.offerAkarAll(token1, name1, type1, depertt1, city_id1, discirt1, bed2, bath2, grage1, areaa1, price, add1, condition, lat, long1, about1, image1, image2, image3);
        akarsModelCall.enqueue(new Callback<AddModel>() {
            @Override
            public void onResponse(@NonNull Call<AddModel> call, @NonNull Response<AddModel> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus().getType().equals("success")) {
                            Toast.makeText(getActivity(), response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                            moveToFragment.moveInMain(new HomeFragment());

                        } else
                            Toast.makeText(getActivity(), response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<AddModel> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), R.string.errorconnection, Toast.LENGTH_LONG).show();
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void serverOffer2() {
        progressBar.setVisibility(View.VISIBLE);
        String name = getSharedPreference.getname();
        String token = getSharedPreference.gettokenId();
        String pric = priceET.getText().toString();
        String add = addressET.getText().toString();
        String areaa = areaET.getText().toString();
        String aboutt = aboutET.getText().toString();
        File file1;
        file1 = new File(image_path1);
        File file2;
        file2 = new File(image_path2);
        RequestBody mFile1 = RequestBody.create(MediaType.parse("image/*"), file1);
        RequestBody mFile2 = RequestBody.create(MediaType.parse("image/*"), file2);
        RequestBody token1 = RequestBody.create(MediaType.parse("text/plain"), token);
        RequestBody name1 = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody type1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(typeCode));
        RequestBody depertt1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(deptCode));
        RequestBody city_id1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(cityCode));
        RequestBody discirt1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(distinctCode));
        RequestBody bed2 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(bedCode));
        RequestBody bath2 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(bathRoomCode));
        RequestBody grage1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(garageCode));
        RequestBody areaa1 = RequestBody.create(MediaType.parse("text/plain"), areaa);
        RequestBody price = RequestBody.create(MediaType.parse("text/plain"), pric);
        RequestBody add1 = RequestBody.create(MediaType.parse("text/plain"), add);
        RequestBody condition = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(check));
        RequestBody lat = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(la));
        RequestBody long1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(lo));
        RequestBody about1 = RequestBody.create(MediaType.parse("text/plain"), aboutt);
        MultipartBody.Part image1 = MultipartBody.Part.createFormData("imgs[0]", file1.getName(), mFile1);
        MultipartBody.Part image2 = MultipartBody.Part.createFormData("imgs[1]", file2.getName(), mFile2);
        Api api = MyRetrofitClient.getShow().create(Api.class);
        Call<AddModel> akarsModelCall = api.offerAkar2(token1, name1, type1, depertt1, city_id1, discirt1, bed2, bath2, grage1, areaa1, price, add1, condition, lat, long1, about1, image1, image2);
        akarsModelCall.enqueue(new Callback<AddModel>() {
            @Override
            public void onResponse(@NonNull Call<AddModel> call, @NonNull Response<AddModel> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus().getType().equals("success")) {
                            Toast.makeText(getActivity(), response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                            moveToFragment.moveInMain(new HomeFragment());

                        } else
                            Toast.makeText(getActivity(), response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<AddModel> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);
            }
        });
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
                                                        checkPermissions();
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
    public void onPause() {
        callGetCity().cancel();
        callGetDept().cancel();
        super.onPause();
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
        mMainViewsCallBack.setCallBackTitle(getString(R.string.offer));
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
