package com.example.inventarioparciales;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.inventarioparciales.databases.DBHelper;

public class IngresarMaterias extends AppCompatActivity {
    EditText etCodigo,etMateria;
    private String materia, codigo;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_materias);
        etCodigo = findViewById(R.id.etxIngresarCodMateria);
        etMateria = findViewById(R.id.etxIngresarMateria);
        DB = new DBHelper(this);
    }
    public void agregarMateria (View v){
        codigo = etCodigo.getText().toString();
        materia = etMateria.getText().toString();
        if(codigo.isEmpty() || materia.isEmpty()){
            Toast.makeText(this, "Debe llenar los campos", Toast.LENGTH_SHORT).show();
        }else{
            Boolean checkMaterias = DB.insertDataMaterias(Integer.parseInt(codigo),materia);
            if (checkMaterias){
                Toast.makeText(this, "Exito al registrar la materia", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Error al ingresar la materia", Toast.LENGTH_SHORT).show();
            }
        }
    }
}