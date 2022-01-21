package com.example.inventarioparciales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.inventarioparciales.databases.DBHelper;

public class Register extends AppCompatActivity {
    private  String username="";
    private String password="";
    private String email="";
    private String telephone="";
    EditText etUsername,
    etPassword,
    etEmail,
    etTelephone;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etUsername = findViewById(R.id.userName);
        etPassword = findViewById(R.id.password);
        etEmail = findViewById(R.id.email);
        etTelephone = findViewById(R.id.phone);
        DB = new DBHelper(this);

    }
    public void btnRegistrar(View v) {
         username = etUsername.getText().toString();
         email = etEmail.getText().toString();
         telephone = etTelephone.getText().toString();
         password = etPassword.getText().toString();

        if (username.isEmpty() || email.isEmpty()|| telephone.isEmpty() || password.isEmpty()) {// verificar que todos los campos sean llenados en su totalidad
            Toast.makeText(Register .this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            // VERIFICAR QUE EL USUARIO SEA UNICO Y NO HAYA OTRO IGUAL
            Boolean checkuser = DB.checkNombreUsuario(username);
            if (telephone.length() < 10) {
                Toast.makeText(Register.this, "Numero de telefono invalido", Toast.LENGTH_SHORT).show();
            } else {
                if (!checkuser) {
                    Boolean insert = DB.insertData(username, password, email,telephone );
                    if (insert) {
                        Toast.makeText(Register.this, "Registro exitoso!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Register.this, "Registro fallido", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Register.this, "Usuario existente", Toast.LENGTH_SHORT).show();
                }

            }
        }

    }


}