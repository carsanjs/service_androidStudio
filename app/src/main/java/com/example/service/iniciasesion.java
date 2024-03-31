package com.example.service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class iniciasesion extends AppCompatActivity {

    private TextView loginEmail, loginPass;
    private Button btnLogin;
    TextView textView5,textView6;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciasesion);

        this.loginEmail = findViewById(R.id.loginEmail);
        this.loginPass = findViewById(R.id.loginPass);
        this.textView5 = findViewById(R.id.textView5);
        this.textView6 = findViewById(R.id.textView6);

        this.database = FirebaseDatabase.getInstance().getReference("usuarios");

        this.btnLogin = (Button) findViewById(R.id.btnLogin);

        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(iniciasesion.this, registro.class));
            }
        });

        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(iniciasesion.this, establecer_contrasena.class));
            }
        });

        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email, pass;
                email = loginEmail.getText().toString();
                pass = loginPass.getText().toString();

                if (email.isEmpty() && pass.isEmpty()){
                    CharSequence text = "Los campos no pueden estar vacios";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                }else{
                    database.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                if (email.equals(dataSnapshot.child("email").getValue()) && pass.equals(dataSnapshot.child("password").getValue())){
                                    Intent intent = new Intent(getApplicationContext(), inventario.class);
                                    startActivity(intent);
                                    clear();
                                    return;
                                }
                            }
                            CharSequence text = "Usuario o contraseña incorrecta";
                            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }
    private void clear(){
        loginEmail.setText("");
        loginPass.setText("");
    }
}