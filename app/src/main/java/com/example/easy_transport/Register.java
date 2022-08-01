package com.example.easy_transport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register extends AppCompatActivity {

    Button register;
    EditText about,cname,contact,mail,address;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        getSupportActionBar().hide();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(Register.this);
        progressDialog.setTitle("Register");
        progressDialog.setMessage("Register your Company...");

       register = findViewById(R.id.btnfillregister);
       about = findViewById(R.id.edaboutcompany);
       cname = findViewById(R.id.edcompanyname);
       contact = findViewById(R.id.edcontactregister);
       mail = findViewById(R.id.edcompanyemailid);
       address = findViewById(R.id.edaddress);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cname.getText().toString().equals("") || mail.getText().toString().equals("") ||
                        contact.getText().toString().equals("") || address.getText().toString().equals("") ||
                        about.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "Please fill-Up all the details..", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog.show();
                    firebaseFirestore.collection("Register").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                User_register user = d.toObject(User_register.class);
                                String st = user.getContact();
                                if (st.equals(contact.getText().toString())) {
                                    check = true;
                                    progressDialog.dismiss();
                                    Toast.makeText(Register.this, "You are already registered..", Toast.LENGTH_SHORT).show();
                                }
                            }
                            if (check == false) {
                                DocumentReference documentReference = firebaseFirestore.collection("Register").document(contact.getText().toString());
                                User_register user = new User_register(
                                        about.getText().toString(),
                                        cname.getText().toString(),
                                        contact.getText().toString(),
                                        mail.getText().toString(),
                                        address.getText().toString()

                                );
                                documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Toast.makeText(Register.this, "Register Successfully..!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Register.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

    }
}
