package com.example.pizzadeliveryapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzadeliveryapp.R;
import com.example.pizzadeliveryapp.activities.ChefActivity2;
import com.example.pizzadeliveryapp.models.ChefModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ChefAdapter extends RecyclerView.Adapter<ChefAdapter.ViewHolder> {

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private final List<ChefModel> chefModelList;
    private final Context context;

    public ChefAdapter(List<ChefModel> chefModelList, Context context) {
        this.chefModelList = chefModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChefAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChefAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chef_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChefAdapter.ViewHolder holder, int position) {

        holder.phoneNumber.setText(String.valueOf(chefModelList.get(position).getPhone()));
        holder.address.setText(String.valueOf(chefModelList.get(position).getAddress()));
        holder.completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = chefModelList.get(position).getPhone();
                String address = chefModelList.get(position).getAddress();
                String status = "2"; // 2, Order out for Delivery (Order is Completed)
                databaseReference.child("data").child("delivery").child(phoneNumber).child("address").setValue(address);
                databaseReference.child("data").child("users").child(phoneNumber).child("orders").child("status").setValue(status);
                databaseReference.child("data").child("orders").child(phoneNumber).removeValue();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChefActivity2.class);
                intent.putExtra("object", chefModelList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chefModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView address, phoneNumber;
        public Button completed;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address);
            phoneNumber = itemView.findViewById(R.id.phoneNumber);
            completed = itemView.findViewById(R.id.completed);
        }
    }
}
