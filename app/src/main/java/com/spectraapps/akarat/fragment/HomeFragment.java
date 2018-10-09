package com.spectraapps.akarat.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.spectraapps.akarat.interfaces.IoCallBack;
import com.spectraapps.akarat.R;
import com.spectraapps.akarat.adapter.HomeAdapter;
import com.spectraapps.akarat.api.Api;
import com.spectraapps.akarat.model.AkarsModel;
import com.spectraapps.akarat.network.MyRetrofitClient;
import com.spectraapps.akarat.util.ListSharePreference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    RecyclerView recycler;
    HomeAdapter homeAdapter;
    private IoCallBack mMainViewsCallBack;
    PullRefreshLayout pullRefreshLayout;
    ProgressBar progressBar;
    ListSharePreference.Set setSharedPreference;
    ListSharePreference.Get getSharedPreference;
    int type;
    private String TAG = getClass().getSimpleName();

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        setSharedPreference = new ListSharePreference.Set(getActivity().getApplicationContext());
        getSharedPreference = new ListSharePreference.Get(getActivity().getApplicationContext());
//        Log.d(TAG, "onCreateView: "+getSharedPreference.gettokenId());
        iniui(root);
        return root;
    }

    private void iniui(View root) {
        recycler = root.findViewById(R.id.recycle_viewID);
        progressBar = root.findViewById(R.id.progerss);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        try {
            progressBar.setVisibility(View.VISIBLE);
            initService();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        initPullRefreshLayout(root);
    }

    private void initService() {
        Api api = MyRetrofitClient.categories().create(Api.class);
        Call<AkarsModel> akarsModelCall = api.getCategories();
        akarsModelCall.enqueue(new Callback<AkarsModel>() {
            @Override
            public void onResponse(@NonNull Call<AkarsModel> call, @NonNull Response<AkarsModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus().getType().equals("success")) {
                            initAdapter(response.body().getData().getCategories());
                        } else
                            Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                        //  Toast.makeText(getActivity(), ""+response.body().getData().getCategories().get(0).getPhoto(), Toast.LENGTH_SHORT).show();
                    }
                }
                progressBar.setVisibility(View.GONE);
                pullRefreshLayout.setRefreshing(false);
            }
            @Override
            public void onFailure(@NonNull Call<AkarsModel> call, @NonNull Throwable t) {
                t.printStackTrace();
                pullRefreshLayout.setRefreshing(false);
                Log.v("MMK", "fail");
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void initAdapter(ArrayList<AkarsModel.DataBean.CategoriesBean> categories) {
        homeAdapter = new HomeAdapter(getActivity(), categories, new HomeAdapter.ListAllListeners() {
            @Override
            public void onItemViewClick(AkarsModel.DataBean.CategoriesBean homeModel, int adapterPosition) {
                setSharedPreference.setCategName(homeModel.getName());
                setSharedPreference.setgId(String.valueOf(adapterPosition));

                getFragmentManager().beginTransaction()
                        .replace(R.id.main_frame, new SubCategFragment())
                        .addToBackStack(null).commit();

            }
        });

        recycler.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();
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
        mMainViewsCallBack.setCallBackTitle(getString(R.string.Home));
    }

    private void initPullRefreshLayout(final View rootView) {

        pullRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayouthome);
        pullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_WATER_DROP);

        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initService();
            }
        });
    }

}
