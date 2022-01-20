package com.example.inventarioparciales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.inventarioparciales.databases.DBHelper;

public class Login extends AppCompatActivity {
    public static  String NAME_USER="";
    EditText etUsername, etPassword;
    private String username, password;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.userameLogin);
        etPassword = findViewById(R.id.passwordLogin);
        DB = new DBHelper(this);
    }
    public void btnAccesoLogin(View v) {
       username = etUsername.getText().toString();
       password = etPassword.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(Login.this, "Todos los campos deben ser llenados", Toast.LENGTH_SHORT).show();
        } else {
            Boolean checkuserpass = DB.checkNombreUsuarioClave(username, password);
            if (checkuserpass == true) {
                NAME_USER = username;
                Toast.makeText(Login.this, "datos correctos!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            } else {
                Toast.makeText(Login.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        }
    }


}