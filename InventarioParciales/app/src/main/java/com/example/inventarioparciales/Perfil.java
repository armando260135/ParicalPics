package com.example.inventarioparciales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Perfil extends AppCompatActivity {
private String nombrePerfil, correoPerfil, telefono;
TextView tvNombrePerfil,tvCorreoPerfil;
CardView cardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        tvNombrePerfil = findViewById(R.id.txtnombreprofile);
        tvCorreoPerfil = findViewById(R.id.txtcorreoprofile);
        nombrePerfil = getIntent().getStringExtra("nombre_perfil");
        correoPerfil = getIntent().getStringExtra("correo_perfil");
        telefono = getIntent().getStringExtra("telefono_perfil");
        tvNombrePerfil.setText(nombrePerfil);
        tvCorreoPerfil.setText(correoPerfil);
        cardView = findViewById(R.id.cardMateria);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Perfil.this, MyAccount.class);
                i.putExtra("nombre_account", nombrePerfil);
                i.putExtra("correo_account", correoPerfil);
                i.putExtra("telefono_account",telefono);
                startActivity(i);
            }
        });

    }
}