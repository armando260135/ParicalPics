package com.example.inventarioparciales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Inicio3 extends AppCompatActivity {
Button btnRegister,btnInvited;
TextView tvLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio3);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btnRegister=findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.irAlLogIn);
        btnInvited = findViewById(R.id.btnInvited);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Inicio3.this, Login.class);
                startActivity(i);
                tvLogin.setEnabled(false);
            }
        });
        // evento de escuchapara registrar
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Inicio3.this, Register.class);
                startActivity(i);
               btnRegister.setEnabled(false);
            }
        });
        btnInvited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Inicio3.this, Home.class);
                i.putExtra("nombre", "invitado");
                i.putExtra("correo","invitado@gmail.com");
                i.putExtra("telefono","1234567891");
                startActivity(i);
                btnInvited.setEnabled(false);
            }
        });

    }
    @Override
    public void onResume(){
        super.onResume();
        btnRegister.setEnabled(true);
        btnInvited.setEnabled(true);
        tvLogin.setEnabled(true);

    }

}