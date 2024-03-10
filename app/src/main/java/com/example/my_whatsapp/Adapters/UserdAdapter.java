package com.example.my_whatsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_whatsapp.Chat_detailed_Activity;
import com.example.my_whatsapp.Models.Users;
import com.example.my_whatsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;


public class UserdAdapter extends RecyclerView.Adapter<UserdAdapter.ViewHolder> {

    FirebaseDatabase database;
    FirebaseAuth mAuth;

    ImageView profileimage;
    TextView username_sample;
    TextView lastmessage;

    ArrayList<Users> arrayList;
    Context context;

    public UserdAdapter(ArrayList<Users> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_show_users, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        Users user = arrayList.get(position);

        Picasso.get().load(user.getProfile_picture()).placeholder(R.drawable.yellowavatar).into(holder.profileimage);
        holder.username_sample.setText(user.getUsername());

        database.getReference().child("chats").child(mAuth.getCurrentUser().getUid() + user.getUserId())
                .orderByChild("timestamp").limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChildren()) {

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                holder.lastmessage.setText(dataSnapshot.child("message").getValue(String.class));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        //yeh item view par isiliye on click listener lagaya kyunki yeh automatically jo onBindViewHolder me last me int posistion uske hisab se sbhi jo first pr ho 2nd par click listener laga dega!!

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, Chat_detailed_Activity.class);
                intent.putExtra("userId", user.getUserId());
                intent.putExtra("userName", user.getUsername());
                intent.putExtra("profileImage", user.getProfile_picture());
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profileimage;
        TextView username_sample;
        TextView lastmessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileimage = itemView.findViewById(R.id.profileimage);
            username_sample = itemView.findViewById(R.id.username_sample);
            lastmessage = itemView.findViewById(R.id.lastmessage);


        }
    }
}
