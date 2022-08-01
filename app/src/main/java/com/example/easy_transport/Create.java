package com.example.easy_transport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Create extends AppCompatActivity {

    EditText from,dest,date,contact,vehicle,space,routeid;
    Button create;
    ProgressDialog progressDialog;
    FirebaseFirestore db;
    boolean route = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        getSupportActionBar().hide();

        from = findViewById(R.id.edcrfrom);
        dest = findViewById(R.id.edcrdest);
        date = findViewById(R.id.edcrdate);
        contact = findViewById(R.id.edcrcontact);
        vehicle = findViewById(R.id.edcrvehicle);
        space = findViewById(R.id.edcrspace);
        create = findViewById(R.id.btncrcreate);
        routeid = findViewById(R.id.edcrrouteid);
        progressDialog = new ProgressDialog(Create.this);
        progressDialog.setTitle("Route");
        progressDialog.setMessage("Creating your Route...");
        db = FirebaseFirestore.getInstance();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                Insertdata();
            }
        });
    }

    private void Insertdata() {
        if (from.getText().toString().equals("") || dest.getText().toString().equals("") ||
                date.getText().toString().equals("") || contact.getText().toString().equals("") ||
                vehicle.getText().toString().equals("") || space.getText().toString().equals("") ||
                routeid.getText().toString().equals("")
        ) {
            progressDialog.dismiss();
            Toast.makeText(this, "Please fill-Up all the details..", Toast.LENGTH_SHORT).show();
        }
        else {
            FirebaseDatabase.getInstance().getReference("Common Routes").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    boolean check = false;
                    if (dataSnapshot.exists()){
                        for (DataSnapshot ds:dataSnapshot.getChildren()){
                            String st = ds.getKey();
                            if (st.equals(routeid.getText().toString())){
                                check = true;
                                progressDialog.dismiss();
                                Toast.makeText(Create.this, "Route-id is already used, Please try something else..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    if (!check){
                        db.collection("Register").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d:list){
                                    User_register user = d.toObject(User_register.class);
                                    String st = user.getContact();
                                    if (st.equals(contact.getText().toString())){
                                        route = true;
                                        finalinsertdata();
                                        break;
                                    }
                                }
                                if (route == false){
                                    progressDialog.dismiss();
                                    Toast.makeText(Create.this, "Please register first..", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
        }

    }
    private void finalinsertdata(){

        Map<String, String> map = new HashMap<>();
        map.put("from", from.getText().toString().trim());
        map.put("dest", dest.getText().toString().trim());
        map.put("date", date.getText().toString().trim());
        map.put("contact", contact.getText().toString().trim());
        map.put("vehicle", vehicle.getText().toString().trim());
        map.put("space", space.getText().toString().trim());
        map.put("routeid", routeid.getText().toString().trim());

        FirebaseDatabase.getInstance().getReference("Routes").child(contact.getText().toString()).child(routeid.getText().toString()).setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(Create.this, "Route Created..!!", Toast.LENGTH_SHORT).show();
                            FirebaseDatabase.getInstance().getReference("Common Routes").child(routeid.getText().toString()).setValue(map);
                            from.setText("");
                            dest.setText("");
                            date.setText("");
                            contact.setText("");
                            vehicle.setText("");
                            space.setText("");
                            routeid.setText("");
                            Intent intent = new Intent(Create.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}
