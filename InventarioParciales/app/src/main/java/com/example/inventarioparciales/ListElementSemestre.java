package com.example.inventarioparciales;

public class ListElementSemestre {
    private String semestre,materiasemestre,abrir;


    public ListElementSemestre( String semestre, String materiasemestre, String abrir) {

        this.semestre = semestre;
        this.materiasemestre = materiasemestre;
        this.abrir = abrir;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getMateriasemestre() {
        return materiasemestre;
    }

    public void setMateriasemestre(String materiasemestre) {
        this.materiasemestre = materiasemestre;
    }

    public String getAbrir() {
        return abrir;
    }

    public void setAbrir(String abrir) {
        this.abrir = abrir;
    }
}
