package com.tkmsoft.akarat.fragment.main;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tkmsoft.akarat.R;
import com.tkmsoft.akarat.adapter.SliderIamgeAdapter;
import com.tkmsoft.akarat.network.api.Api;
import com.tkmsoft.akarat.interfaces.MainViewCallBack;
import com.tkmsoft.akarat.model.AkarsModel;
import com.tkmsoft.akarat.network.MyRetrofitClient;
import com.tkmsoft.akarat.util.ListSharePreference;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MdetailsFragment extends Fragment implements OnMapReadyCallback {
    MapView mapView;
    GoogleMap map;
    Marker marker;
    double lat;
    double lon;
    TextView textView, textView1, textView2, textView4, textView5, textView6, textView7, textView8, textView9;
    private ViewPager mPager;
    private static int currentPage = 0;
    ArrayList<AkarsModel.DataBean.CategoriesBean.AkarsBean.ImagesBean> image1 = new ArrayList<>();
    MainViewCallBack mMainViewsCallBack;
    String id;
    int id1;
    String addres;

    ListSharePreference.Set setSharedPreference;
    ListSharePreference.Get getSharedPreference;
    private ScrollView fullparentscrolling;
    @BindView(R.id.nameTV)
    TextView nameTV;
    @BindView(R.id.typeTV)
    TextView typeTV;
    @BindView(R.id.bathroomTV)
    TextView bathroomTV;

    public MdetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_mdetails, container, false);
        ButterKnife.bind(this, root);
        iniui(root);
        mapView = root.findViewById(R.id.map);
        fullparentscrolling = root.findViewById(R.id.scroll);
        mapView.onCreate(savedInstanceState);

        mapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(this);
        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // latitude and longitude
        map = googleMap;
        LatLng sydney = new LatLng(lat, lon);
        map.addMarker(new MarkerOptions().position(sydney).title(addres));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10.0f));


    }

    private void iniui(View root) {
        setSharedPreference = new ListSharePreference.Set(getActivity().getApplicationContext());
        getSharedPreference = new ListSharePreference.Get(getActivity().getApplicationContext());
        textView = root.findViewById(R.id.tx1);
        textView1 = root.findViewById(R.id.tx2);
        textView2 = root.findViewById(R.id.tx3);

        textView4 = root.findViewById(R.id.tx5);
        textView5 = root.findViewById(R.id.tx6);
        textView6 = root.findViewById(R.id.tx7);
        textView7 = root.findViewById(R.id.tx8);
        textView8 = root.findViewById(R.id.tx9);
        textView9 = root.findViewById(R.id.tx10);
        Bundle bundle = getArguments();
        String code = bundle.getString("code");
        String about = bundle.getString("about");
        String area = bundle.getString("area");
        String rooms = bundle.getString("rooms");
        String price = bundle.getString("price");
        String grage = bundle.getString("grage");
        String city = bundle.getString("city");
        String discirt = bundle.getString("discirt");
        String name = bundle.getString("name");
        addres = bundle.getString("addres");
        lat = Double.parseDouble(bundle.getString("lat"));
        lon = Double.parseDouble(bundle.getString("long"));
        if (name != null)
            nameTV.setText(name);
        String type = bundle.getString("type");
        typeTV.setText(type);
        String bathroom = bundle.getString("bathroom");
        bathroomTV.setText(bathroom);

        textView.setText(code);
        textView1.setText(about);
        textView2.setText(area);
        textView4.setText(rooms);
        textView5.setText(price);
        assert grage != null;
        if (grage.equals("0"))
            textView6.setText(getString(R.string.yes));
        else
            textView6.setText(getString(R.string.no));
        textView7.setText(city);
        textView8.setText(discirt);
        textView9.setText(addres);
        initService(root);

    }

    private void inislider(View root, final ArrayList<AkarsModel.DataBean.CategoriesBean.AkarsBean.ImagesBean> image) {

        mPager = root.findViewById(R.id.pager);
        CircleIndicator indicator = root.findViewById(R.id.indicator);

        mPager.setAdapter(new SliderIamgeAdapter(getActivity(), image, new SliderIamgeAdapter.PadgerListner() {
            @Override
            public void onItemViewClick(ArrayList<AkarsModel.DataBean.CategoriesBean.AkarsBean.ImagesBean> images, int position) {

            }
        }));

        indicator.setViewPager(mPager);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == image.size()) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1500, 3500);


    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        try {
            mMainViewsCallBack = (MainViewCallBack) context;
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
        mMainViewsCallBack.setCallBackTitle(getString(R.string.Details));
    }

    private void initService(final View root) {
        image1 = new ArrayList<>();
        id1 = getSharedPreference.getkId();
        id = getSharedPreference.getgId();
        Api api = MyRetrofitClient.getBase().create(Api.class);
        Call<AkarsModel> akarsModelCall = api.getCategories();
        akarsModelCall.enqueue(new Callback<AkarsModel>() {
            @Override
            public void onResponse(@NonNull Call<AkarsModel> call, @NonNull Response<AkarsModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {
                        image1.addAll(response.body().getData().getCategories().get(Integer.parseInt(id)).getAkars().get(id1).getImages());
                        inislider(root, image1);
                    }
                } else {

                    Toast.makeText(getActivity(), R.string.notdata, Toast.LENGTH_LONG).show();
                    //progressBar.setVisibility(View.INVISIBLE);
                }


            }

            @Override
            public void onFailure(@NonNull Call<AkarsModel> call, @NonNull Throwable t) {
                t.printStackTrace();


            }
        });


    }


}
