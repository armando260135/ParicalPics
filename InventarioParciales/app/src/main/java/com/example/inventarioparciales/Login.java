package com.example.inventarioparciales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.content.SharedPreferences;
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
    String userPreferences, passwordPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.userameLogin);
        etPassword = findViewById(R.id.passwordLogin);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        btnLogin = findViewById(R.id.btnLogin);
        userPreferences = "";
        passwordPreferences = "";
        progressDialogLoginUser = new ProgressDialog(this);
        uploadCredentials();
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
            progressDialogLoginUser.setTitle("Iniciando Sesión");
            progressDialogLoginUser.setMessage("Por Favor Espere un Momento");
            progressDialogLoginUser.setCancelable(false);
            progressDialogLoginUser.show();

            mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        queryDatabases(mail);
                        saveCredentialsForUsers();
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
        long tiempoI = System.currentTimeMillis();
       db.collection("users").whereEqualTo("Correo",mail)
               .get()
               .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
           @Override
           public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
               String nombre = "";
               String correo = "";
               for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                   nombre = doc.getString("Nombre");
                   correo = doc.getString("Correo");
               }
               Intent intent = new Intent(Login.this, Home .class);
               intent.putExtra("nombre", nombre);
               intent.putExtra("correo", correo);
               startActivity(intent);
               long tiempoF = System.currentTimeMillis();
               System.out.println("time->:"+(tiempoF-tiempoI));
               progressDialogLoginUser.dismiss();
           }

       });

    }
    public void saveCredentialsForUsers(){
        SharedPreferences preferences = getSharedPreferences("credentials", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nombreUser", etUsername.getText().toString());
        editor.putString("passwordUser", etPassword.getText().toString());
        editor.commit();

    }
    public void uploadCredentials(){
        SharedPreferences preferences = getSharedPreferences("credentials", this.MODE_PRIVATE);
        userPreferences = preferences.getString("nombreUser","");
        passwordPreferences = preferences.getString("passwordUser","");
        etUsername.setText(userPreferences);
        etPassword.setText(passwordPreferences);

    }
}