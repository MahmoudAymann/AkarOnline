package com.spectraapps.akarat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spectraapps.akarat.R;
import com.spectraapps.akarat.model.AkarsModel;
import com.joooonho.SelectableRoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private Context context;
    private ListAllListeners listAllListeners;
    private ArrayList<AkarsModel.DataBean.CategoriesBean> bDataList;


    public HomeAdapter(Context context, ArrayList<AkarsModel.DataBean.CategoriesBean> bDataList,
                       ListAllListeners listAllListeners) {
        this.bDataList = bDataList;
        this.listAllListeners = listAllListeners;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recyclerview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeAdapter.MyViewHolder holder, int position) {
            holder.nameTV.setText(bDataList.get(holder.getAdapterPosition()).getName());

        Picasso.get().load(bDataList.get(holder.getAdapterPosition()).getPhoto())
                .placeholder(R.drawable.place_holder)
                .resize(300,200)
                .into(holder.imageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listAllListeners.onItemViewClick(bDataList.get(holder.getAdapterPosition()), holder.getAdapterPosition());
            }
        });
    }

    public interface ListAllListeners {
        void onItemViewClick(AkarsModel.DataBean.CategoriesBean homeModel, int adapterPosition);

    }

    @Override
    public int getItemCount() {
        if (bDataList == null)
            return 0;
        else
            return bDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV;
        SelectableRoundedImageView imageView;

        MyViewHolder(View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.name_film);
            imageView = itemView.findViewById(R.id.image_film);
        }
    }


}
