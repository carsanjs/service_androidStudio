package com.example.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class registro extends AppCompatActivity {

    private TextView registerid ,registerName, registerLast, registerEmail, registerPass;
    private Button btnRegister;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        this.registerid = findViewById(R.id.registerid);
        this.registerName = findViewById(R.id.registerName);
        this.registerLast = findViewById(R.id.registerLast);
        this.registerEmail = findViewById(R.id.registerEmail);
        this.registerPass = findViewById(R.id.registerPass);

        this.database = FirebaseDatabase.getInstance().getReference("usuarios");


        this.btnRegister = (Button) findViewById(R.id.btnRegister);
        this.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id,name, last, email, pass;
                id = registerid.getText().toString();
                name = registerName.getText().toString();
                last = registerLast.getText().toString();
                email = registerEmail.getText().toString();
                pass = registerPass.getText().toString();

                if (id.isEmpty() && name.isEmpty() && last.isEmpty()&& email.isEmpty() && pass.isEmpty()){

                    CharSequence text = "Algunos campos se encuentran vacios";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                }else{

                    Map<String, Object> map = new HashMap<>();

                    map.put("id",id);
                    map.put("name", name);
                    map.put("lastname", last);
                    map.put("email", email);
                    map.put("password", pass);
                    database.child(id).updateChildren(map);


                    CharSequence text = "Se ha registrado el usuario correctamente!!";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(registro.this, iniciasesion.class));
                }
            }
        });


    }
}