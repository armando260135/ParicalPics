package com.example.inventarioparciales;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.inventarioparciales.databases.DBHelper;
import java.util.ArrayList;

public class Home extends AppCompatActivity {
    ArrayList<MateriasHome> listHome;
    RecyclerView recyclerView;
    LinearLayout dotslayout;
    SliderAdapter adapter;
    ViewPager2 pager2;
    Drawable list[];
    ImageView imgNotify,subirimg;
    DBHelper DB;
    GridLayout gridMaterias;
    TextView txtsinmaterias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DB = new DBHelper(this);
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

        imgNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iAdmin = new Intent(Home.this, VerSemestres.class);
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

        llenarMaterias();
        AdapterHome adapterHome = new AdapterHome(listHome);
        recyclerView.setAdapter(adapterHome);
    }

    private void llenarMaterias() {
        SQLiteDatabase data = DB.getWritableDatabase();
        Cursor fila = data.rawQuery(
                "select nombremateria,codigoicono from materias", null);
        if(fila.moveToFirst()){
            gridMaterias.setVisibility(View.VISIBLE);
            do{
                listHome.add(new MateriasHome(fila.getString(0),fila.getInt(1)));
            }while (fila.moveToNext());

        }else
            txtsinmaterias.setVisibility(View.VISIBLE);
    }
}