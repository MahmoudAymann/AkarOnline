package com.tkmsoft.akarat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.tkmsoft.akarat.R;
import com.tkmsoft.akarat.model.spinner.PhoneSpinnerModel;

import java.util.List;

public class DefaultSpinnerAdapter extends ArrayAdapter<PhoneSpinnerModel> {

    private List<String> mDataList;
    private Context mContext;

    public DefaultSpinnerAdapter(@NonNull Context context, List<String> mDataList) {
        super(context, R.layout.single_item_spinner_phone_countries);
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
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert mInflater != null;
            convertView = mInflater.inflate(R.layout.single_item_spinner_phone_countries, parent, false);
            //mViewHolder.mFlag = convertView.findViewById(R.id.imageView);
            mViewHolder.mName = convertView.findViewById(R.id.textView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        //mViewHolder.mFlag.setImageResource(mDataList.get(position).getImage());
        mViewHolder.mName.setText(mDataList.get(position));
        return convertView;
    }

    private static class ViewHolder {
        //ImageView mFlag;
        TextView mName;
    }

}