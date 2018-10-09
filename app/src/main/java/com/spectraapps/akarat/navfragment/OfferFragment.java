package com.spectraapps.akarat.navfragment;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
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

import com.spectraapps.akarat.interfaces.IoCallBack;
import com.spectraapps.akarat.MainActivity;
import com.spectraapps.akarat.R;
import com.spectraapps.akarat.adapter.BathroomAdapter;
import com.spectraapps.akarat.adapter.BedroomAdapter;
import com.spectraapps.akarat.adapter.GrageAdapter;
import com.spectraapps.akarat.adapter.SpinnerCityAdapter;
import com.spectraapps.akarat.adapter.SpinnerDepertmentAdapter;
import com.spectraapps.akarat.adapter.SpinnerDiscirt;
import com.spectraapps.akarat.adapter.TypeAdapter;
import com.spectraapps.akarat.api.Api;
import com.spectraapps.akarat.model.AddModel;
import com.spectraapps.akarat.model.AkarsModel;
import com.spectraapps.akarat.model.BathroomModel;
import com.spectraapps.akarat.model.BedroomModel;
import com.spectraapps.akarat.model.CityModel;
import com.spectraapps.akarat.model.GrageModel;
import com.spectraapps.akarat.model.typeModel;
import com.spectraapps.akarat.network.MyRetrofitClient;
import com.spectraapps.akarat.util.ListSharePreference;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
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
    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION};
    protected static String image_path1, image_path2, image_path3;
    SpinnerCityAdapter spinnerAdapter;
    ArrayList<String> cityArrayList, disccArrayList, depert;
    SpinnerDiscirt spinnerAdapter1;
    SpinnerDepertmentAdapter spinnerDepertmentAdapter;
    Spinner city, disc, depertment, bathroom, bedroom, typespinner, gragespinner;
    EditText address, price, area, about;
    Button submit;
    Button bmap;
    int check;
    ImageView imageView1, imageView2, imageView3;
    ListSharePreference.Set setSharedPreference;
    ListSharePreference.Get getSharedPreference;
    CheckBox checkBox;
    private ProgressBar progressBar;
    private IoCallBack mMainViewsCallBack;
    MapView mapView;
    GoogleMap map2;
    Marker marker;
    double la = 0, lo = 0;
    LinearLayout linearLayout;
    @BindView(R.id.nameET)
    EditText nameET;
    private FragmentActivity mContext;
    private String cityCode, garageCode, bathCode, roomsCode, deptCode, districtCode, typeCode;

    public OfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_offer, container, false);
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
        setSharedPreference = new ListSharePreference.Set(OfferFragment.this.getActivity().getApplicationContext());
        getSharedPreference = new ListSharePreference.Get(OfferFragment.this.getActivity().getApplicationContext());
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
        about = root.findViewById(R.id.descriptionET);
        imageView1 = root.findViewById(R.id.img1_item);
        imageView2 = root.findViewById(R.id.img2_item);
        imageView3 = root.findViewById(R.id.img3_item);
        checkBox = root.findViewById(R.id.checkBox);
        progressBar = root.findViewById(R.id.progerss2);
        bmap = root.findViewById(R.id.mapBtn);
        linearLayout = root.findViewById(R.id.lin4);
        try {
            initServiceCity();
            try {
                iniservicedepert();
            } catch (Exception ignore) {

            }
            iniTypeSpinner();
            iniGarageSpinner();
            iniRoomSpinner();
            iniBathroomSpinner();
            inibutton();
        } catch (Exception e) {

            e.printStackTrace();

        }
        imageView1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pickImage(IMG_CODE1);
                        checkPermissions();

                    }
                }
        );


        imageView2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pickImage(IMG_CODE2);
                        checkPermissions();

                    }
                }
        );

        imageView3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pickImage(IMG_CODE3);
                        checkPermissions();

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

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(getActivity(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[0]), 100);
            return false;
        }
        return true;
    }

    private void inibutton() {

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitAddItem();
            }
        });
    }


    private void serverOfferAll() {
        progressBar.setVisibility(View.VISIBLE);
        String name = getSharedPreference.getname();
        String token = getSharedPreference.gettokenId();
        String pric = price.getText().toString();
        String add = address.getText().toString();
        String areaa = area.getText().toString();
        String aboutt = about.getText().toString();
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
        RequestBody type1 = RequestBody.create(MediaType.parse("text/plain"), typeCode);
        RequestBody depertt1 = RequestBody.create(MediaType.parse("text/plain"), deptCode);
        RequestBody city_id1 = RequestBody.create(MediaType.parse("text/plain"), cityCode);
        RequestBody discirt1 = RequestBody.create(MediaType.parse("text/plain"), districtCode);
        RequestBody bed2 = RequestBody.create(MediaType.parse("text/plain"), roomsCode);
        RequestBody bath2 = RequestBody.create(MediaType.parse("text/plain"), bathCode);
        RequestBody grage1 = RequestBody.create(MediaType.parse("text/plain"), garageCode);
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
        Api api = MyRetrofitClient.show().create(Api.class);
        Call<AddModel> akarsModelCall = api.offerAkarAll(token1, name1, type1, depertt1, city_id1, discirt1, bed2, bath2, grage1, areaa1, price, add1, condition, lat, long1, about1, image1, image2, image3);
        akarsModelCall.enqueue(new Callback<AddModel>() {
            @Override
            public void onResponse(@NonNull Call<AddModel> call, @NonNull Response<AddModel> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus().getType().equals("success")) {
                            Toast.makeText(getActivity(), response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent();
                            myIntent.setClassName("com.spectraapps.akarat", MainActivity.class.getCanonicalName());
                            startActivity(myIntent);
                            getActivity().finish();
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
        String pric = price.getText().toString();
        String add = address.getText().toString();
        String areaa = area.getText().toString();
        String aboutt = about.getText().toString();
        File file1;
        file1 = new File(image_path1);
        File file2;
        file2 = new File(image_path2);
        RequestBody mFile1 = RequestBody.create(MediaType.parse("image/*"), file1);
        RequestBody mFile2 = RequestBody.create(MediaType.parse("image/*"), file2);
        RequestBody token1 = RequestBody.create(MediaType.parse("text/plain"), token);
        RequestBody name1 = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody type1 = RequestBody.create(MediaType.parse("text/plain"), typeCode);
        RequestBody depertt1 = RequestBody.create(MediaType.parse("text/plain"), deptCode);
        RequestBody city_id1 = RequestBody.create(MediaType.parse("text/plain"), cityCode);
        RequestBody discirt1 = RequestBody.create(MediaType.parse("text/plain"), districtCode);
        RequestBody bed2 = RequestBody.create(MediaType.parse("text/plain"), roomsCode);
        RequestBody bath2 = RequestBody.create(MediaType.parse("text/plain"), bathCode);
        RequestBody grage1 = RequestBody.create(MediaType.parse("text/plain"), garageCode);
        RequestBody areaa1 = RequestBody.create(MediaType.parse("text/plain"), areaa);
        RequestBody price = RequestBody.create(MediaType.parse("text/plain"), pric);
        RequestBody add1 = RequestBody.create(MediaType.parse("text/plain"), add);
        RequestBody condition = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(check));
        RequestBody lat = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(la));
        RequestBody long1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(lo));
        RequestBody about1 = RequestBody.create(MediaType.parse("text/plain"), aboutt);
        MultipartBody.Part image1 = MultipartBody.Part.createFormData("imgs[0]", file1.getName(), mFile1);
        MultipartBody.Part image2 = MultipartBody.Part.createFormData("imgs[1]", file2.getName(), mFile2);
        Api api = MyRetrofitClient.show().create(Api.class);
        Call<AddModel> akarsModelCall = api.offerAkar2(token1, name1, type1, depertt1, city_id1, discirt1, bed2, bath2, grage1, areaa1, price, add1, condition, lat, long1, about1, image1, image2);
        akarsModelCall.enqueue(new Callback<AddModel>() {
            @Override
            public void onResponse(@NonNull Call<AddModel> call, @NonNull Response<AddModel> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus().getType().equals("success")) {
                            Toast.makeText(getActivity(), response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent();
                            myIntent.setClassName("com.spectraapps.akarat", MainActivity.class.getCanonicalName());
                            startActivity(myIntent);
                            getActivity().finish();
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
            public void onResponse(@NonNull Call<CityModel> call, @NonNull Response<CityModel> response) {

                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {
                        try {
                            iniCitySpinner(response.body().getData().getCities());
                        } catch (Exception e) {

                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CityModel> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), R.string.errorconnection, Toast.LENGTH_SHORT).show();
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
        spinnerAdapter = new SpinnerCityAdapter(getActivity(), cityArrayList);
        city.setAdapter(spinnerAdapter);
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    linearLayout.setVisibility(LinearLayout.VISIBLE);
                    cityCode = String.valueOf(cities.get(i - 1).getId());
                    serverDistrict(i - 1);
                } else {
                    linearLayout.setVisibility(LinearLayout.GONE);
                    cityCode = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void serverDistrict(final int cityPos) {
        Api api = MyRetrofitClient.categories().create(Api.class);
        Call<CityModel> loginModelCall = api.getcity();
        loginModelCall.enqueue(new Callback<CityModel>() {

            @Override
            public void onResponse(Call<CityModel> call, Response<CityModel> response) {

                if (response.isSuccessful()) {

                    if (response.body().getData() != null) {
                        iniDistrictSpinner(response.body().getData().getCities().get(cityPos).getDisricts());
                    }
                }
            }

            @Override
            public void onFailure(Call<CityModel> call, Throwable t) {

                Toast.makeText(getActivity(), R.string.errorconnection, Toast.LENGTH_SHORT).show();
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
                if (i > 0)
                    garageCode = String.valueOf(i - 1);
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
        BedroomAdapter bedroomAdapter = new BedroomAdapter(getActivity(), countriesModelsList);
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
                if (i > 0)
                    deptCode = String.valueOf(categories.get(i - 1).getId());
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

    private void iniDistrictSpinner(final ArrayList<CityModel.DataBean.CitiesBean.DisrictsBean> disricts) {
        disccArrayList = new ArrayList<>();
        disccArrayList.add(0, getString(R.string.chooseDistrict));

        for (int i = 0; i < disricts.size(); i++) {
            disccArrayList.add(disricts.get(i).getName());
        }

        spinnerAdapter1 = new SpinnerDiscirt(getActivity(), disccArrayList);
        disc.setAdapter(spinnerAdapter1);
        disc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0)
                    districtCode = String.valueOf(disricts.get(i - 1).getId());
                else
                    districtCode = null;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public void onAttach(Activity context) {
        if (context instanceof FragmentActivity) {
            mContext = (FragmentActivity) context;
        }
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
        mMainViewsCallBack.setCallBackTitle(getString(R.string.offer));
    }
}
