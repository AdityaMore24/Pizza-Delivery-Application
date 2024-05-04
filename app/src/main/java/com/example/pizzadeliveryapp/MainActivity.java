package com.example.pizzadeliveryapp;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pizzadeliveryapp.activities.CartActivity;
import com.example.pizzadeliveryapp.activities.OrderStatusActivity;
import com.example.pizzadeliveryapp.activities.WelcomeActivity;
import com.example.pizzadeliveryapp.adapters.HomeVerAdapter;
import com.example.pizzadeliveryapp.models.HomeVerModel;
import com.example.pizzadeliveryapp.models.preferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzadeliveryapp.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    List<HomeVerModel> homeVerModelList = new ArrayList<>();
    RecyclerView homeVerticalRec;
    HomeVerAdapter homeVerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        HomeFood();
        setVariable();

    }

    private void setVariable() {

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
                preferences.clearData(MainActivity.this);
                finish();
            }
        });

        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = binding.searchText.getText().toString();
                filteredList(text);
            }
        });

        String phoneNumber = preferences.getPhone(this);
        String userName = preferences.getFullName(this);
        binding.userName.setText(userName);

        binding.cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });

        binding.ordersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, OrderStatusActivity.class));
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String status = snapshot.child("data").child("users").child(phoneNumber).child("orders").child("status").getValue(String.class);

                if(status.equals("2")){
                    orderForDeliveryNotification();
                }
                else if (status.equals("3")){
                    orderDeliveredNotification();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void HomeFood() {
        binding.progressBar.setVisibility(View.VISIBLE);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                homeVerModelList.clear();
                for(DataSnapshot food : snapshot.child("data").child("menu").child("home").getChildren()){
                    String fname = food.child("fname").getValue(String.class);
                    Double price = food.child("price").getValue(Double.class);
                    Float rating = food.child("rating").getValue(Float.class);
                    String timing = food.child("timing").getValue(String.class);
                    String url = food.child("url").getValue(String.class);
                    String details = food.child("details").getValue(String.class);

                    HomeVerModel myItems = new HomeVerModel(fname, price, rating, timing, url, details);

                    homeVerModelList.add(myItems);

                    binding.progressBar.setVisibility(View.GONE);
                }
                homeVerticalRec = binding.homeVerRec;
                homeVerticalRec.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
                homeVerticalRec.setAdapter(new HomeVerAdapter(homeVerModelList, MainActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void filteredList(String text) {
        List<HomeVerModel> filteredList = new ArrayList<>();
        for (HomeVerModel item : homeVerModelList){
            if(item.getFname().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()){
            Toast.makeText(this, "We'll add that pizza for you ASAP!", Toast.LENGTH_SHORT).show();
        }
        else {
            homeVerAdapter.setFilteredList(filteredList);
        }
    }


    public void orderForDeliveryNotification(){
        String channelID = "CHANNEL_ID_NOTIFICATION";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelID);
        builder.setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setContentTitle("On its way!")
                .setContentText("Your order is ready and Out for Delivery!")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(getApplicationContext(), OrderStatusActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0, intent, PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, builder.build());

    }

    public void orderDeliveredNotification(){
        String channelID = "CHANNEL_ID_NOTIFICATION";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelID);
        builder.setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setContentTitle("Bon Appetite")
                .setContentText("Your order has been delivered!" + " Enjoy your meal!")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(getApplicationContext(), OrderStatusActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0, intent, PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, builder.build());

        databaseReference.child("data").child("users").child(preferences.getPhone(this)).child("orders").child("status").setValue("0");

    }

}