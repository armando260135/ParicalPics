package com.example.inventarioparciales;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    private ProgressBar progressBar;
    String nombreUsuario="";
    String correo = "";
    String telefono = "";
    private int iddrawable;
//basicaly

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        progressBar = findViewById(R.id.progress_materias);
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
        telefono = getIntent().getStringExtra("telefono");
        tvusername.setText(nombreUsuario);

        iconprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iSubirImg = new Intent(Home.this, Perfil.class);
                iSubirImg.putExtra("nombre_perfil",nombreUsuario);
                iSubirImg.putExtra("correo_perfil",correo);
                iSubirImg.putExtra("telefono_perfil", telefono);
                startActivity(iSubirImg);
                iconprofile.setEnabled(false);
            }
        });

        imgNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        recyclerView.setVisibility(View.VISIBLE);
        gridMaterias.setVisibility(View.VISIBLE);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d("network", " "+networkInfo);
        } else {
            toast();
            Log.d("network", " "+networkInfo);
        }
        llenarMaterias();
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
                            //int icon = Integer.parseInt(sn.child("foto").getValue().toString());
                            iddrawable = getResources().getIdentifier(sn.child("foto").getValue().toString(), "raw", getPackageName());
                            String codigo = sn.child("codigo").getValue().toString();
                            listHome.add(new MateriasHome(nombre, iddrawable, codigo));
                            AdapterHome adapterHome = new AdapterHome(listHome, Home.this);
                            recyclerView.setAdapter(adapterHome);
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                } else {
                    listHome.clear();
                    recyclerView.setVisibility(View.GONE);
                    txtsinmaterias.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_faitured_BD) + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        iconprofile.setEnabled(true);
    }
}