package com.example.inventarioparciales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {
    EditText etUsername, etPassword;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Button btnLogin;
    private ProgressDialog progressDialogLoginUser;
    String userPreferences, passwordPreferences;
    public static Boolean flagnetworkfailtured = true;

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
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                Log.d("network", " "+networkInfo);
                flagnetworkfailtured = true;
            } else {
                toast();
                flagnetworkfailtured = false;
                Log.d("network", " "+networkInfo);
            }
            userLogin();
        });

    }

    //logueo para usuarios con firebase
    public void userLogin() {
        String mail = etUsername.getText().toString().toLowerCase(); //correo
        String password = etPassword.getText().toString(); //contraseña

        if (TextUtils.isEmpty(mail)) {
            etUsername.setError(getResources().getString(R.string.etx_correo));
            etUsername.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etPassword.setError(getResources().getString(R.string.etx_contrasena));
            etPassword.requestFocus();
        } else {
            progressDialogLoginUser.setTitle(getResources().getString(R.string.txt_progress_title));
            progressDialogLoginUser.setMessage(getResources().getString(R.string.txt_progress_msg));
            progressDialogLoginUser.setCancelable(false);
            progressDialogLoginUser.show();

            if (flagnetworkfailtured){
                mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            queryDatabases(mail);
                            saveCredentialsForUsers();
                        } else {
                            progressDialogLoginUser.dismiss();
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, getResources().getString(R.string.login_faiured),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else {
                progressDialogLoginUser.dismiss();
            }
        }
    }

    public void queryDatabases(String mail){
       db.collection("users").whereEqualTo("Correo",mail)
               .get()
               .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
           @Override
           public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
               String nombre = "";
               String correo = "";
               String telefono = "";
               for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                   nombre = doc.getString("Nombre");
                   correo = doc.getString("Correo");
                   telefono = doc.getString("Telefono");
               }
               Intent intent = new Intent(Login.this, Home.class);
               intent.putExtra("nombre", nombre);
               intent.putExtra("correo", correo);
               intent.putExtra("telefono", telefono);
               startActivity(intent);
               progressDialogLoginUser.dismiss();
           }

       });

    }

    public void toast() {
        final RelativeLayout relativeLayout = findViewById(R.id.msgAccount);
        relativeLayout.setVisibility(View.VISIBLE);
        Button btnEntentido = findViewById(R.id.entendidoMsg);

        relativeLayout.setOnClickListener(v -> {
            //nothing
        });
        btnEntentido.setOnClickListener(v ->{
            relativeLayout.setVisibility(View.INVISIBLE);
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