package com.example.pizzadeliveryapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzadeliveryapp.R;
import com.example.pizzadeliveryapp.activities.DetailedViewActivity;
import com.example.pizzadeliveryapp.models.ChefModel;
import com.example.pizzadeliveryapp.models.ChefModel2;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChefAdapter2 extends RecyclerView.Adapter<ChefAdapter2.ViewHolder> {

    private final List<ChefModel2> chefModelList2;
    private final Context context;
    private int availableUsers = 0;

    public ChefAdapter2(List<ChefModel2> chefModelList2, Context context) {
        this.chefModelList2 = chefModelList2;
        this.context = context;
    }

    @NonNull
    @Override
    public ChefAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChefAdapter2.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chef_item_2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChefAdapter2.ViewHolder holder, int position) {
        holder.productName.setText(String.valueOf(chefModelList2.get(position).getFname()));
        holder.quantity.setText(String.valueOf(chefModelList2.get(position).getNumberInCart()));
    }

    @Override
    public int getItemCount() {
        return chefModelList2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView productName, quantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            quantity = itemView.findViewById(R.id.quantityText);
        }
    }
}
