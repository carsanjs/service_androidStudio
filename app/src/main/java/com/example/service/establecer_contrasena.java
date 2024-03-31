package com.example.service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

import java.util.HashMap;
import java.util.Map;

public class establecer_contrasena extends AppCompatActivity {

    TextView txtid,txtemail,txtpass,txtpass1;
    Button button2,button3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.establecer_contrasena);

        txtid = findViewById(R.id.txtid);
        txtemail = findViewById(R.id.txtemail);
        txtpass = findViewById(R.id.txtpass);
        txtpass1 = findViewById(R.id.txtpass1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button3.setOnClickListener(View -> {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("usuarios");
            //myRef.child(textnumerodocumento.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            //if(myRef.child(textnumerodocumento.getText().toString()).equals(database.getReference().setValue(textnumerodocumento))){
            myRef.child(txtid.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(txtid.getText().toString().isEmpty() ||
                            txtemail.getText().toString().isEmpty() ||
                            txtpass.getText().toString().isEmpty() || txtpass1.getText().toString().isEmpty()){
                        Context context = getApplicationContext();
                        String mensaje = "INGRESE LOS DATOS";
                        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                    }else{
                        if (txtpass.getText().toString().equals(txtpass1.getText().toString())){
                            Map<String, Object> map = new HashMap<>();

                            map.put("id",txtid.getText().toString());
                            map.put("email", txtemail.getText().toString());
                            map.put("password", txtpass.getText().toString());
                            map.put("password", txtpass1.getText().toString());
                            myRef.child(txtid.getText().toString()).updateChildren(map);

                            Context context = getApplicationContext();
                            String mensaje = "CONTRASEÑA ACTUALIZADA";
                            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();

                            clear();
                        }else{
                            Context context = getApplicationContext();
                            String mensaje = "LAS CONTRASEÑAS NO COINCIDEN.";
                            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (!snapshot.exists()){
                        Context context = getApplicationContext();
                        String mensaje = "ESTE USUARIO NO EXISTE EN LA BASE DE DATOS.";
                        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    String mensaje = "La búsqueda de datos fue cancelada. Error: " + error.getMessage();
                    Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
    private void clear(){
        txtid.setText("");
        txtemail.setText("");
        txtpass.setText("");
        txtpass1.setText("");
    }
}