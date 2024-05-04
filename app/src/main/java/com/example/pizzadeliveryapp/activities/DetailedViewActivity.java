package com.example.pizzadeliveryapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.pizzadeliveryapp.databinding.ActivityDetailedViewBinding;
import com.example.pizzadeliveryapp.helper.ManagementCart;
import com.example.pizzadeliveryapp.models.HomeVerModel;

public class DetailedViewActivity extends AppCompatActivity {

    ActivityDetailedViewBinding binding;
    private HomeVerModel object;
    private int num=1;
    private ManagementCart managementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailedViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentExtra();
        setVariable();
    }

    private void setVariable() {
        managementCart = new ManagementCart(this);

        binding.backBtn.setOnClickListener(view -> finish());

        Glide.with(DetailedViewActivity.this)
                .load(object.getUrl())
                .into(binding.foodImg);

        binding.priceTxt.setText("Rs. "+object.getPrice());
        binding.titleTxt.setText(object.getFname());
        binding.ratingTxt.setText(object.getRating()+"");
        binding.ratingBar.setRating(object.getRating());
        binding.details.setText(object.getDetails());
        binding.timeTxt.setText(object.getTiming());
        binding.totalTxt.setText(("Rs. "+num*object.getPrice()));

        binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num=num+1;
                binding.numTxt.setText(num+"");
                binding.totalTxt.setText("Rs. "+num*object.getPrice());
            }
        });

        binding.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num>1){
                    num=num-1;
                    binding.numTxt.setText(num+"");
                    binding.totalTxt.setText("Rs. "+num*object.getPrice());
                }
            }
        });

        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                object.setNumberInCart(num);
                managementCart.insertFood(object);
            }
        });

    }

    private void getIntentExtra() {
        object = (HomeVerModel) getIntent().getSerializableExtra("object");
    }

}