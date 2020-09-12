package com.example.healthapplication;

/*
 * Name : HelpActivity.java
 * Author : Sai Nikhil Pippara
 * Description : This code helps users to contact with the admin using the Android application.
 * Date of completion : 03/02/2020
 * */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener {

    //Variables declaration
    private FirebaseAuth firebaseAuth;
    private Button btn;

    //Creating menu on the action bar for ease of access to the users.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.common_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                finish();

                //Navigating to another activity using intent
                startActivity(new Intent(this, MainActivity.class));
                return true;


            case R.id.item2:
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
        setContentView(R.layout.activity_help);

        //Displaying title on the action bar
        getSupportActionBar().setTitle("Contact");

        //Creating instance of firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        //Getting references for the widgets in the activity_help.xml
        btn = (Button) findViewById(R.id.doneBtn);

        //Creating onclick listener for the button
        btn.setOnClickListener(this);
    }
    //Enabling onclick listener
    @Override
    public void onClick(View view){
        if(view == btn){
            finish();

            //Navigating to another activity using intent
            startActivity(new Intent(this,MainActivity.class));
        }
    }

}
