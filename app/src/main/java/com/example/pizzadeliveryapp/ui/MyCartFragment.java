package com.example.pizzadeliveryapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzadeliveryapp.R;
import com.example.pizzadeliveryapp.adapters.CartAdapter;
import com.example.pizzadeliveryapp.adapters.HomeVerAdapter;
import com.example.pizzadeliveryapp.databinding.FragmentMyCartBinding;
import com.example.pizzadeliveryapp.models.CartModel;
import com.example.pizzadeliveryapp.models.HomeVerModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyCartFragment extends Fragment{

    FragmentMyCartBinding binding;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private final List<CartModel> cartModelList = new ArrayList<>();
    RecyclerView cartRec;
    private int availableUsers = 0;
    FloatingActionButton delBtn;

    private String fname;
    private Double rating;
    private String price;
    private String quantity;

    public MyCartFragment(){

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyCartBinding.inflate(inflater, container, false);

        cartRec = binding.cartRec;
        cartRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        getIntentExtra();

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.child("data").hasChild("orders")){
//                    availableUsers = (int)snapshot.child("data").child("orders").getChildrenCount();
//                }
//                else {
//
//                }
//                cartModelList.clear();
//                for(DataSnapshot food : snapshot.child("data").child("orders").getChildren()){
//                    final String fname = food.child("foodname").getValue(String.class);
//                    final String price = food.child("price").getValue(String.class);
//                    final Double rating = food.child("rating").getValue(Double.class);
//                    final String quantity = food.child("quantity").getValue(String.class);
////                    int x = Integer.parseInt(actualPrice);
////                    int y = Integer.parseInt(quantity);
////                    int z = x * y;
////                    final String price = String.valueOf(z);
//
//                    CartModel myItems = new CartModel(fname, price, rating, quantity);
//
//                    cartModelList.add(myItems);
//                }
//                cartRec.setAdapter(new CartAdapter(cartModelList, getActivity(), delBtn));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });



        //Firebase Database

//        cartRec = (RecyclerView)root.findViewById(R.id.cart_rec);
//        cartRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
//
//        FirebaseRecyclerOptions<CartModel> options =
//                new FirebaseRecyclerOptions.Builder<CartModel>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("data").child("menu").child("home"), CartModel.class)
//                        .build();
//
//        cartAdapter = new CartAdapter(options);
//        cartRec.setAdapter(cartAdapter);

        return binding.getRoot();
    }

    private void getIntentExtra() {
        fname = "";
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        cartAdapter.startListening();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        cartAdapter.stopListening();
//    }

//    public void plusNumberFood(@NonNull List<CartModel> cartModelList, int position){
//        cartModelList.get(position).setQuantity(String.valueOf(Integer.parseInt(cartModelList.get(position).getQuantity())+1));
//    }
//
//    public void minusNumberFood(@NonNull List<CartModel> cartModelList, int position){
//        if (Integer.parseInt(cartModelList.get(position).getQuantity())==1){
//            cartModelList.remove(position);
//        }else {
//            cartModelList.get(position).setQuantity(String.valueOf(Integer.parseInt(cartModelList.get(position).getQuantity())-1));
//        }
//    }

}