package com.example.easy_transport;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signUp extends AppCompatActivity {

    EditText mail,crpasss,username;
    Button signup,signin,withgoogle;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();
        mail = findViewById(R.id.edemailsignup);
        crpasss = findViewById(R.id.edcrpasssignup);
        username = findViewById(R.id.edusernamesignup);
        signup = findViewById(R.id.btnsignup);
        signin = findViewById(R.id.btnsignin);
        withgoogle = findViewById(R.id.btngooglesignup);
        progressDialog = new ProgressDialog(signUp.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Creating your Account...");

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signUp.this, signIn.class);
                startActivity(intent);
                finish();
            }
        });

        withgoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signUp.this, signIn.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mail.getText().toString().equals("") || crpasss.getText().toString().equals("") || username.getText().toString().equals("")) {
                    Toast.makeText(signUp.this, "Please enter your details..", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.show();
                    auth.createUserWithEmailAndPassword(mail.getText().toString(), crpasss.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    User_signin user = new User_signin(mail.getText().toString(), crpasss.getText().toString(), username.getText().toString());
                                                    databaseReference.child("Users").child(auth.getCurrentUser().getUid()).setValue(user);
                                                    Toast.makeText(signUp.this, "User Created Successfully, Please verify your Email-id..!", Toast.LENGTH_SHORT).show();
                                                    progressDialog.dismiss();
                                                    Intent intent = new Intent(signUp.this, signIn.class);
                                                    startActivity(intent);
                                                    mail.setText("");
                                                    crpasss.setText("");
                                                    username.setText("");
                                                } else {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(signUp.this, "Something went wrong..!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(signUp.this, "User NOT created, something went wrong..!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }


}