package com.example.inventarioparciales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;

public class VerSemestres extends AppCompatActivity {
    public  static String rutasemestre="";
    private List<ListElementSemestre> elementSemestres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_semestres);
        init();
    }

    public void init(){
        elementSemestres = new ArrayList<>();
        elementSemestres.add(new ListElementSemestre("Semestre 2022-1","base de datos","Ver"));
        elementSemestres.add(new ListElementSemestre("Semestre 2022-2","base de datos","Ver"));
        elementSemestres.add(new ListElementSemestre("Semestre 2021-1","base de datos","Ver"));
        elementSemestres.add(new ListElementSemestre("Semestre 2021-2","base de datos","Ver"));
        elementSemestres.add(new ListElementSemestre("Semestre 2020-1","base de datos","Ver"));
        elementSemestres.add(new ListElementSemestre("Semestre 2020-2","base de datos","Ver"));

        AdapterListSemestre adapterListSemestre = new AdapterListSemestre(elementSemestres,this);
        RecyclerView recyclerView = findViewById(R.id.recyclerSemestre);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterListSemestre);
    }
}