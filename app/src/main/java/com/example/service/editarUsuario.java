package com.example.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editarUsuario extends AppCompatActivity {

    private TextView wcName, wcLast, wcEmail,wcId;
    private Button wcbtnChange, wcbtnUpdate, wcbtnInv;
    private ImageButton atras;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_usuario);

        this.wcId = findViewById(R.id.editTextTextPersonName2);
        this.wcName = findViewById(R.id.wcName);
        this.wcLast = findViewById(R.id.wcLast);
        this.wcEmail = findViewById(R.id.wcEmail);

        this.database = FirebaseDatabase.getInstance().getReference("usuarios");

        this.atras = (ImageButton) findViewById(R.id.atras);
        this.atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        this.wcbtnChange = (Button) findViewById(R.id.wcbtnChange);
        this.wcbtnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = wcId.getText().toString();

                if (code.isEmpty()){
                    CharSequence text = "El campo codigo no puede estar vacio";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                }else{
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("usuarios");
                    db.child(code).removeValue();
                    CharSequence text = "Se ha eliminado el usuario correctamente";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                    clear();
                }
            }
        });

        this.wcbtnInv = (Button) findViewById(R.id.wcbtnInv);
        this.wcbtnInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), inventario.class);
                startActivity(intent);
            }
        });

        this.wcbtnUpdate = (Button) findViewById(R.id.wcbtnUpdate);
        this.wcbtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id, name, last, email;

                id = wcId.getText().toString();
                name = wcName.getText().toString();
                last = wcLast.getText().toString();
                email = wcEmail.getText().toString();

                if (!id.isEmpty() && !name.isEmpty() && !last.isEmpty() && !email.isEmpty()){
                    database.child(id).child("id").setValue(name);
                    database.child(id).child("name").setValue(name);
                    database.child(id).child("lastname").setValue(last);
                    database.child(id).child("email").setValue(email);

                    CharSequence text = "Usuario actualizado correctamente";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                }else{
                    CharSequence text = "No se pudieron actualizar los datos";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    private void clear(){
        wcId.setText("");
    }
}