package com.example.pizzadeliveryapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pizzadeliveryapp.adapters.DeliveryAdapter;
import com.example.pizzadeliveryapp.databinding.ActivityDeliveryBinding;
import com.example.pizzadeliveryapp.models.DeliveryModel;
import com.example.pizzadeliveryapp.models.preferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {

    ActivityDeliveryBinding binding;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private final List<DeliveryModel> deliveryModelList = new ArrayList<>();
    RecyclerView deliveryRec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeliveryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        deliveryRec = binding.deliveryRec;
        deliveryRec.setLayoutManager(new LinearLayoutManager(DeliveryActivity.this, RecyclerView.VERTICAL, false));

        DeliveryFood();
        setVariable();
    }

    private void setVariable() {

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeliveryActivity.this, WelcomeActivity.class));
                preferences.clearData(DeliveryActivity.this);
                finish();
            }
        });

        String userName = preferences.getFullName(this);
        binding.userName.setText(userName);
        
    }

    private void DeliveryFood() {
        binding.progressBar.setVisibility(View.VISIBLE);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                deliveryModelList.clear();
                if (snapshot.child("data").child("delivery").exists()){
                    binding.emptyDelivery.setVisibility(View.GONE);
                    for(DataSnapshot deliverySnapshot : snapshot.child("data").child("delivery").getChildren()){
                        final String phoneNumber = deliverySnapshot.getKey();
                        final String address = deliverySnapshot.child("address").getValue(String.class);

                        DeliveryModel myItems = new DeliveryModel(phoneNumber, address);

                        deliveryModelList.add(myItems);

                        binding.progressBar.setVisibility(View.GONE);
                    }
                    deliveryRec.setAdapter(new DeliveryAdapter(deliveryModelList, DeliveryActivity.this));
                }
                else {
                    binding.emptyDelivery.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}