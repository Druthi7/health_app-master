package com.example.healthapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth firebaseAuth;
    private Button btn;
    FirebaseFirestore db;

    //private StorageReference mStorageRef;


    //private ImageView editTextImage;
    private CircleImageView ProfileImage;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;


    private TextView userId;
    private TextView firstName;
    private TextView lastName;
    private TextView nickName;
    private EditText etUserId;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etNickName;
    FloatingActionButton fab;
    String pUserId,pfirstName,plastName,pnickName;
    private FirebaseUser user;


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
                startActivity(new Intent(this, MainActivity.class));
                return true;


            case R.id.item2:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profile");
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        //mStorageRef = FirebaseStorage.getInstance().getReference();

        ProfileImage = (CircleImageView) findViewById(R.id.profileImg);

        btn = (Button) findViewById(R.id.doneBtn);
        //editTextImage = (ImageView) findViewById(R.id.imageViewEdit);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        btn.setOnClickListener(this);


        userId = (TextView) findViewById(R.id.tvUserId);
        firstName = (TextView) findViewById(R.id.tvFirstName);
        lastName = (TextView) findViewById(R.id.tvLastName);
        nickName = (TextView) findViewById(R.id.tvNickName);
        etUserId = (EditText) findViewById(R.id.etUserId);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etNickName = (EditText) findViewById(R.id.etNickName);


        etUserId.setVisibility(View.INVISIBLE);
        etFirstName.setVisibility(View.INVISIBLE);
        etLastName.setVisibility(View.INVISIBLE);
        etNickName.setVisibility(View.INVISIBLE);
        btn.setVisibility(View.INVISIBLE);

        String name =user.getUid();


        db.collection("User data").document("Profile").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            String user_id = documentSnapshot.getString("User Id");
                            String first_name = documentSnapshot.getString("First Name");
                            String last_name = documentSnapshot.getString("Last Name");
                            String nick_name = documentSnapshot.getString("Nick Name");

                           userId.setText(user_id);
                           firstName.setText(first_name);
                           lastName.setText(last_name);
                           nickName.setText(nick_name);
                           etUserId.setText(user_id);
                           etFirstName.setText(first_name);
                           etLastName.setText(last_name);
                           etNickName.setText(nick_name);




                        }
                        else {
                            Toast.makeText(ProfileActivity.this,"Document doesnot exist",Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this,"Error",Toast.LENGTH_LONG).show();

                    }
                });


        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ProfileImage.setImageBitmap(bitmap);




                ContextWrapper cw = new ContextWrapper(getApplicationContext());
                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                File file = new File(directory, "UniqueFileName" + ".jpg");
                if (!file.exists()) {
                    Log.d("path", file.toString());
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.flush();
                        fos.close();
                        Bitmap bm = BitmapFactory.decodeFile("/imageDir/UniqueFileName.jpg");
                        ProfileImage.setImageBitmap(bm);
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                }






            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onClick(View view){
        if(view == btn){

            String nick = etNickName.getText().toString().trim();
            String uId = etUserId.getText().toString().trim();
            String fname = etFirstName.getText().toString();
            String lname = etLastName.getText().toString();

            Map<String, Object> doc = new HashMap<>();
            doc.put("Nick Name",nick);
            doc.put("User Id",uId);
            doc.put("First Name",fname);
            doc.put("Last Name",lname);

            db.collection("User data").document("Profile").update(doc)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });



            finish();
            startActivity(new Intent(this,ProfileActivity.class));
        }

        if(view == fab){


            userId.setVisibility(View.INVISIBLE);
            firstName.setVisibility(View.INVISIBLE);
            lastName.setVisibility(View.INVISIBLE);
            nickName.setVisibility(View.INVISIBLE);
            etUserId.setVisibility(View.VISIBLE);
            etFirstName.setVisibility(View.VISIBLE);
            etLastName.setVisibility(View.VISIBLE);
            etNickName.setVisibility(View.VISIBLE);
            btn.setVisibility(View.VISIBLE);




        }

    }



}
