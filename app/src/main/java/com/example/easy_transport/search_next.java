package com.example.easy_transport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class search_next extends AppCompatActivity {

    TextView cname,cemail,caddress,about,vehicles;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_next);

        getSupportActionBar().hide();

        cname = findViewById(R.id.txtsenecname);
        cemail = findViewById(R.id.txtsenecemail);
        caddress = findViewById(R.id.txtsenecaddress);
        about = findViewById(R.id.txtseneabout);
        vehicles = findViewById(R.id.txtsenevehicle);
        firebaseFirestore = FirebaseFirestore.getInstance();

        Intent i = getIntent();
        String con = i.getStringExtra("contact");
        String veh = i.getStringExtra("vehicle");

        firebaseFirestore.collection("Register").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d:list){
                            User_register user = d.toObject(User_register.class);
                            if (con.equals(user.getContact())) {
                                cname.setText(user.getCompanyname());
                                cemail.setText(user.getCompayemail());
                                caddress.setText(user.getAddress());
                                about.setText(user.getAboutCompany());
                                vehicles.setText("Vehicle : "+veh);
                            }
                        }
                    }
                });

    }
}