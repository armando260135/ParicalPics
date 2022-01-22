package com.example.inventarioparciales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    ArrayList<MateriasHome> listHome;
    RecyclerView recyclerView;
    LinearLayout dotslayout;
    SliderAdapter adapter;
    ViewPager2 pager2;
    Drawable list[];
    ImageView imgNotify,subirimg;

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

        imgNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iAdmin = new Intent(Home.this, IngresarMaterias.class);
                startActivity(iAdmin);
            }
        });

        subirimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iSubirImg = new Intent(Home.this, SubirParciales.class);
                startActivity(iSubirImg);
            }
        });



        llenarPersonajes();
        AdapterHome adapterHome = new AdapterHome(listHome);
        recyclerView.setAdapter(adapterHome);
    }


    private void llenarPersonajes() {
        listHome.add(new MateriasHome("Base De Datos",R.drawable.dime));
        listHome.add(new MateriasHome("Ingenieria De Software",R.drawable.softwa));
        listHome.add(new MateriasHome("Programacion I",R.drawable.python));
        listHome.add(new MateriasHome("Programacion II",R.drawable.python));
        listHome.add(new MateriasHome("Estructura De Datos",R.drawable.graphm));
        listHome.add(new MateriasHome("Redes y Computadores",R.drawable.network));
        listHome.add(new MateriasHome("Inteligencia Artificial",R.drawable.robot));
        listHome.add(new MateriasHome("Tecnicas De Investigacion",R.drawable.proyec1));
        listHome.add(new MateriasHome("Proyectos I",R.drawable.proyec1));


    }


}