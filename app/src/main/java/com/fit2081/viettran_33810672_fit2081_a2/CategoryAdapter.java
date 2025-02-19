package com.fit2081.viettran_33810672_fit2081_a2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fit2081.viettran_33810672_fit2081_a1.R;
import com.fit2081.viettran_33810672_fit2081_a2.model.entity.CategoryEntity;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private ArrayList<CategoryEntity> cateDatabase;

    public CategoryAdapter() {}

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_category, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        CategoryEntity category = cateDatabase.get(position);
        holder.tvCardCateID.setText(category.getCateID());
        holder.tvCardCateName.setText(category.getCateName());
        holder.tvCardEventCount.setText(String.valueOf(category.getEventCount()));
        holder.tvCardIsActive.setText(String.valueOf(category.getCateIsActive()));
        holder.tvCardLocation.setText(category.getCategoryLocation());

        // card onclick listener
        holder.cardViewCate.setOnClickListener(v -> {

            Context context = holder.cardViewCate.getContext();
            Intent intent = new Intent(context, MapsActivity.class);
            intent.putExtra("categoryName", category.getCateName());
            intent.putExtra("categoryLocation", category.getCategoryLocation());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if(cateDatabase == null)
            return 0;
        else
            return cateDatabase.size();
    }

    public void setCategory(ArrayList<CategoryEntity> newData){cateDatabase = newData;}
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCardCateID, tvCardCateName, tvCardEventCount, tvCardIsActive, tvCardLocation;
        View cardViewCate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewCate = itemView;
            tvCardCateID = itemView.findViewById(R.id.tvCardCateID);
            tvCardCateName = itemView.findViewById(R.id.tvCardCateName);
            tvCardEventCount = itemView.findViewById(R.id.tvCardCateEventCount);
            tvCardIsActive = itemView.findViewById(R.id.tvCardCateIsActive);
            tvCardLocation = itemView.findViewById(R.id.tvCardCateLocation);
        }
    }
}
