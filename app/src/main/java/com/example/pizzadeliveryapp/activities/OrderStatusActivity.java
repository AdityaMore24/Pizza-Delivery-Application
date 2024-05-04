package com.example.pizzadeliveryapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pizzadeliveryapp.adapters.OrderStatusAdapter;
import com.example.pizzadeliveryapp.databinding.ActivityOrderStatusBinding;
import com.example.pizzadeliveryapp.models.OrderStatusModel;
import com.example.pizzadeliveryapp.models.preferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderStatusActivity extends AppCompatActivity {

    ActivityOrderStatusBinding binding;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private final List<OrderStatusModel> orderStatusList = new ArrayList<>();
    RecyclerView orderStatusRec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        orderStatusRec = binding.orderStatusRec;
        orderStatusRec.setLayoutManager(new LinearLayoutManager(OrderStatusActivity.this, RecyclerView.VERTICAL, false));

        OrderStatusFood();
        setVariable();
    }

    private void setVariable() {

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderStatusActivity.this, WelcomeActivity.class));
                preferences.clearData(OrderStatusActivity.this);
                finish();
            }
        });

    }

    private void OrderStatusFood() {
        binding.progressBar.setVisibility(View.VISIBLE);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderStatusList.clear();
                String phoneNumber = preferences.getPhone(OrderStatusActivity.this);
                if(snapshot.child("data").child("users").child(phoneNumber).child("orders").exists()){
                    final String address = snapshot.child("data").child("users").child(phoneNumber).child("orders").child("address").getValue(String.class);
                    final String status = snapshot.child("data").child("users").child(phoneNumber).child("orders").child("status").getValue(String.class);
                    if(status.equals("1")) {
                        String finalStatus = "Order Placed!";
                        OrderStatusModel myItems = new OrderStatusModel(phoneNumber, address, finalStatus);
                        orderStatusList.add(myItems);
                    }
                    else if(status.equals("2")){
                        String finalStatus = "On its Way!";
                        OrderStatusModel myItems = new OrderStatusModel(phoneNumber, address, finalStatus);
                        orderStatusList.add(myItems);
                    }
                    else {
                        String finalStatus = "Delivered!";
                        OrderStatusModel myItems = new OrderStatusModel(phoneNumber, address, finalStatus);
                        orderStatusList.add(myItems);
                    }
                    binding.progressBar.setVisibility(View.GONE);
                    orderStatusRec.setAdapter(new OrderStatusAdapter(orderStatusList, OrderStatusActivity.this));
                }
                else {
                    binding.emptyOrder.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}