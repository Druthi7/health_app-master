package com.example.healthapplication;

/*
 * Name : LoginActivity.java
 * Author : Rudra Teja Potturi
 * Description : This code helps users to login into the Android application with the registered credentials.
 * Date of completion : 02/14/2020
 * */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //Variables declaration
    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;
    private TextView tvForgotPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Creating a firebase auth instance
        firebaseAuth = FirebaseAuth.getInstance();

        //Intent to navigate to Login page
        if (firebaseAuth.getCurrentUser() != null){
            finish();

            //Navigating to MainActivity.class using intent
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        //Getting references for the widgets in the activity_login.xml
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignin);
        textViewSignup = (TextView) findViewById(R.id.textViewSignUp);
        tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        progressDialog = new ProgressDialog(this);

        //Creating OnClickListener for the buttons
        buttonSignIn.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);


    }


    //Defining userLogin as an user defined method
    private void userLogin(){

        //Defining and initializing variables
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){

            //Displaying Toast message to the user
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){

            //Displaying Toast message to the user
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }

        //Progress dialog to the user as a response
        progressDialog.setMessage("Signing in User...");
        progressDialog.show();

        //Calling predefined firebase method to sign in an user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                finish();

                                //Navigating to another activity using intent
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                            else {

                                //Displaying Toast message to the user
                                Toast.makeText(LoginActivity.this,"Please Verify your email address",Toast.LENGTH_LONG).show();
                            }
                        }
                        else{

                            //Displaying Toast message to the user
                            Toast.makeText(LoginActivity.this,"Invalid Credentials",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    //Enabling onClick for the buttons
    @Override
    public void onClick(View view) {
        if (view == buttonSignIn){
            userLogin();
        }

        if(view == textViewSignup){
            finish();

            //Navigating to another activity using intent
            startActivity(new Intent(this,SignupActivity.class));
        }

        if(view == tvForgotPassword){
            finish();

            //Navigating to another activity using intent
            startActivity(new Intent(this,ForgotPasswordActivity.class));
        }
    }
}
