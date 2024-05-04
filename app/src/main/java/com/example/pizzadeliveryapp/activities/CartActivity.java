package com.example.pizzadeliveryapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pizzadeliveryapp.R;
import com.example.pizzadeliveryapp.adapters.CartAdapter;
import com.example.pizzadeliveryapp.databinding.ActivityCartBinding;
import com.example.pizzadeliveryapp.helper.ManagementCart;
import com.example.pizzadeliveryapp.models.preferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class CartActivity extends AppCompatActivity implements PaymentResultListener {

    ActivityCartBinding binding;
    private RecyclerView.Adapter adapter;
    private ManagementCart managementCart;
    private double tax;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Checkout.preload(getApplicationContext());

        managementCart = new ManagementCart(this);

        calculateCart();
        initList();

        binding.btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
        alertDialog.setTitle(" One Last Step!");
        alertDialog.setMessage("Enter your Address: ");
        final EditText edtAddress = new EditText(CartActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT

        );
        edtAddress.setLayoutParams(layoutParams);
        alertDialog.setView(edtAddress);
        alertDialog.setIcon(R.drawable.ic_baseline_shopping_cart_24);

        String phoneNumber = preferences.getPhone(this);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String Status = "1"; // 1, Order Placed (Order getting prepared)
                        String Address = edtAddress.getText().toString();

                        databaseReference.child("data").child("orders").child(phoneNumber).setValue(managementCart.getListCart());
                        databaseReference.child("data").child("orders").child(phoneNumber).child("address").setValue(Address);

                        databaseReference.child("data").child("users").child(phoneNumber).child("orders").setValue(managementCart.getListCart());
                        databaseReference.child("data").child("users").child(phoneNumber).child("orders").child("address").setValue(Address);
                        databaseReference.child("data").child("users").child(phoneNumber).child("orders").child("status").setValue(Status);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                makePayment();
                Toast.makeText(CartActivity.this, "Proceed to Payment!", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }

    private void makePayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_bPdJ4AeYalALne");

        double percentTax = 0.10;
        double delivery = 20;
        tax = Math.round(managementCart.getTotalFee()*percentTax*100.0)/100;
        double total = Math.round((managementCart.getTotalFee()+tax+delivery)*100);

        String email = preferences.getEmail(this);
        String phoneNumber = preferences.getPhone(this);

        checkout.setImage(R.mipmap.logo4);

        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();

            options.put("name", "Pizza Delivery App");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
            //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", total);//pass amount in currency subunits
            options.put("prefill.email", email);
            options.put("prefill.contact", phoneNumber);
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 2);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }

    }

    @Override
    public void onPaymentSuccess(String s) {
        orderPlacedNotification();
        Toast.makeText(this, "Payment Successful :"+s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        String phoneNumber = preferences.getPhone(this);
        databaseReference.child("data").child("orders").child(phoneNumber).removeValue();
        databaseReference.child("data").child("users").child(phoneNumber).child("orders").removeValue();
        Toast.makeText(this, "Payment Failed and cause is :"+s, Toast.LENGTH_SHORT).show();
    }

    private void initList() {
        if(managementCart.getListCart().isEmpty()){
            binding.emptyCart.setVisibility(View.VISIBLE);
            binding.scrollViewCart.setVisibility(View.GONE);
        }
        else {
            binding.emptyCart.setVisibility(View.GONE);
            binding.scrollViewCart.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.cartRec.setLayoutManager(linearLayoutManager);
        adapter = new CartAdapter(managementCart.getListCart(), this, () -> calculateCart());
        binding.cartRec.setAdapter(adapter);

    }

    private void calculateCart() {
        double percentTax = 0.10;
        double delivery = 20;

        tax = Math.round(managementCart.getTotalFee()*percentTax*100.0)/100;

        double total = Math.round((managementCart.getTotalFee()+tax+delivery)*100)/100;
        double itemTotal = Math.round(managementCart.getTotalFee()*100)/100;

        binding.subTotal.setText("Rs. "+itemTotal);
        binding.deliveryCharges.setText("Rs. "+delivery);
        binding.totalTax.setText("Rs. "+tax);
        binding.totalPrice.setText("Rs. "+total);
    }

    public void orderPlacedNotification(){
        String channelID = "CHANNEL_ID_NOTIFICATION";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelID);
        builder.setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setContentTitle("Successful")
                .setContentText("Your order has been placed!")
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

}