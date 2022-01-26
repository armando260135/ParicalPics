package com.example.inventarioparciales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.inventarioparciales.databases.DBHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText etUsername, etPassword;
    DBHelper DB;
    private FirebaseAuth mAuth;
    private Button btnLogin;
    private ProgressDialog progressDialogLoginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.userameLogin);
        etPassword = findViewById(R.id.passwordLogin);
        DB = new DBHelper(this);
        mAuth = FirebaseAuth.getInstance();
        btnLogin = findViewById(R.id.btnLogin);
        progressDialogLoginUser = new ProgressDialog(this);

        btnLogin.setOnClickListener(view -> {
            userLogin();
        });

    }

    //logueo para usuarios con firebase
    public void userLogin(){
        String mail = etUsername.getText().toString(); //correo
        String password = etPassword.getText().toString(); //contraseña

        if (TextUtils.isEmpty(mail)){
            etUsername.setError("Ingrese un correo");
            etUsername.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            etPassword.setError("Ingrese una contraseña");
            etPassword.requestFocus();
        }else {
            progressDialogLoginUser.setTitle("Iniciando Seción");
            progressDialogLoginUser.setMessage("Por Favor Espere un Momento");
            progressDialogLoginUser.setCancelable(false);
            progressDialogLoginUser.show();

            mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Login.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this,Home.class));
                        progressDialogLoginUser.dismiss();
                    }else {
                        progressDialogLoginUser.dismiss();
                        Log.w("TAG", "signInWithEmail:failure", task.getException());
                        Toast.makeText(Login.this, "Credenciales Incorrectas",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}