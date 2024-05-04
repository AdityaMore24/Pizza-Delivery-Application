package com.example.pizzadeliveryapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pizzadeliveryapp.R;
import com.example.pizzadeliveryapp.activities.DetailedViewActivity;
import com.example.pizzadeliveryapp.models.HomeVerModel;

import java.util.List;

public class HomeVerAdapter extends RecyclerView.Adapter<HomeVerAdapter.ViewHolder>{

    List<HomeVerModel> homeVerModelList;
    Context context;

    public void setFilteredList(List<HomeVerModel> filteredList){
        homeVerModelList = filteredList;
        notifyDataSetChanged();
    }

    public HomeVerAdapter(List<HomeVerModel> homeVerModelList, Context context) {
        this.homeVerModelList = homeVerModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeVerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_vertical_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeVerAdapter.ViewHolder holder, int position) {
        holder.fname.setText(String.valueOf(homeVerModelList.get(position).getFname()));
        holder.rating.setText(String.valueOf(homeVerModelList.get(position).getRating()));
        holder.timing.setText(String.valueOf(homeVerModelList.get(position).getTiming()));
        holder.price.setText(String.valueOf(homeVerModelList.get(position).getPrice()));
        Glide.with(holder.imageView.getContext()).load(homeVerModelList.get(position).getUrl()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedViewActivity.class);
                intent.putExtra("object", homeVerModelList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeVerModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView fname, price, rating, timing;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ver_img);
            fname = itemView.findViewById(R.id.fname);
            rating = itemView.findViewById(R.id.rating);
            timing = itemView.findViewById(R.id.timing);
            price = itemView.findViewById(R.id.price);
        }
    }
}