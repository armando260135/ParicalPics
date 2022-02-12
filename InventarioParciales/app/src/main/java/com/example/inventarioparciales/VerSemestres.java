package com.example.inventarioparciales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class VerSemestres extends AppCompatActivity {
    public  static String rutasemestre="";
    private List<ListElementSemestre> elementSemestres;
    private String materia;
    RadioGroup rbgTipoExamen;
    RadioButton rbParcial_1, rbParcial_2, rbExamen;
    private String TYPE_EXAMEN="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_semestres);
        materia = getIntent().getStringExtra("Materia");
        rbgTipoExamen = findViewById(R.id.rbgTipoExamen);
        rbParcial_1 = findViewById(R.id.rbParcial_1);
        rbParcial_2 = findViewById(R.id.rbParcial_2);
        rbExamen = findViewById(R.id.rbExamen);
        rbgTipoExamen.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rbParcial_1.isChecked()){
                    TYPE_EXAMEN= "1 Parcial";
                   init();
                }else if(rbParcial_2.isChecked()){
                    TYPE_EXAMEN="2 Parcial";
                    init();
                }else{
                    TYPE_EXAMEN="Examen Final";
                    init();
                }
            }
        });

    }

    public void init(){
        elementSemestres = new ArrayList<>();
        elementSemestres.add(new ListElementSemestre("Semestre 2022-1",materia,"Ver"));
        elementSemestres.add(new ListElementSemestre("Semestre 2022-2",materia,"Ver"));
        elementSemestres.add(new ListElementSemestre("Semestre 2021-1",materia,"Ver"));
        elementSemestres.add(new ListElementSemestre("Semestre 2021-2",materia,"Ver"));
        elementSemestres.add(new ListElementSemestre("Semestre 2020-1",materia,"Ver"));
        elementSemestres.add(new ListElementSemestre("Semestre 2020-2",materia,"Ver"));

        AdapterListSemestre adapterListSemestre = new AdapterListSemestre(elementSemestres,this, materia,TYPE_EXAMEN);
        RecyclerView recyclerView = findViewById(R.id.recyclerSemestre);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterListSemestre);
    }
}