package com.example.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class eliminarProducto extends AppCompatActivity {

    EditText addCode;
    Button btndelete;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_producto);
        imageButton = findViewById(R.id.atras);
        addCode = findViewById(R.id.addCode);
        btndelete = findViewById(R.id.btndelete);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(eliminarProducto.this, inventario.class));
            }
        });
        this.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = addCode.getText().toString();

                if (code.isEmpty()){
                    CharSequence text = "El campo codigo no puede estar vacio";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                }else{
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("productos");
                    db.child(code).removeValue();
                    CharSequence text = "Se ha eliminado el producto correctamente";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                    clear();
                }
            }
        });

    }
    private void clear(){
        addCode.setText("");
    }
}