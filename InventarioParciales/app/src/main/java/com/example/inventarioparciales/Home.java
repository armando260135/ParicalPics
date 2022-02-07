package com.example.inventarioparciales;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    ArrayList<MateriasHome> listHome;
    RecyclerView recyclerView;
    LinearLayout dotslayout;
    SliderAdapter adapter;
    ViewPager2 pager2;
    Drawable list[];
    ImageView imgNotify,iconprofile;
    GridLayout gridMaterias;
    TextView txtsinmaterias,tvusername;
    private ProgressDialog progressDialog;
    String nombreUsuario="";
    String correo = "";
    Network network = new Network();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        progressDialog = new ProgressDialog(this);
        listHome = new ArrayList<>();
        recyclerView = findViewById(R.id.RecyclerId);
        imgNotify = findViewById(R.id.imgNotify);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        dotslayout = findViewById(R.id.containerPuntos);
        pager2 = findViewById(R.id.viewPager2);
        iconprofile = findViewById(R.id.profile);
        gridMaterias = findViewById(R.id.gridMaterias);
        txtsinmaterias = findViewById(R.id.txtsinmaterias);
        tvusername = findViewById(R.id.tvHomeUsername);
        list = new Drawable[3];
        list[0] = getResources().getDrawable(R.drawable.bannerperfecto);
        list[1] = getResources().getDrawable(R.drawable.bannerperfecto2);
        list[2] = getResources().getDrawable(R.drawable.bannerperfecto3);
        adapter = new SliderAdapter(list);
        pager2.setAdapter(adapter);
        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
        correo = getIntent().getStringExtra("correo");
        nombreUsuario = getIntent().getStringExtra("nombre").toUpperCase();
        tvusername.setText(nombreUsuario);

        iconprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iSubirImg = new Intent(Home.this, Perfil.class);
                iSubirImg.putExtra("nombre_perfil",nombreUsuario);
                iSubirImg.putExtra("correo_perfil",correo);
                startActivity(iSubirImg);
            }
        });

        imgNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        llenarMaterias();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (network.isOnlineNet())
//            Log.e("accInternet",   Boolean.toString(network.isOnlineNet()));
//        else{
//            progressDialog.dismiss();
//            toast();
//            Log.e("accInternet",   Boolean.toString(network.isOnlineNet()));
//        }
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

    private void llenarMaterias() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        progressDialog.setTitle("Cargando Materias");
        progressDialog.setMessage("Por Favor Espere un Momento");
        progressDialog.setCancelable(false);
        progressDialog.show();
        reference.child("Asignaturas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    listHome.clear();
                    for (DataSnapshot sn : snapshot.getChildren()) {
                        if (sn == null) {
                            txtsinmaterias.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        } else {
                            txtsinmaterias.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            gridMaterias.setVisibility(View.VISIBLE);
                            String nombre = sn.child("nombre").getValue().toString();
                            int icon = Integer.parseInt(sn.child("foto").getValue().toString());
                            String codigo = sn.child("codigo").getValue().toString();
                            listHome.add(new MateriasHome(nombre, icon, codigo));
                            AdapterHome adapterHome = new AdapterHome(listHome, Home.this);
                            recyclerView.setAdapter(adapterHome);
                            progressDialog.dismiss();
                        }
                    }

                } else {
                    progressDialog.dismiss();
                    listHome.clear();
                    recyclerView.setVisibility(View.GONE);
                    txtsinmaterias.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "sin ijuemadre conexion con la bd" + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}