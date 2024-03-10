package com.example.my_whatsapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.my_whatsapp.Adapters.UserdAdapter;
import com.example.my_whatsapp.Models.Users;
import com.example.my_whatsapp.R;
import com.example.my_whatsapp.databinding.FragmentChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Chat_Fragment extends Fragment {
    public Chat_Fragment() {

    }

    FragmentChatBinding binding;
    ArrayList<Users> arrayList;
    FirebaseDatabase firebaseDatabase;
    UserdAdapter userdAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater, container, false);
        arrayList = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.chatRecyclerview.setLayoutManager(linearLayoutManager);


        firebaseDatabase.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users users = dataSnapshot.getValue(Users.class);
                    users.setUserId(dataSnapshot.getKey());
                    arrayList.add(users);
                    userdAdapter = new UserdAdapter(arrayList, getContext());
                    binding.chatRecyclerview.setAdapter(userdAdapter);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
        return binding.getRoot();

    }
}