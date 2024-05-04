package com.example.pizzadeliveryapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.pizzadeliveryapp.MainActivity;
import com.example.pizzadeliveryapp.R;
import com.example.pizzadeliveryapp.models.preferences;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        if (preferences.getDataLogin(this)){
            if(preferences.getDataAs(this).equals("chef")){
                startActivity(new Intent(WelcomeActivity.this, ChefActivity.class));
                finish();
            }
            else if (preferences.getDataAs(this).equals("delivery")) {
                startActivity(new Intent(WelcomeActivity.this, DeliveryActivity.class));
                finish();
            }
            else if (preferences.getDataAs(this).equals("user")){
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
    }

    public void register(View view) {
        startActivity(new Intent(WelcomeActivity.this, RegistrationActivity.class));
        finish();
    }

    public void login(View view) {
        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        finish();
    }
}