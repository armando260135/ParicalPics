package com.example.inventarioparciales;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Perfil extends AppCompatActivity {
private String nombrePerfil, correoPerfil;
TextView tvNombrePerfil,tvCorreoPerfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        tvNombrePerfil = findViewById(R.id.txtnombreprofile);
        tvCorreoPerfil = findViewById(R.id.txtcorreoprofile);
        nombrePerfil = getIntent().getStringExtra("nombre_perfil");
        correoPerfil = getIntent().getStringExtra("correo_perfil");
        tvNombrePerfil.setText(nombrePerfil);
        tvCorreoPerfil.setText(correoPerfil);
    }
}