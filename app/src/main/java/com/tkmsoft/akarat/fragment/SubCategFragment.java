package com.tkmsoft.akarat.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.tkmsoft.akarat.R;
import com.tkmsoft.akarat.adapter.SubCategoryAdapter;
import com.tkmsoft.akarat.api.Api;
import com.tkmsoft.akarat.interfaces.IoCallBack;
import com.tkmsoft.akarat.model.AkarsModel;
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
public class SubCategFragment extends Fragment {

    private RecyclerView recycler;
    private IoCallBack mMainViewsCallBack;
    PullRefreshLayout pullRefreshLayout;

    ListSharePreference.Set setSharedPreference;
    ListSharePreference.Get getSharedPreference;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    String type, city, district, bathRoom, rooms, garage, priceMin, priceMax, distanceMin, distanceMax;
    boolean filterOn;

    public SubCategFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            filterOn = getArguments().getBoolean("filterOn");
            type = getArguments().getString("type");
            city = getArguments().getString("city");
            district = getArguments().getString("district");
            bathRoom = getArguments().getString("bathRoom");
            rooms = getArguments().getString("rooms");
            garage = getArguments().getString("garage");
//            priceMin = getArguments().getString("priceMin");
//            priceMax = getArguments().getString("priceMax");
//            distanceMin = getArguments().getString("areaMin");
//            distanceMax = getArguments().getString("areaMax");
        }
        //Toast.makeText(getActivity(), "after " + city, Toast.LENGTH_SHORT).show();

    }

    private void serverAkars() {
        progressBar.setVisibility(View.VISIBLE);
        final int categPos = Integer.parseInt(getSharedPreference.getgId());
        Api api = MyRetrofitClient.categories().create(Api.class);
        final ArrayList<AkarsModel.DataBean.CategoriesBean.AkarsBean> filteredList = new ArrayList<>();
        Call<AkarsModel> akarsModelCall = api.getCategories();

        akarsModelCall.enqueue(new Callback<AkarsModel>() {
            @Override
            public void onResponse(@NonNull Call<AkarsModel> call, @NonNull Response<AkarsModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus().getType().equals("success")) {
                            ArrayList<AkarsModel.DataBean.CategoriesBean.AkarsBean> akarsBean = response.body().getData().getCategories().get(categPos).getAkars();
                            if (!filterOn)
                                initAdapter(akarsBean);
                            else {
//                                Toast.makeText(getActivity(), "" + city, Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.VISIBLE);
                                for (int i = 0; i < akarsBean.size(); i++) {
                                    if (akarsBean.get(i).getType_id().equals(type) &&
                                            akarsBean.get(i).getCity_id().equals(city) &&
                                            akarsBean.get(i).getDisrict_id().equals(district)) {
                                        filteredList.add(akarsBean.get(i));
                                        initAdapter(filteredList);
                                    } else if (akarsBean.get(i).getType_id().equals(type) &&
                                            akarsBean.get(i).getCity_id().equals(city)) {
                                        filteredList.add(akarsBean.get(i));
                                        initAdapter(filteredList);
                                    } else if (akarsBean.get(i).getType_id().equals(type) &&
                                            akarsBean.get(i).getDisrict_id().equals(district)) {
                                        filteredList.add(akarsBean.get(i));
                                        initAdapter(filteredList);
                                    } else if (akarsBean.get(i).getCity_id().equals(city) &&
                                            akarsBean.get(i).getDisrict_id().equals(district)) {
                                        filteredList.add(akarsBean.get(i));
                                        initAdapter(filteredList);
                                    } else if (akarsBean.get(i).getType_id().equals(type)) {
                                        filteredList.add(akarsBean.get(i));
                                        initAdapter(filteredList);
                                    } else if (akarsBean.get(i).getCity_id().equals(city)) {
                                        filteredList.add(akarsBean.get(i));
                                        initAdapter(filteredList);
                                    }
//                                    else if (akarsBean.get(i).getDisrict_id().equals(district)) {
//                                        filteredList.add(akarsBean.get(i));
//                                        initAdapter(filteredList);
//                                    } else if (akarsBean.get(i).getType_id().equals(type) &&
//                                            akarsBean.get(i).getCity_id().equals(city) &&
//                                            akarsBean.get(i).getDisrict_id().equals(district) &&
//                                            akarsBean.get(i).getBathrooms().equals(bathRoom) &&
//                                            akarsBean.get(i).getRooms().equals(rooms) &&
//                                            akarsBean.get(i).getGarage().equals(garage)) {
//
//                                        filteredList.add(akarsBean.get(i));
//                                        initAdapter(filteredList);
//                                    } else if (akarsBean.get(i).getType_id().equals(type) &&
//                                            akarsBean.get(i).getCity_id().equals(city) &&
//                                            akarsBean.get(i).getDisrict_id().equals(district) &&
//                                            akarsBean.get(i).getBathrooms().equals(bathRoom) &&
//                                            akarsBean.get(i).getRooms().equals(rooms)) {
//
//                                        filteredList.add(akarsBean.get(i));
//                                        initAdapter(filteredList);
//                                    } else if (akarsBean.get(i).getType_id().equals(type) &&
//                                            akarsBean.get(i).getCity_id().equals(city) &&
//                                            akarsBean.get(i).getDisrict_id().equals(district) &&
//                                            akarsBean.get(i).getBathrooms().equals(bathRoom)) {
//                                        filteredList.add(akarsBean.get(i));
//                                        initAdapter(filteredList);
//                                    }
                                }
                                progressBar.setVisibility(View.GONE);
                            }
                        } else
                            Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<AkarsModel> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), R.string.errorconnection, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_categ, container, false);
        ButterKnife.bind(this, view);
        iniUI(view);
        return view;
    }

    private void iniUI(View view) {
        recycler = view.findViewById(R.id.recycle_viewID1);
        setSharedPreference = new ListSharePreference.Set(getActivity().getApplicationContext());
        getSharedPreference = new ListSharePreference.Get(getActivity().getApplicationContext());
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recycler.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        }

        initPullRefreshLayout(view);
        ImageButton imageButton = getActivity().findViewById(R.id.toolbar_filter_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent();
                myIntent.setClassName("com.tkmsoft.akarat", FilterActivity.class.getCanonicalName());
                startActivity(myIntent);
            }
        });
    }

    @Override
    public void onStart() {
        serverAkars();
        super.onStart();
    }


    private boolean getDistance(int area) {
        return area >= Integer.valueOf(distanceMin) && area <= Integer.valueOf(distanceMax);
    }

    private boolean getPrice(int price) {
        return price >= Integer.valueOf(priceMin) && price <= Integer.valueOf(priceMax);
    }

    private void initAdapter(ArrayList<AkarsModel.DataBean.CategoriesBean.AkarsBean> categories) {

        SubCategoryAdapter subCategoryAdapter = new SubCategoryAdapter(getActivity(), categories, new SubCategoryAdapter.ListAllListeners() {
            @Override
            public void onItemViewClick(AkarsModel.DataBean.CategoriesBean.AkarsBean akarsBean, int adapterPosition) {
                setSharedPreference.setkId(adapterPosition);
                String code = String.valueOf(akarsBean.getId());
                String about = akarsBean.getAbout();
                String area = akarsBean.getArea();
                String price = akarsBean.getPrice();
                String grage = akarsBean.getGarage();
                String city = akarsBean.getCity().getName();
                String discirt = akarsBean.getDisrict_name();
                String addres = akarsBean.getAddress();
                String lat = akarsBean.getLat();
                String lon = akarsBean.getLongX();
                Bundle bundle = new Bundle();
                bundle.putString("code", code);
                bundle.putString("area", area);
                bundle.putString("rooms", akarsBean.getRooms());
                bundle.putString("city", city);
                bundle.putString("price", price);
                bundle.putString("grage", grage);
                bundle.putString("about", about);
                bundle.putString("discirt", discirt);
                bundle.putString("addres", addres);
                bundle.putString("lat", lat);
                bundle.putString("long", lon);
                bundle.putString("name", akarsBean.getName());
                bundle.putString("bathroom", akarsBean.getBathrooms());

                if (akarsBean.getType_id().equals("1"))
                    bundle.putString("type", getString(R.string.rent));
                else
                    bundle.putString("type", getString(R.string.sale));
                MdetailsFragment detailsFragment = new MdetailsFragment();
                detailsFragment.setArguments(bundle);

                getFragmentManager().beginTransaction().replace(R.id.main_frame, detailsFragment).addToBackStack(null).commit();

            }

            @Override
            public void onFavButtonClick(View v, int position, boolean isFav) {


            }
        });

        recycler.setAdapter(subCategoryAdapter);
        subCategoryAdapter.notifyDataSetChanged();

    }


    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        try {
            mMainViewsCallBack = (IoCallBack) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "error");
        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMainViewsCallBack = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMainViewsCallBack.setFilterBtn(true);
        mMainViewsCallBack.setCallBackTitle(getSharedPreference.getCategName());
    }

    private void initPullRefreshLayout(final View rootView) {

        pullRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayouthome);
        pullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_WATER_DROP);
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFragmentManager().beginTransaction()
                        .detach(SubCategFragment.this)
                        .attach(SubCategFragment.this).commit();

            }
        });
    }


}
