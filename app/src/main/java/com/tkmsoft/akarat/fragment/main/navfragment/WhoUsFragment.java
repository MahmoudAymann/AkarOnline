package com.tkmsoft.akarat.fragment.main.navfragment;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.tkmsoft.akarat.R;
import com.tkmsoft.akarat.activities.MainActivity;
import com.tkmsoft.akarat.fragment.main.home.HomeFragment;
import com.tkmsoft.akarat.interfaces.MainViewCallBack;
import com.tkmsoft.akarat.util.BaseBackPressedListener;
import com.tkmsoft.akarat.util.InitSpinner;
import com.tkmsoft.akarat.util.ListSharePreference;
import com.tkmsoft.akarat.util.MoveToFragment;

/**
 * A simple {@link Fragment} subclass.
 */

public class WhoUsFragment extends Fragment {

    private MainViewCallBack mMainViewsCallBack;
    private FragmentActivity mContext;
    private MoveToFragment moveToFragment;

    public WhoUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_who_us, container, false);
        Toast.makeText(getActivity(), "loading...", Toast.LENGTH_SHORT).show();
        WebView webView = rootView.findViewById(R.id.webView);
        webView.loadUrl("http://3qaronline.net/aboutET-us");
        return rootView;
    }

    @Override
    public void onStart() {
        mMainViewsCallBack.setFilterBtn(false);
        mMainViewsCallBack.setCallBackTitle(getString(R.string.who_us));
        super.onStart();
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
        moveToFragment = new MoveToFragment(mContext);
        fireBackButtonEvent();
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
