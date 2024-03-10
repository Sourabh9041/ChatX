package com.example.my_whatsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_whatsapp.Models.Message_models;
import com.example.my_whatsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<Message_models> arrayList;
    Context context;

    FirebaseDatabase database;
    FirebaseAuth mAuth;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == Sender_View_Type) {

            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender, parent, false);
            return new SenderViewHolder(view);
        } else {

            View view = LayoutInflater.from(context).inflate(R.layout.sample_reciever, parent, false);
            return new RecieverViewHolder(view);

        }
    }

    int Sender_View_Type = 1;
    int Reciever_View_Type = 2;

    public ChatAdapter(ArrayList<Message_models> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }


    @Override
    public int getItemViewType(int position) {
        if (arrayList.get(position).getuId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            return Sender_View_Type;
        } else {
            return Reciever_View_Type;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Message_models message_models = arrayList.get(position);
        if (holder.getClass() == SenderViewHolder.class) {
            ((SenderViewHolder) holder).senderMessage.setText(message_models.getMessage());
        } else {
            ((RecieverViewHolder) holder).recieverMessage.setText(message_models.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder {

        TextView recieverMessage, recieverTimestamp;

        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);

            recieverMessage = itemView.findViewById(R.id.recieving_message);
            recieverTimestamp = itemView.findViewById(R.id.time_keeper_reciever);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {

        TextView senderMessage, senderTimestamp;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMessage = itemView.findViewById(R.id.sending_message);
            senderTimestamp = itemView.findViewById(R.id.time_keeper_sender);


        }
    }
}

