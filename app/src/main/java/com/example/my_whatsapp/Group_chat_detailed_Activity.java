package com.example.my_whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.my_whatsapp.Adapters.ChatAdapter;
import com.example.my_whatsapp.Models.MainScreen;
import com.example.my_whatsapp.Models.Message_models;
import com.example.my_whatsapp.databinding.ActivityGroupChatDetailedBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class Group_chat_detailed_Activity extends AppCompatActivity {

    ActivityGroupChatDetailedBinding binding;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupChatDetailedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Group_chat_detailed_Activity.this, MainScreen.class);
                startActivity(intent);
            }
        });

        final ArrayList<Message_models> arrayList = new ArrayList<>();
        final ChatAdapter chatAdapter = new ChatAdapter(arrayList, this);
        binding.chatRecyclerview.setAdapter(chatAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.chatRecyclerview.setLayoutManager(linearLayoutManager);

        binding.usernameChatScreen.setText("Narcos");

        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();


        final String sender_id = mAuth.getCurrentUser().getUid();
        Picasso.get().load(R.drawable.group_icon).placeholder(R.drawable.group_icon).into(binding.imageinchat);

        binding.sendArrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                arrayList.clear();
                final String message=binding.enterYourMessage.getText().toString();
                final Message_models message_models=new Message_models(message,sender_id);
                message_models.setTimestamp(String.valueOf(new Date().getTime()));
                binding.enterYourMessage.setText("");
                chatAdapter.notifyDataSetChanged();

                firebaseDatabase.getReference().child("Group Chats").push().setValue(message_models).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
            }
        });

        firebaseDatabase.getReference().child("Group Chats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrayList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Message_models models=dataSnapshot.getValue(Message_models.class);
                    arrayList.add(models);
                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}