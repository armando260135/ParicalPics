package com.example.inventarioparciales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText etUsername,
    etPassword,
    etEmail,
    etTelephone;

    //firebase register
    private FirebaseAuth mAuth;
    private String userID;
    private FirebaseFirestore db;
    private Button btnRegistrarse;
    private ProgressDialog progressDialogRegisterUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etUsername = findViewById(R.id.userName);
        etPassword = findViewById(R.id.password);
        etEmail = findViewById(R.id.email);
        etTelephone = findViewById(R.id.phone);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        progressDialogRegisterUser = new ProgressDialog(this);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnRegistrarse.setOnClickListener(view -> {
            createUser();
        });
    }

    //Registro de usuarios en firebase
    public void createUser(){
        String name = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String mail = etEmail.getText().toString();
        String phone = etTelephone.getText().toString();

        if (TextUtils.isEmpty(name)){
            etUsername.setError("Ingrese un nombre");
            etUsername.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            etPassword.setError("Ingrese una contraseña");
            etPassword.requestFocus();
        }else if (TextUtils.isEmpty(mail)){
            etEmail.setError("Ingrese un correo");
            etEmail.requestFocus();
        }else if (TextUtils.isEmpty(phone)){
            etTelephone.setError("Ingrese un telefono");
            etTelephone.requestFocus();
        }else {
            progressDialogRegisterUser.setTitle("Registrando Usuario");
            progressDialogRegisterUser.setMessage("Por Favor Espere un Momento");
            progressDialogRegisterUser.setCancelable(false);
            progressDialogRegisterUser.show();
            mAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        userID = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = db.collection("users").document(userID);

                        Map<String,Object> user = new HashMap<>();
                        user.put("Nombre", name);
                        user.put("Password", password);
                        user.put("Correo", mail);
                        user.put("Telefono", phone);

                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG","onSuccess: Datos Registrados" + userID);
                                progressDialogRegisterUser.dismiss();
                            }
                        });
                        Toast.makeText(Register.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register.this,Login.class));
                    }else{
                        progressDialogRegisterUser.dismiss();
                        Toast.makeText(Register.this, "Usuario no registrado: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}

