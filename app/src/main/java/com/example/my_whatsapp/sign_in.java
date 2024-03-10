package com.example.my_whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.my_whatsapp.Models.MainScreen;
import com.example.my_whatsapp.databinding.ActivitySignInBinding;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class sign_in extends AppCompatActivity {

    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ActivitySignInBinding binding;

    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions gso;



    int RC_SIGN_IN = 65;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();


        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        progressDialog = new ProgressDialog(sign_in.this);
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("Logging");


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        binding.signingooglesignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGoogle();
            }


            private void signInWithGoogle() {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


        binding.DoesntHaveAAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sign_in.this, MainActivity.class);
                startActivity(intent);
            }
        });
        binding.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signInWithEmailAndPassword
                        (binding.signinEmail.getText().toString(), binding.signinPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
           public void onComplete(@NonNull Task<AuthResult> task) {

              progressDialog.dismiss();
              if (task.isSuccessful()) {
                  Toast.makeText(sign_in.this, "login Succesfull", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(sign_in.this, MainScreen.class);
                     startActivity(intent);
                      finish();
                      } else {
                        Toast.makeText(sign_in.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
              }
             }
        });
      }
   });


        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(sign_in.this, MainScreen.class);
            startActivity(intent);
            finish();
        }

    }
}


