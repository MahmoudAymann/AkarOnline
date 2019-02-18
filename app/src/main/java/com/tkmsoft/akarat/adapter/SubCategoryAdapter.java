package com.tkmsoft.akarat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.squareup.picasso.Picasso;
import com.tkmsoft.akarat.R;
import com.tkmsoft.akarat.model.AkarsModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder> {

    private Context context;
    private ListAllListeners listAllListeners;
    private ArrayList<AkarsModel.DataBean.CategoriesBean.AkarsBean> bDataList;


    public SubCategoryAdapter(Context context, ArrayList<AkarsModel.DataBean.CategoriesBean.AkarsBean> bDataList,
                              ListAllListeners listAllListeners) {
        this.bDataList = bDataList;
        this.listAllListeners = listAllListeners;
        this.context = context; }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.tool, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.bind();
    }

    public interface ListAllListeners {
        void onItemViewClick(AkarsModel.DataBean.CategoriesBean.AkarsBean akarsBean, int adapterPosition);

        void onFavButtonClick(View v, int position, boolean isFav);
    }

    @Override
    public int getItemCount() {
        if (bDataList != null) {
            return bDataList.size();
        } else
            return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView area;
        TextView price, typeTV;
        SelectableRoundedImageView imageView;
        ImageButton favBtn;
        @BindView(R.id.countryTV)
        TextView countryTV;
        @BindView(R.id.cityTV)
        TextView cityTV;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            area = itemView.findViewById(R.id.m2);
            price = itemView.findViewById(R.id.eg);
            typeTV = itemView.findViewById(R.id.type);
            imageView = itemView.findViewById(R.id.imageView);

        }

        void bind() {
            if (bDataList.get(getAdapterPosition()).getType_id().equals("0")) {
                typeTV.setText(R.string.rent);
            } else {

                typeTV.setText(R.string.sale);
            }

            price.setText(bDataList.get(getAdapterPosition()).getPrice());
            area.setText(bDataList.get(getAdapterPosition()).getArea());
            countryTV.setText(bDataList.get(getAdapterPosition()).getCity().getName());
            cityTV.setText(bDataList.get(getAdapterPosition()).getDisrict_name());
            Picasso.get().load(bDataList.get(getAdapterPosition()).getImages().get(0).getName())
                    .placeholder(R.drawable.place_holder)
                    .error(R.drawable.place_holder)
                    .resize(300,200)
                    .into(imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listAllListeners.onItemViewClick(bDataList.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }


}

//