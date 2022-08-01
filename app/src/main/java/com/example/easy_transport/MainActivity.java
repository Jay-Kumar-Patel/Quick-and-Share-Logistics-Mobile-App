package com.example.easy_transport;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button create,edit,delete,search,register,logout;
    TextView name;
    ImageView img;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        register = findViewById(R.id.btnregister);
        create = findViewById(R.id.btncreate);
        edit = findViewById(R.id.btnedit);
        delete = findViewById(R.id.btndelete);
        search = findViewById(R.id.btnsearch);
        logout = findViewById(R.id.btnlogout);

        auth = FirebaseAuth.getInstance();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Delete.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Search.class);
                startActivity(intent);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Create.class);
                startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Edit.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Register.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Toast.makeText(MainActivity.this, "Logout successfully...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,signIn.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        name = findViewById(R.id.txtmainusername);
        img = findViewById(R.id.imageuser);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    User_google_signin user = snapshot.getValue(User_google_signin.class);
                    name.setText("Welcome,  "+user.getUsername());
                    Glide.with(MainActivity.this).load(user.getProfilepic()).into(img);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("userNameAndProfileError",error.getMessage());
            }
        });
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(MainActivity.this)
                .setIcon(R.drawable.warning)
                .setTitle("Confirm Exit")
                .setMessage("Are you sure, You want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
       // super.onBackPressed();
    }
}