package com.example.pizzadeliveryapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzadeliveryapp.R;
import com.example.pizzadeliveryapp.adapters.HomeVerAdapter;
import com.example.pizzadeliveryapp.databinding.FragmentHomeBinding;
import com.example.pizzadeliveryapp.models.CartModel;
import com.example.pizzadeliveryapp.models.HomeVerModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private final List<HomeVerModel> homeVerModelList = new ArrayList<>();
    RecyclerView homeVerticalRec;
    FloatingActionButton sendBtn;

    public HomeFragment(){

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        homeVerticalRec = binding.homeVerRec;
        homeVerticalRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        HomeFood();

//        sendBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        cartModelList.clear();
//                        for(DataSnapshot food : snapshot.child("data").child("menu").child("home").getChildren()){
//                            final String fname = food.child("fname").getValue(String.class);
//                            final String price = food.child("price").getValue(String.class);
//                            final Double rating = food.child("rating").getValue(Double.class);
//                            final String url = food.child("url").getValue(String.class);
//
//                            CartModel myItems = new CartModel(fname, price, rating, url);
//
//                            cartModelList.add(myItems);
//                        }
//                        cartRec.setAdapter(new CartAdapter(cartModelList, getActivity()));
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        });


        //Firebase Database
//
//        homeVerticalRec = (RecyclerView)root.findViewById(R.id.home_ver_rec);
//        homeVerticalRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
//
//        FirebaseRecyclerOptions<HomeVerModel> options =
//                new FirebaseRecyclerOptions.Builder<HomeVerModel>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("data").child("menu").child("home"), HomeVerModel.class)
//                        .build();
//
//        homeVerAdapter = new HomeVerAdapter(options);
//        homeVerticalRec.setAdapter(homeVerAdapter);

        //Local Database

//        homeVerticalRec = root.findViewById(R.id.home_ver_rec);
//
//        homeVerModelList = new ArrayList<>();
//
//        homeVerModelList.add(new HomeVerModel(R.drawable.pizza1, "Veg Pizza", "30 Minutes", "4.6", "Rs. 120"));
//        homeVerModelList.add(new HomeVerModel(R.drawable.pizza3, "Cheese Pizza", "30 Minutes", "4.8", "Rs. 150"));
//
//
//        homeVerAdapter = new HomeVerAdapter(getActivity(),homeVerModelList){};
//        homeVerticalRec.setAdapter(homeVerAdapter);
//        homeVerticalRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
//        homeVerticalRec.setHasFixedSize(true);
//        homeVerticalRec.setNestedScrollingEnabled(false);

        return binding.getRoot();
    }

    private void HomeFood() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                homeVerModelList.clear();
                for(DataSnapshot food : snapshot.child("data").child("menu").child("home").getChildren()){
                    final String fname = food.child("fname").getValue(String.class);
                    final Double price = food.child("price").getValue(Double.class);
                    final Float rating = food.child("rating").getValue(Float.class);
                    final String timing = food.child("timing").getValue(String.class);
                    final String url = food.child("url").getValue(String.class);

                    HomeVerModel myItems = new HomeVerModel(fname, price, rating, timing, url);

                    homeVerModelList.add(myItems);
                }
                homeVerticalRec.setAdapter(new HomeVerAdapter(homeVerModelList, getActivity()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


//    @Override
//    public void onStart() {
//        super.onStart();
//        homeVerAdapter.startListening();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        homeVerAdapter.stopListening();
//    }

}