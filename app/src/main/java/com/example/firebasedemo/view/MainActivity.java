package com.example.firebasedemo.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firebasedemo.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ActivityMainBinding binding;
    String email="";
    String password="";
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        if(user!=null){
            Intent intent=new Intent(MainActivity.this,FeedActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void signIn(View view){
        email=binding.emailText.getText().toString();
        password=binding.passText.getText().toString();
        if(email.equals("") || password.equals("")){
            Toast.makeText(this,"Enter email and password",Toast.LENGTH_LONG).show();
        }
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intent=new Intent(MainActivity.this,FeedActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void signUp(View view){
        email=binding.emailText.getText().toString();
        password=binding.passText.getText().toString();
        if(email.equals("") || password.equals("")){
            Toast.makeText(this,"Enter email and password",Toast.LENGTH_SHORT).show();
        }
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intent=new Intent(MainActivity.this,FeedActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }
}