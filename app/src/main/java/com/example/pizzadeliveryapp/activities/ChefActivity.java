package com.example.pizzadeliveryapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pizzadeliveryapp.adapters.ChefAdapter;
import com.example.pizzadeliveryapp.databinding.ActivityChefBinding;
import com.example.pizzadeliveryapp.models.ChefModel;
import com.example.pizzadeliveryapp.models.preferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChefActivity extends AppCompatActivity {

    ActivityChefBinding binding;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private final List<ChefModel> chefModelList = new ArrayList<>();
    RecyclerView chefRec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChefBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chefRec = binding.chefRec;
        chefRec.setLayoutManager(new LinearLayoutManager(ChefActivity.this, RecyclerView.VERTICAL, false));

        OrderFood();
        setVariable();
    }

    private void setVariable() {

        String userName = preferences.getFullName(this);
        binding.userName.setText(userName);

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChefActivity.this, WelcomeActivity.class));
                preferences.clearData(ChefActivity.this);
                finish();
            }
        });

    }

    private void OrderFood() {
        binding.progressBar.setVisibility(View.VISIBLE);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chefModelList.clear();
                for(DataSnapshot orderSnapshot : snapshot.child("data").child("orders").getChildren()){
                    final String phoneNumber = orderSnapshot.getKey();
                    final String address = snapshot.child("data").child("orders").child(phoneNumber).child("address").getValue(String.class);
                    ChefModel myItems = new ChefModel(phoneNumber, address);
                    chefModelList.add(myItems);
                    binding.progressBar.setVisibility(View.GONE);
                }
                chefRec.setAdapter(new ChefAdapter(chefModelList, ChefActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}