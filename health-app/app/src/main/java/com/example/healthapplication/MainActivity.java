package com.example.healthapplication;

/*
 * Name : MainActivity.java
 * Author : Rudra Teja Potturi
 * Description : This code helps to create tabbed bar using fragments.
 * Date of Completion : 03/02/2020
 * */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    //Declaring variables
    private FirebaseAuth firebaseAuth;

    //Creating menu for the ease of access to the application
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                finish();

                //Navigating to another activity using intent
                startActivity(new Intent(this, ProfileActivity.class));
                return true;

            case R.id.item2:
                finish();

                //Navigating to another activity using intent
                startActivity(new Intent(this, HelpActivity.class));
                return true;

            case R.id.item3:
                firebaseAuth.signOut();
                finish();

                //Navigating to another activity using intent
                startActivity(new Intent(this, LoginActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creating an instance for firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new SelfFragment()).commit();
    }

    //Navigating between fragments using BottomNavigationView
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_self:
                            selectedFragment = new SelfFragment();
                            break;

                        case R.id.nav_group:
                            selectedFragment = new GroupFragment();
                            break;
                        case R.id.nav_history:
                            selectedFragment = new HistoryFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}
