package com.example.inventarioparciales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VerSemestres extends AppCompatActivity {
    private Button btnsemes20201,btnsemes20202;
    public  static String ruta="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_semestres);
        btnsemes20201 = findViewById(R.id.btnSemestre20201);
        btnsemes20202 = findViewById(R.id.btnSemestre20202);
        ShowAllImagesFromStorage showAllImagesFromStorage = new ShowAllImagesFromStorage();

        btnsemes20201.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showAllImagesFromStorage.verParciales("Semestre 2020-1");
                ruta = "Semestre 2020-1";
                Intent verParcial20201 = new Intent(VerSemestres.this,ShowAllImagesFromStorage.class);
                startActivity(verParcial20201);
            }
        });

        btnsemes20202.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showAllImagesFromStorage.verParciales("Semestre 2020-2");
                ruta = "Semestre 2020-2";
                Intent verParcial20202 = new Intent(VerSemestres.this,ShowAllImagesFromStorage.class);
                startActivity(verParcial20202);
            }
        });
    }
}