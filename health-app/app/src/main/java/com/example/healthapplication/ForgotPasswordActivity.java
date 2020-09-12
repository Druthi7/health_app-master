package com.example.healthapplication;

/*
 * Name : ForgotPasswordActivity.java
 * Author : Jaswanthi Nannuru
 * Description : This code helps users to reset password for the Android application with the registered email.
 * Date of completion : 02/20/2020
 * */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    //Variables declaration
    private FirebaseAuth firebaseAuth;
    private EditText editTextUserEmail;
    private Button buttonForgotPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //Displaying name of the activity on the action bar
        getSupportActionBar().setTitle("Reset Password");
        progressDialog = new ProgressDialog(this);

        //Getting references for the widgets in the activity_forgot_password.xml
        editTextUserEmail = (EditText) findViewById(R.id.editTextUserEmail);
        buttonForgotPassword = (Button) findViewById(R.id.buttonForgotPassword);
        firebaseAuth = FirebaseAuth.getInstance();



        //Enabling OnClickListener for the button
        buttonForgotPassword.setOnClickListener(this);
    }

    ////Defining forgotPassword as an user defined method
    private void forgotPassword(){

        //Defining and initializing variable
        String email = editTextUserEmail.getText().toString().trim();


        if(TextUtils.isEmpty(email)){

            //Displaying Toast message to the user
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }

        //Creating and displaying progress dialog to the user
        progressDialog.setMessage("Sending link...");
        progressDialog.show();


        //Calling predefined firebase method to reset password  for the user
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){

                            //Displaying Toast message to the user
                            Toast.makeText(ForgotPasswordActivity.this,"Reset link sent successfully",Toast.LENGTH_LONG).show();
                            finish();

                            //Navigating to another activity using intent
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }
                        else{

                            //Displaying Toast message to the user
                            Toast.makeText(ForgotPasswordActivity.this,"Invalid email",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    //Enabling onClick for the buttons
    @Override
    public void onClick(View view) {
        if (view == buttonForgotPassword){
            forgotPassword();
        }

    }


}
