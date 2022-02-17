package com.example.inventarioparciales;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MyAccount extends AppCompatActivity {
    private String nombrePerfil, correoPerfil, telefono;
    TextView name, email, phone, username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        nombrePerfil = getIntent().getStringExtra("nombre_account");
        correoPerfil = getIntent().getStringExtra("correo_account");
        telefono = getIntent().getStringExtra("telefono_account");
        name = findViewById(R.id.tvNameProfile);
        email = findViewById(R.id.tvEmailProfile);
        phone = findViewById(R.id.tvTelefonoProfile);
        username = findViewById(R.id.tvUsernameProfile);
        username.setText(nombrePerfil);
        name.setText(nombrePerfil);
        email.setText(correoPerfil);
        phone.setText(telefono);


    }
}