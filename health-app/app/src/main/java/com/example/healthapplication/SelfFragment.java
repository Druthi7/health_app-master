package com.example.healthapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DatabaseReference;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import static android.widget.Toast.makeText;

public class SelfFragment extends Fragment  {

    EditText txtSteps,txtHydration,txtCAl,txtFruit,txtVeg,txtSleep;
    TextView textView3;
    Button btnDone,button1,viewButton;
    //DatabaseReference reff;
    //DailyData dailyData;
    //private int steps;

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private FirebaseUser user;
    int year, month, day;
    String date;
    StringBuilder fix;


    ProgressDialog pd;
    FirebaseFirestore db;
    public static ArrayList<String> students;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_self, container, false);

        //txtSteps = (EditText) getView().findViewById(R.id.stepET);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize your view here for use view.findViewById("your view id")
        txtSteps = (EditText) view.findViewById(R.id.stepET);
        txtSleep = (EditText) view.findViewById(R.id.sleepET);
        txtHydration = (EditText) view.findViewById(R.id.hydrationET);
        txtCAl = (EditText) view.findViewById(R.id.calET);
        txtFruit = (EditText) view.findViewById(R.id.fruitET);
        txtVeg = (EditText) view.findViewById(R.id.vegET);
        btnDone = (Button) view.findViewById(R.id.doneBtn);
        button1 = (Button) view.findViewById(R.id.button1);
        viewButton = (Button) view.findViewById(R.id.viewButton);
        textView3 = (TextView) view.findViewById(R.id.textView3);
        students = new ArrayList<>();
        students.add("Today's data");
        pd = new ProgressDialog(getActivity());
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate=Calendar.getInstance();
                year=mcurrentDate.get(Calendar.YEAR);
                month=mcurrentDate.get(Calendar.MONTH);
                day=mcurrentDate.get(Calendar.DAY_OF_MONTH);
                SimpleDateFormat dfDate = new SimpleDateFormat("MMM dd yyyy ");

                final DatePickerDialog   mDatePicker =new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday)
                    {



                        textView3.setText(new StringBuilder().append(selectedyear).append(" - ").append(selectedmonth+1).append(" - ").append(selectedday));
                        //int month_k=selectedmonth+1;
                       // setDate(textView3);


                    }


                },year, month, day);
                mDatePicker.setTitle("Please select date");
                // TODO Hide Future Date Here
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());

                // TODO Hide Past Date Here
                //  mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis());
                //String md = dfDate.format(mDatePicker.getDatePicker());
                //textView3.setText(md);
                //dfDate.format(mDatePicker);
                //String md = dfDate.format(mDatePicker);
                //textView3.setText(md);
                //DatePicker dd = mDatePicker.getDatePicker();

                mDatePicker.show();






            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                                    txtSteps.setText(steps);
                                    txtHydration.setText(hydration);
                                    txtSleep.setText(sleep);
                                    txtCAl.setText(calories);
                                    txtFruit.setText(fruits);
                                    txtVeg.setText(vegetables);
                                    btnDone.setText("Update");


                                    //viewButton.setVisibility(View.INVISIBLE);
                                    //viewDataTV.setText("Steps : "+steps+"\n"+"Hydration : "+hydration+"\n"+"Sleep : "+sleep
                                    // +"\n"+"Calories : "+calories+"\n"+"Fruits : "+fruits+"\n"+"Vegetables : "+vegetables);

                                }
                                else {
                                    //viewDataTV.setText("No data was found on "+date);
                                    txtSteps.setText("");
                                    txtHydration.setText("");
                                    txtSleep.setText("");
                                    txtCAl.setText("");
                                    txtFruit.setText("");
                                    txtVeg.setText("");
                                    btnDone.setText("Add");
                                    Toast.makeText(getActivity(),"No Data exist",Toast.LENGTH_LONG).show();

                                }

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();

                            }
                        });

            }
        });


        btnDone.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String steps = txtSteps.getText().toString().trim();
               String sleep = txtSleep.getText().toString().trim();
               String hydration = txtHydration.getText().toString().trim();
               String calories = txtCAl.getText().toString().trim();
               String fruits = txtFruit.getText().toString().trim();
               String vegetables = txtVeg.getText().toString().trim();

               uploadData(steps, sleep, hydration, calories,fruits,vegetables);

               txtSteps.setText(steps);
               txtSleep.setText(sleep);
               txtHydration.setText(hydration);
               txtCAl.setText(calories);
               txtFruit.setText(fruits);
               txtVeg.setText(vegetables);
           }
       });

    }

    private void uploadData(String steps,String sleep, String hydration, String calories, String fruits, String vegetables){



        pd.setTitle("Adding data");
        pd.show();
        String id = UUID.randomUUID().toString();
        date=textView3.getText().toString();
        String name =user.getUid();


        Map<String, Object> doc = new HashMap<>();
        doc.put("id",id);
        doc.put("steps",steps);
        doc.put("sleep",sleep);
        doc.put("hydration",hydration);
        doc.put("calories",calories);
        doc.put("fruits",fruits);
        doc.put("vegetables",vegetables);
        //doc.put("dateExample", new Timestamp(new Date()));

        db.collection("Daily data").document(date+name).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(getActivity(),"Data Added",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        pd.dismiss();
                        Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });



    }

    /*public void setDate (TextView view){

        Date today = (Date) textView3.getText();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");//formating according to my need
        String date = formatter.format(today);
        textView3.setText(date);
    }*/



}
