package com.example.my_whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.my_whatsapp.Adapters.ChatAdapter;
import com.example.my_whatsapp.Fragments.Chat_Fragment;
import com.example.my_whatsapp.Models.MainScreen;
import com.example.my_whatsapp.Models.Message_models;
import com.example.my_whatsapp.databinding.ActivityChatDetailedBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.Date;

public class Chat_detailed_Activity extends AppCompatActivity {

    ActivityChatDetailedBinding binding;

    FirebaseDatabase database;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityChatDetailedBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();


        final String sender_id = mAuth.getCurrentUser().getUid();

        // final keyword isiliye taki isi khi par bhi global access kar sake...

        String reciever_id = getIntent().getStringExtra("userId");
        String userName = getIntent().getStringExtra("userName");
        String profile_image = getIntent().getStringExtra("profileImage");


        binding.usernameChatScreen.setText(userName);


        Picasso.get().load(profile_image).placeholder(R.drawable.yellowavatar).into(binding.imageinchat);

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Chat_detailed_Activity.this, MainScreen.class);
                startActivity(intent);

            }
        });

        final ArrayList<Message_models> arrayList = new ArrayList<>();
        final ChatAdapter Chatadapter = new ChatAdapter(arrayList, this);

        binding.chatRecyclerview.setAdapter(Chatadapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.chatRecyclerview.setLayoutManager(linearLayoutManager);


        final String Sender_Room = sender_id + reciever_id;
        //we have taken both the id because one sender deleted the message from the chat it should not be deleted from the receiver ends and vice versa...
        final String Reciever_Room = reciever_id + sender_id;


        database.getReference().child("chats").child(Sender_Room).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {


                    Message_models models = dataSnapshot.getValue(Message_models.class);
                    arrayList.add(models);
                }
                Chatadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.sendArrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String message = binding.enterYourMessage.getText().toString();
                //yeh message model isiliye liya kyunki isime hamne senderId or message store kraya tha hamesha data kisi ka bhi uske model me se ata hai...


                final Message_models message_models = new Message_models(message, sender_id);

                message_models.setTimestamp(String.valueOf((new Date().getTime())));
                binding.enterYourMessage.setText("");

                // No we have to store the messages or chats and ID in the Database...
                // In set value we passed the above message_models in order to set the value of sender_id and the message



                //In Firebase, the push() method generates a unique key for each new child node added to a specified database reference.
                // This is particularly useful when you want to add items to a list or collection without explicitly specifying the key for each item.
                database.getReference().child("chats").child(Sender_Room).push().setValue(message_models).addOnSuccessListener(new OnSuccessListener<Void>() {

                    // first one is used to store the message of sender in the database...

                    @Override
                    public void onSuccess(Void aVoid) {
                        database.getReference().child("chats").child(Reciever_Room).push().setValue(message_models).addOnSuccessListener(new OnSuccessListener<Void>() {

                            //This one is used to store the message of the receiver in the database..

                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });
            }
        });
    }
}