package com.example.inventarioparciales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Inicio3 extends AppCompatActivity {
Button btnRegister,btnInvited;
TextView tvLogin;
boolean estadoBotones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio3);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btnRegister=findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.irAlLogIn);
        btnInvited = findViewById(R.id.btnInvited);
       /* estadoBotones = getIntent().getBooleanExtra("estado_boton", false);*/

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Inicio3.this, Login.class);
                startActivity(i);
            }
        });
        // evento de escuchapara registrar
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Inicio3.this, Register.class);
                startActivity(i);
               /* btnRegister.setEnabled(estadoBotones);*/
            }
        });
        btnInvited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Inicio3.this, Home.class);
                i.putExtra("nombre", "invitado");
                i.putExtra("correo","invitado@gmail.com");
                startActivity(i);
            }
        });

    }

}