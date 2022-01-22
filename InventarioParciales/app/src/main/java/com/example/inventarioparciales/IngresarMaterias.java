package com.example.inventarioparciales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.inventarioparciales.databases.DBHelper;

public class IngresarMaterias extends AppCompatActivity {
    EditText etCodigo,etMateria;
    private String materia, codigo,codigoicono;
    DBHelper DB;
    private EditText ingresaricono;
    private int iddrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_materias);
        etCodigo = findViewById(R.id.etxIngresarCodMateria);
        etMateria = findViewById(R.id.etxIngresarMateria);
        ingresaricono = findViewById(R.id.etxIngresarNombreIcono);
        DB = new DBHelper(this);


    }
    public void agregarMateria (View v){
        codigo = etCodigo.getText().toString();
        materia = etMateria.getText().toString();
        codigoicono=ingresaricono.getText().toString();
        iddrawable = getResources().getIdentifier(codigoicono, "drawable", getPackageName());
        if(codigo.isEmpty() || materia.isEmpty()){
            Toast.makeText(this, "Debe llenar los campos", Toast.LENGTH_SHORT).show();
        }else{
            Boolean checkMaterias = DB.insertDataMaterias(Integer.parseInt(codigo),materia,iddrawable);
            if (checkMaterias){
                Toast.makeText(this, "Exito al registrar la materia", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(IngresarMaterias.this, Home.class);
                startActivity(i);
            }else {
                Toast.makeText(this, "Error al ingresar la materia", Toast.LENGTH_SHORT).show();
            }
        }
    }
}