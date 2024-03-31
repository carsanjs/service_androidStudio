package com.example.service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class inventario extends AppCompatActivity {

    ImageButton imageButton2,imageButton4, imageButton;
    Button btnconfig,camara,gps;
    EditText busqueda;

    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventario);

        imageButton = findViewById(R.id.atras);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton4 = findViewById(R.id.imageButton4);
        btnconfig = findViewById(R.id.btnconfig);
        camara = findViewById(R.id.camara);
        gps = findViewById(R.id.gps);

        busqueda = findViewById(R.id.busqueda);
        tableLayout = findViewById(R.id.tableLayout);

        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(inventario.this, Mcamara.class));
            }
        });
        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(inventario.this, Mgps.class));
            }
        });





        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(inventario.this, iniciasesion.class));
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(inventario.this, nuevo_producto.class));
            }
        });
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(inventario.this, eliminarProducto.class));
            }
        });

        btnconfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(inventario.this, editarUsuario.class));
            }
        });

        busqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buscarProductos(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });



    }
    private void buscarProductos(String textoBusqueda) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference productosRef = database.getReference("productos");

        productosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tableLayout.removeAllViews();

                TableRow headerRow = new TableRow(inventario.this);
                // Agregar encabezados de columna

                TextView header1 = new TextView(inventario.this);
                header1.setText("Codigo");
                header1.setBackgroundColor(Color.GRAY);
                headerRow.addView(header1);

                TextView header2 = new TextView(inventario.this);
                header2.setText("Nombre del Producto");
                header2.setBackgroundColor(Color.WHITE);
                headerRow.addView(header2);

                TextView header3 = new TextView(inventario.this);
                header3.setText("Cantidad Almacen");
                header3.setBackgroundColor(Color.GRAY);
                headerRow.addView(header3);

                TextView header4 = new TextView(inventario.this);
                header4.setText("Costo");
                header4.setBackgroundColor(Color.WHITE);
                headerRow.addView(header4);

                TextView header5 = new TextView(inventario.this);
                header5.setText("Precio");
                header5.setBackgroundColor(Color.GRAY);
                headerRow.addView(header5);

                tableLayout.addView(headerRow);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.exists()) {
                        String codigo = snapshot.child("code").getValue().toString();
                        String descripcion = snapshot.child("name").getValue().toString();
                        String nombreProducto = snapshot.child("amount").getValue().toString();
                        String precio = snapshot.child("value").getValue().toString();
                        String valor = snapshot.child("price").getValue().toString();

                        // Verificar si el producto coincide con el texto de búsqueda
                        if (codigo.contains(textoBusqueda) || descripcion.contains(textoBusqueda) || nombreProducto.contains(textoBusqueda) || precio.contains(textoBusqueda) || valor.contains(textoBusqueda)) {

                            TableRow row = new TableRow(inventario.this);

                            // Agregar texto a la celda
                            TextView casilla1 = new TextView(inventario.this);
                            casilla1.setText(codigo);
                            row.addView(casilla1);

                            TextView casilla2 = new TextView(inventario.this);
                            casilla2.setText(descripcion);
                            row.addView(casilla2);

                            TextView casilla3 = new TextView(inventario.this);
                            casilla3.setText(nombreProducto);
                            row.addView(casilla3);

                            TextView casilla4 = new TextView(inventario.this);
                            casilla4.setText(precio);
                            row.addView(casilla4);

                            TextView casilla5 = new TextView(inventario.this);
                            casilla5.setText(valor);
                            row.addView(casilla5);
                            tableLayout.addView(row);
                        }
                    }
                }
                if (!dataSnapshot.exists()) {
                    Context context = getApplicationContext();
                    String mensaje = "No existen producto registrados";
                    Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
    }
}