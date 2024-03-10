package com.example.my_whatsapp;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.my_whatsapp.Models.MainScreen;
import com.example.my_whatsapp.Models.Users;
import com.example.my_whatsapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();


        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("creating");
        progressDialog.setMessage("creating account");

        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();
                mAuth.createUserWithEmailAndPassword
                        (binding.email.getText().toString(), binding.password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();

                        if (task.isSuccessful()) {

                            Users users = new Users(binding.username.getText().toString(), binding.email.getText().toString(), binding.password.getText().toString());

                            String id = task.getResult().getUser().getUid();

                            database.getReference().child("Users").child(id).setValue(users);
                            Toast.makeText(MainActivity.this, "Account Successfully Created", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(MainActivity.this, MainScreen.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Failed To create Account", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        View view = getWindow().getDecorView();
        int hide1 = View.SYSTEM_UI_FLAG_FULLSCREEN;
        view.setSystemUiVisibility(hide1);

        binding.alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, sign_in.class);
                startActivity(intent);

            }


        });

    }
}