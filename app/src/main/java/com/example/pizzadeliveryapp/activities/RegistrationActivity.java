package com.example.pizzadeliveryapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pizzadeliveryapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrationActivity extends AppCompatActivity {

    private final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    public boolean isValidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final EditText phone = findViewById(R.id.et_phone);
        final EditText name = findViewById(R.id.et_full_name);
        final EditText email = findViewById(R.id.et_email);
        final EditText password = findViewById(R.id.et_password);
        final Button registerBtn = findViewById(R.id.btn_register);
        final TextView loginBtn = findViewById(R.id.txt_sign_in);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String phoneText = phone.getText().toString();
                final String nameText = name.getText().toString();
                final String emailText = email.getText().toString();
                final String passwordText = password.getText().toString();

                if(phoneText.isEmpty() || nameText.isEmpty()|| emailText.isEmpty() || passwordText.isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "Please fill in all the details.", Toast.LENGTH_SHORT).show();
                }
                else if (passwordText.length()<5) {
                    Toast.makeText(RegistrationActivity.this, "Password must be greater than 5 characters.", Toast.LENGTH_SHORT).show();
                } 
                else if (!isValidEmail(emailText)){
                    Toast.makeText(RegistrationActivity.this, "Please enter valid email address.", Toast.LENGTH_SHORT).show();
                }
                else if (Long.parseLong(phoneText)>=7000000000L && Long.parseLong(phoneText)<=9999999999L && isValidEmail(emailText)) {

                    databaseReference.child("data").child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(phoneText)){
                                Toast.makeText(RegistrationActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                            }else {
                                databaseReference.child("data").child("users").child(phoneText).child("fullname").setValue(nameText);
                                databaseReference.child("data").child("users").child(phoneText).child("email").setValue(emailText);
                                databaseReference.child("data").child("users").child(phoneText).child("password").setValue(passwordText);
                                databaseReference.child("data").child("users").child(phoneText).child("as").setValue("user");
                                Toast.makeText(RegistrationActivity.this, "User Registered Successfully.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{
                    Toast.makeText(RegistrationActivity.this, "Please enter valid phone number.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}