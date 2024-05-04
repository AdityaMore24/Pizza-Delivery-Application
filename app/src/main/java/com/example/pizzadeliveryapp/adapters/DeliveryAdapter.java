package com.example.pizzadeliveryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzadeliveryapp.R;
import com.example.pizzadeliveryapp.models.DeliveryModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.ViewHolder> {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private final List<DeliveryModel> deliveryModelList;
    private final Context context;
    private int availableUsers = 0;

    public DeliveryAdapter(List<DeliveryModel> deliveryModelList, Context context) {
        this.deliveryModelList = deliveryModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public DeliveryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DeliveryAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryAdapter.ViewHolder holder, int position) {
        holder.phoneNumber.setText(String.valueOf(deliveryModelList.get(position).getPhone()));
        holder.address.setText(String.valueOf(deliveryModelList.get(position).getAddress()));
        holder.completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = deliveryModelList.get(position).getPhone();
                String status = "3"; // Order Delivered
                databaseReference.child("data").child("users").child(phoneNumber).child("orders").child("status").setValue(status);
                databaseReference.child("data").child("delivery").child(phoneNumber).removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        return deliveryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView address, phoneNumber;
        public Button completed;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address2);
            phoneNumber = itemView.findViewById(R.id.phoneNumber2);
            completed = itemView.findViewById(R.id.completed2);
        }
    }
}
