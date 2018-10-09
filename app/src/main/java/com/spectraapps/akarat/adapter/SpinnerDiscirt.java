package com.spectraapps.akarat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.spectraapps.akarat.R;
import com.spectraapps.akarat.model.CityModel;

import java.util.ArrayList;

public class SpinnerDiscirt  extends ArrayAdapter<CityModel.DataBean.CitiesBean.DisrictsBean> {

    private ArrayList<String> mDataList;
    private Context mContext;

    public SpinnerDiscirt(@NonNull Context context, ArrayList<String> mDataList) {
        super(context, R.layout.iteam_spinner);
        this.mDataList = mDataList;
        this.mContext = context;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SpinnerDiscirt.ViewHolder mViewHolder = new SpinnerDiscirt.ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert mInflater != null;
            convertView = mInflater.inflate(R.layout.iteam_spinner, parent, false);
            mViewHolder.mName = convertView.findViewById(R.id.textView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (SpinnerDiscirt.ViewHolder) convertView.getTag();
        }
        //  Picasso.get().load(mDataList.get(position).getImage())
        //         .placeholder(R.drawable.ic_launcher_background)
        //        .error(R.drawable.ic_launcher_background)
        //       .into(mViewHolder.mFlag);
        mViewHolder.mName.setText(mDataList.get(position));
        return convertView;
    }

    private static class ViewHolder {
        TextView mName;
    }

}
