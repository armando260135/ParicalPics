package com.example.inventarioparciales;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    ImageView imgNotify,subirimg;
    GridLayout gridMaterias;
    TextView txtsinmaterias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        listHome = new ArrayList<>();
        recyclerView = findViewById(R.id.RecyclerId);
        imgNotify = findViewById(R.id.imgNotify);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        dotslayout = findViewById(R.id.containerPuntos);
        pager2 = findViewById(R.id.viewPager2);
        subirimg = findViewById(R.id.subirImg);
        gridMaterias = findViewById(R.id.gridMaterias);
        txtsinmaterias = findViewById(R.id.txtsinmaterias);

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

        subirimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iSubirImg = new Intent(Home.this, SubirParciales.class);
                startActivity(iSubirImg);
            }
        });

        llenarMaterias();
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
                            int icon = Integer.parseInt(sn.child("foto").getValue().toString());
                            String codigo = sn.child("codigo").getValue().toString();
                            listHome.add(new MateriasHome(nombre, icon, codigo));
                            AdapterHome adapterHome = new AdapterHome(listHome);
                            recyclerView.setAdapter(adapterHome);
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
                Toast.makeText(getApplicationContext(), "sin ijuemadre conexion con la bd" + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}