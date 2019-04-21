package com.tkmsoft.akarat.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tkmsoft.akarat.R;
import com.tkmsoft.akarat.model.AkarsModel;

import java.util.ArrayList;

public class SliderIamgeAdapter extends PagerAdapter {


    private ArrayList<AkarsModel.DataBean.CategoriesBean.AkarsBean.ImagesBean> images;
    private LayoutInflater inflater;
    private Context context;
    private PadgerListner padgerListner;



    public SliderIamgeAdapter(Context context, ArrayList<AkarsModel.DataBean.CategoriesBean.AkarsBean.ImagesBean> images, PadgerListner padgerListner) {
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);
        this.padgerListner = padgerListner;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    public interface PadgerListner {
        void onItemViewClick(ArrayList<AkarsModel.DataBean.CategoriesBean.AkarsBean.ImagesBean> images, int position);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, final int position) {
        View myImageLayout = inflater.inflate(R.layout.iteamslider, view, false);
        ImageView myImage = myImageLayout.findViewById(R.id.image);
        Picasso.get().load(images.get(position).getName())
                .placeholder(R.drawable.place_holder)
                .into(myImage);

        myImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                padgerListner.onItemViewClick(images, position);
            }
        });

        view.addView(myImageLayout, 0);

        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
}

