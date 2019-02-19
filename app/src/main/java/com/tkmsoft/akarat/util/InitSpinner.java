package com.tkmsoft.akarat.util;

import android.graphics.PorterDuff;
import android.support.v4.app.FragmentActivity;
import android.widget.Spinner;

import com.tkmsoft.akarat.R;
import com.tkmsoft.akarat.adapter.DefaultSpinnerAdapter;
import java.util.ArrayList;

/**
 * Created by MahmoudAyman on 14/01/2019.
 */
public class InitSpinner {

    private FragmentActivity mContext;

    public InitSpinner(FragmentActivity mContext) {
        this.mContext = mContext;
    }

    public Spinner setSpinner(Spinner spinner, ArrayList<String> stringArrayList) {
        spinner.getBackground().setColorFilter(mContext.getResources().getColor(R.color.white),
                PorterDuff.Mode.SRC_ATOP);
        DefaultSpinnerAdapter adapter = new DefaultSpinnerAdapter(mContext, stringArrayList);
        spinner.setAdapter(adapter);
        return spinner;
    }

}
