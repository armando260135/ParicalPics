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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

public class Login extends AppCompatActivity {
    EditText etUsername, etPassword;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Button btnLogin;
    private ProgressDialog progressDialogLoginUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.userameLogin);
        etPassword = findViewById(R.id.passwordLogin);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        btnLogin = findViewById(R.id.btnLogin);
        progressDialogLoginUser = new ProgressDialog(this);

        btnLogin.setOnClickListener(view -> {
            userLogin();
        });

    }

    //logueo para usuarios con firebase
    public void userLogin() {
        String mail = etUsername.getText().toString(); //correo
        String password = etPassword.getText().toString(); //contraseña

        if (TextUtils.isEmpty(mail)) {
            etUsername.setError("Ingrese un correo");
            etUsername.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etPassword.setError("Ingrese una contraseña");
            etPassword.requestFocus();
        } else {
            progressDialogLoginUser.setTitle("Iniciando Seción");
            progressDialogLoginUser.setMessage("Por Favor Espere un Momento");
            progressDialogLoginUser.setCancelable(false);
            progressDialogLoginUser.show();

            mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        queryDatabases(mail);
                        Toast.makeText(Login.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                        progressDialogLoginUser.dismiss();
                    } else {
                        progressDialogLoginUser.dismiss();
                        Log.w("TAG", "signInWithEmail:failure", task.getException());
                        Toast.makeText(Login.this, "Credenciales Incorrectas",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void queryDatabases(String mail){
       db.collection("users").whereEqualTo("Correo",mail)
               .get()
               .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
           @Override
           public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
               for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                   Intent intent = new Intent(Login.this,Home .class);
                   intent.putExtra("nombre", doc.getString("Nombre"));
                   startActivity(intent);
               }
           }
       });

    }

}