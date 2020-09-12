package com.example.healthapplication;

/*
* Name : SignupActivity.java
* Author : Sai Nikhil Pippara
* Description : This code helps users to register into the Android application.
* Date of completion : 02/10/2020
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    //Variables declaration
    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private EditText editTextUserId;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextNickName;
    FirebaseFirestore db;
    FirebaseUser user;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Creating a firebase auth instance
        firebaseAuth = FirebaseAuth.getInstance();

        //Intent to navigate to Login page
        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        //Progress dialog to the user as a response
        progressDialog = new ProgressDialog(this);

        //creating an instance of firebase firestore
        db = FirebaseFirestore.getInstance();
        //user = FirebaseAuth.getInstance().getCurrentUser();


        //Getting references for the widgets in the activity_signup.xml
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);
        editTextUserId = (EditText) findViewById(R.id.editTextUserId);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextNickName = (EditText) findViewById(R.id.editTextNickName);

        //Enabling OnClickListener for the buttons
        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }


    //Defining registerUser as an user defined method
    private void registerUser(){

        //Defining and initializing variables
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String userId = editTextUserId.getText().toString().trim();
        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString().trim();
        String nickName = editTextNickName.getText().toString();


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

        //Creating and displaying progress dialog to the user
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        //Calling predefined firebase method to create an user
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){

                    //Sending verification email to the user
                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignupActivity.this,"Registered Successfully, Please check email for verification",Toast.LENGTH_LONG).show();
                                finish();
                                //Navigation to login activity using Intent
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                            }
                            else{
                                Toast.makeText(SignupActivity.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                }else{
                    //Toast.makeText(SignupActivity.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    Toast.makeText(SignupActivity.this,"Registration Failed, Please try again",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        //Calling user defined uploadData method
        uploadData(nickName,userId,firstName,lastName);
    }

    private void uploadData(String nickName, String userId, String firstName, String lastName) {

        //String id = UUID.randomUUID().toString();
        //name = user.getUid();

        //creating an instance for map
        Map<String, Object> doc = new HashMap<>();

        // Uploading data to the map instance
        doc.put("Nick Name",nickName);
        doc.put("User Id",userId);
        doc.put("First Name",firstName);
        doc.put("Last Name",lastName);

        //uploading data to the User data collection with Profile as document path using map instance
        db.collection("User data").document("Profile").set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(SignupActivity.this,"Data Added",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                        Toast.makeText(SignupActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });

    }

    //Enabling onClick for the buttons
    @Override
    public void onClick(View view) {

        if(view == buttonRegister){
            registerUser();
        }

        if(view == textViewSignin){
            startActivity(new Intent(this, LoginActivity.class));
        }

    }
}
