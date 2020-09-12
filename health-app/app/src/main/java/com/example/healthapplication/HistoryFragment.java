package com.example.healthapplication;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class HistoryFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    Button viewDataBtn,viewbtn;
    TextView textView3;
    int year, month, day;
    String date ="";
    ProgressDialog pd;
    FirebaseFirestore db;
    private FirebaseUser user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);


    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        viewDataBtn = (Button) view.findViewById(R.id.button1);
        viewbtn = (Button) view.findViewById(R.id.viewbtn);
        //cancelBtn = (Button) view.findViewById(R.id.cancelBtn);

        textView3 = (TextView) view.findViewById(R.id.textView3);

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        viewDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate=Calendar.getInstance();
                year=mcurrentDate.get(Calendar.YEAR);
                month=mcurrentDate.get(Calendar.MONTH);
                day=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog mDatePicker =new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday)
                    {
                        textView3.setText(new StringBuilder().append(selectedyear).append(" - ").append(selectedmonth+1).append(" - ").append(selectedday));
                        int month_k=selectedmonth+1;

                    }
                },year, month, day);
                mDatePicker.setTitle("Please select date");
                // TODO Hide Future Date Here
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());

                // TODO Hide Past Date Here
                //  mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis());
                mDatePicker.show();
            }
        });

        viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView viewDataTV = view.findViewById(R.id.viewDataTV);
                date=textView3.getText().toString();
                String name =user.getUid();

                db.collection("Daily data").document(date+name).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){
                                    String steps = documentSnapshot.getString("steps");
                                    String hydration = documentSnapshot.getString("hydration");
                                    String sleep = documentSnapshot.getString("sleep");
                                    String vegetables = documentSnapshot.getString("vegetables");
                                    String fruits = documentSnapshot.getString("fruits");
                                    String calories = documentSnapshot.getString("calories");
                                    viewDataTV.setText("Steps : "+steps+"\n"+"Hydration : "+hydration+"\n"+"Sleep : "+sleep
                                            +"\n"+"Calories : "+calories+"\n"+"Fruits : "+fruits+"\n"+"Vegetables : "+vegetables);

                                }
                                else {
                                    viewDataTV.setText("No data was found on "+date);
                                    Toast.makeText(getActivity(),"Document doesnot exist",Toast.LENGTH_LONG).show();
                                }

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();

                            }
                        });















               /* DocumentReference docRef = db.collection("Daily data").document(date);

                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("", "DocumentSnapshot data: " + document.getData().values());

                                String temp="";

                                for(int i=0;i<(document.getData().values().toArray().length);i++){

                                    for(Object obj:(ArrayList)document.getData().values().toArray()[i]){
                                        temp=temp+obj.toString()+"\n";
                                    }
                                }
                                viewDataTV.setText(temp);

                            } else {
                                Log.d("", "No such document");
                            }
                        } else {
                            Log.d("", "get failed with ", task.getException());
                        }
                    }
                });*/


            }
        });


        /*cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });*/



    }


}
