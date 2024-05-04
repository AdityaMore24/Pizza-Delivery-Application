package com.example.pizzadeliveryapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pizzadeliveryapp.MainActivity;
import com.example.pizzadeliveryapp.R;
import com.example.pizzadeliveryapp.databinding.ActivityLoginBinding;
import com.example.pizzadeliveryapp.models.User;
import com.example.pizzadeliveryapp.models.preferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_user = database.getReference("data").child("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final EditText phone = findViewById(R.id.et_phone);
        final EditText password = findViewById(R.id.et_password);
        final Button loginBtn = findViewById(R.id.btn_sign_in);
        final TextView registerBtn = findViewById(R.id.txt_register);
        final Switch active = findViewById(R.id.switch1);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String phoneText = phone.getText().toString();
                final String passwordText = password.getText().toString();

                if(phoneText.isEmpty() || passwordText.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please fill in all the details.", Toast.LENGTH_SHORT).show();
                }
                else {
                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(phoneText)){
                                User user = snapshot.child(phoneText).getValue(User.class);
                                if(user.getPassword().equals(passwordText)){
                                    if (active.isChecked()){
                                        if (snapshot.child(phoneText).child("as").getValue(String.class).equals("chef")){
                                            preferences.setDataLogin(LoginActivity.this, true);
                                            preferences.setDataAs(LoginActivity.this, "chef");
                                            String name = snapshot.child(phoneText).child("fullname").getValue(String.class);
                                            preferences.setFullName(LoginActivity.this, name);
                                            preferences.setPhone(LoginActivity.this, phoneText);
                                            Toast.makeText(LoginActivity.this, "Successfully Logged In.", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this, ChefActivity.class));
                                            finish();
                                        }
                                        else if (snapshot.child(phoneText).child("as").getValue(String.class).equals("delivery")) {
                                            preferences.setDataLogin(LoginActivity.this, true);
                                            preferences.setDataAs(LoginActivity.this, "delivery");
                                            String name = snapshot.child(phoneText).child("fullname").getValue(String.class);
                                            preferences.setFullName(LoginActivity.this, name);
                                            preferences.setPhone(LoginActivity.this, phoneText);
                                            Toast.makeText(LoginActivity.this, "Successfully Logged In.", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this, DeliveryActivity.class));
                                            finish();
                                        }
                                        else if (snapshot.child(phoneText).child("as").getValue(String.class).equals("user")) {
                                            preferences.setDataLogin(LoginActivity.this, true);
                                            preferences.setDataAs(LoginActivity.this, "user");
                                            String name = snapshot.child(phoneText).child("fullname").getValue(String.class);
                                            String email = snapshot.child(phoneText).child("email").getValue(String.class);
                                            preferences.setFullName(LoginActivity.this, name);
                                            preferences.setEmail(LoginActivity.this, email);
                                            preferences.setPhone(LoginActivity.this, phoneText);
                                            Toast.makeText(LoginActivity.this, "Successfully Logged In.", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                            finish();
                                        }
                                    }else {
                                        if (snapshot.child(phoneText).child("as").getValue(String.class).equals("chef")){
                                            preferences.setDataLogin(LoginActivity.this, false);
                                            preferences.setDataAs(LoginActivity.this, "chef");
                                            String name = snapshot.child(phoneText).child("fullname").getValue(String.class);
                                            preferences.setFullName(LoginActivity.this, name);
                                            preferences.setPhone(LoginActivity.this, phoneText);
                                            Toast.makeText(LoginActivity.this, "Successfully Logged In.", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this, ChefActivity.class));
                                            finish();
                                        }
                                        else if (snapshot.child(phoneText).child("as").getValue(String.class).equals("delivery")) {
                                            preferences.setDataLogin(LoginActivity.this, false);
                                            preferences.setDataAs(LoginActivity.this, "delivery");
                                            String name = snapshot.child(phoneText).child("fullname").getValue(String.class);
                                            preferences.setFullName(LoginActivity.this, name);
                                            preferences.setPhone(LoginActivity.this, phoneText);
                                            Toast.makeText(LoginActivity.this, "Successfully Logged In.", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this, DeliveryActivity.class));
                                            finish();
                                        }
                                        else if (snapshot.child(phoneText).child("as").getValue(String.class).equals("user")) {
                                            preferences.setDataLogin(LoginActivity.this, false);
                                            preferences.setDataAs(LoginActivity.this, "user");
                                            String name = snapshot.child(phoneText).child("fullname").getValue(String.class);
                                            String email = snapshot.child(phoneText).child("email").getValue(String.class);
                                            preferences.setFullName(LoginActivity.this, name);
                                            preferences.setEmail(LoginActivity.this, email);
                                            preferences.setPhone(LoginActivity.this, phoneText);
                                            Toast.makeText(LoginActivity.this, "Successfully Logged In.", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                            finish();
                                        }
                                    }
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Wrong Phone Number.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                finish();
            }
        });

    }

}