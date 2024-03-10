package com.example.my_whatsapp.Models;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.my_whatsapp.Adapters.ChatAdapter;
import com.example.my_whatsapp.Adapters.FragmentsAdapter;
import com.example.my_whatsapp.Adapters.UserdAdapter;
import com.example.my_whatsapp.Fragments.Chat_Fragment;
import com.example.my_whatsapp.Group_chat_detailed_Activity;
import com.example.my_whatsapp.R;
import com.example.my_whatsapp.databinding.ActivityMainScreenBinding;
import com.example.my_whatsapp.settings_Activity;
import com.example.my_whatsapp.sign_in;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    ActivityMainScreenBinding binding;

    RecyclerView recyclerView;

    ArrayList<Users> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainScreenBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mAuth = FirebaseAuth.getInstance();


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, new Chat_Fragment()) // Replace with your initial fragment
                .commit();


        binding.bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.Logout) {

                    mAuth.signOut();
                    Intent intent = new Intent(MainScreen.this, sign_in.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                }
                if (id == R.id.group_chatting) {
                    Intent intent2 = new Intent(MainScreen.this, Group_chat_detailed_Activity.class);
                    startActivity(intent2);
                    return true;
                }
                if (id == R.id.chats) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame, new Chat_Fragment()) // Replace with your fragment
                            .commit();
                    return true;
                }
                if (id == R.id.settings) {
                    Intent intent = new Intent(MainScreen.this, settings_Activity.class);
                    startActivity(intent);
                    return true;
                }


                return false;
            }
        });
    }
}

  /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.Logout) {

            mAuth.signOut();
            Intent intent = new Intent(MainScreen.this, sign_in.class);
            startActivity(intent);

        }

        if (item.getItemId() == R.id.settings) {
            // Handle the "Settings" item.
            return true;
        }

        if (item.getItemId() == R.id.group_chatting) {
            Intent intent2 = new Intent(MainScreen.this, Group_chat_detailed_Activity.class);
            startActivity(intent2);
            return true;
        }

        //return super.onOptionsItemSelected(item);
        return true;
    }


}
*/

