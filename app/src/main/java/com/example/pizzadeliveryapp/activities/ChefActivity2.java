package com.example.pizzadeliveryapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.pizzadeliveryapp.adapters.ChefAdapter2;
import com.example.pizzadeliveryapp.databinding.ActivityChef2Binding;
import com.example.pizzadeliveryapp.models.ChefModel;
import com.example.pizzadeliveryapp.models.ChefModel2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChefActivity2 extends AppCompatActivity {

    ActivityChef2Binding binding;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private final List<ChefModel2> chefModelList2 = new ArrayList<>();
    RecyclerView chefRec2;
    private ChefModel object;
    int availableOrders = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChef2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chefRec2 = binding.chefRec2;
        chefRec2.setLayoutManager(new LinearLayoutManager(ChefActivity2.this, RecyclerView.VERTICAL, false));

        OrderFood();
        getIntentExtra();
    }

    private void OrderFood() {
        binding.progressBar.setVisibility(View.VISIBLE);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chefModelList2.clear();
                final String phoneNumber = object.getPhone();
                availableOrders = Integer.parseInt(String.valueOf(snapshot.child("data").child("orders").child(phoneNumber).getChildrenCount()))-1;
                for (int i = 0; i<availableOrders; i++){
                    final String fname = snapshot.child("data").child("orders").child(phoneNumber).child(String.valueOf(i)).child("fname").getValue(String.class);
                    final String quantity = String.valueOf(snapshot.child("data").child("orders").child(phoneNumber).child(String.valueOf(i)).child("numberInCart").getValue(Long.class));
                    ChefModel2 myItems = new ChefModel2(fname, quantity);
                    chefModelList2.add(myItems);
                }
                binding.progressBar.setVisibility(View.GONE);
                chefRec2.setAdapter(new ChefAdapter2(chefModelList2, ChefActivity2.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getIntentExtra() {
        object = (ChefModel) getIntent().getSerializableExtra("object");
    }
}