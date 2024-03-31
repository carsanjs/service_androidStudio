package com.example.service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class nuevo_producto extends AppCompatActivity {

    private TextView addCode, addName, addAmount, addValue, addPrice;
    private Button btnAdd, button4;
    private DatabaseReference database;

    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_producto);

        this.imageButton = findViewById(R.id.atras);
        this.addCode = findViewById(R.id.addCode);
        this.addName = findViewById(R.id.addName);
        this.addAmount = findViewById(R.id.addAmount);
        this.addValue = findViewById(R.id.addValue);
        this.addPrice = findViewById(R.id.addPrice);
        this.button4 = findViewById(R.id.button4);


        this.database = FirebaseDatabase.getInstance().getReference("productos");

        this.btnAdd = (Button) findViewById(R.id.btndelete);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(nuevo_producto.this, inventario.class));
            }
        });

        this.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code, name, amount, value, price;
                code = addCode.getText().toString();
                name = addName.getText().toString();
                amount = addAmount.getText().toString();
                value = addValue.getText().toString();
                price = addPrice.getText().toString();

                if (!code.isEmpty() && !name.isEmpty() && !amount.isEmpty() && !value.isEmpty() && !price.isEmpty()){
                    database.child(code).child("code").setValue(code);
                    database.child(code).child("name").setValue(name);
                    database.child(code).child("value").setValue(value);
                    database.child(code).child("amount").setValue(amount);
                    database.child(code).child("price").setValue(price);

                    CharSequence text = "Se ha agregado el producto correctamente!!";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                    clear();
                }else{
                    CharSequence text = "No se ha podido agregar el producto";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                }
            }
        });

        button4.setOnClickListener(view ->  {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("productos");
            myRef.child(addCode.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String code, name, amount, value, price;
                    code = addCode.getText().toString();
                    name = addName.getText().toString();
                    amount = addAmount.getText().toString();
                    value = addValue.getText().toString();
                    price = addPrice.getText().toString();
                    if (!code.isEmpty() && !name.isEmpty() && !amount.isEmpty() && !value.isEmpty() && !price.isEmpty()){
                        Map<String, Object> map = new HashMap<>();
                        map.put("code", addCode.getText().toString());
                        map.put("name", addName.getText().toString());
                        map.put("amount", addAmount.getText().toString());
                        map.put("value", addValue.getText().toString());
                        map.put("price", addPrice.getText().toString());
                        myRef.child(addCode.getText().toString()).updateChildren(map);

                        Context context = getApplicationContext();
                        String mensaje = "producto actualizado correctamente";
                        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                        clear();
                    }else{
                        Context context = getApplicationContext();
                        String mensaje = "Existen Campos Vacíos";
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
        addCode.setText("");
        addName.setText("");
        addAmount.setText("");
        addValue.setText("");
        addPrice.setText("");
    }
}