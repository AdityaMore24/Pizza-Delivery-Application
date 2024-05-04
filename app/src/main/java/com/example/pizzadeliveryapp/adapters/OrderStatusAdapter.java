package com.example.pizzadeliveryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzadeliveryapp.R;
import com.example.pizzadeliveryapp.models.OrderStatusModel;

import java.util.List;

public class OrderStatusAdapter extends RecyclerView.Adapter<OrderStatusAdapter.ViewHolder>{

    private final List<OrderStatusModel> orderStatusList;
    private final Context context;

    public OrderStatusAdapter(List<OrderStatusModel> orderStatusList, Context context) {
        this.orderStatusList = orderStatusList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderStatusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderStatusAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textOrderStatus.setText(String.valueOf(orderStatusList.get(position).getStatus()));
        holder.textOrderPhone.setText(String.valueOf(orderStatusList.get(position).getPhone()));
        holder.textOrderAddress.setText(String.valueOf(orderStatusList.get(position).getAddress()));
    }

    @Override
    public int getItemCount() {
        return orderStatusList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textOrderStatus, textOrderPhone, textOrderAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textOrderStatus = itemView.findViewById(R.id.orderStatus);
            textOrderPhone = itemView.findViewById(R.id.orderPhone);
            textOrderAddress = itemView.findViewById(R.id.orderAddress);
        }
    }
}

