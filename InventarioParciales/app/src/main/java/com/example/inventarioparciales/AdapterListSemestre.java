package com.example.inventarioparciales;

import static com.example.inventarioparciales.VerSemestres.rutasemestre;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterListSemestre extends RecyclerView.Adapter<AdapterListSemestre.ViewHolderSemestre> {

    private List<ListElementSemestre> semestresdata;
    private LayoutInflater semestreinflater;
    private Context context;
    private String materia;
    private String examen;
    private long ultimoClick= 0;

    public AdapterListSemestre(List<ListElementSemestre> itemList, Context context, String materia,String examen) {
        this.semestresdata = itemList;
        this.context = context;
        this.semestreinflater = LayoutInflater.from(context);
        this.materia = materia;
        this.examen = examen;
    }

    @NonNull
    @Override
    public AdapterListSemestre.ViewHolderSemestre onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = semestreinflater.inflate(R.layout.list_semestres,null);
        return new ViewHolderSemestre(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListSemestre.ViewHolderSemestre holder, int position) {
        rutasemestre = "";
        ListElementSemestre listElementSemestre = semestresdata.get(position);
        holder.binData(semestresdata.get(position));
        String semestre = listElementSemestre.getSemestre();
        holder.itemView.setOnClickListener(view -> {
            if (SystemClock.elapsedRealtime() - ultimoClick < 1000){
                return;
            }
            ultimoClick= SystemClock.elapsedRealtime();
            Intent datosSemestreClick = new Intent( context, ShowAllImagesFromStorage.class);
            datosSemestreClick.putExtra("semestreClick",semestre);
            datosSemestreClick.putExtra("materiaClick",materia);
            datosSemestreClick.putExtra("examenClick",examen);
            context.startActivity(datosSemestreClick);
        });
    }

    @Override
    public int getItemCount() {
        return semestresdata.size();
    }

    public void setItems(List<ListElementSemestre> items){
        semestresdata = items;
    }

    public class ViewHolderSemestre  extends RecyclerView.ViewHolder{

        ImageView iconImage;
        TextView semestre,materiassemestre,abrir;

        public ViewHolderSemestre(@NonNull View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageSemestre);
            semestre = itemView.findViewById(R.id.nombreSemestre);
            materiassemestre = itemView.findViewById(R.id.nombreMateriaSemestre);
            abrir = itemView.findViewById(R.id.abrirSemestre);
        }

        void binData(final ListElementSemestre item) {
            semestre.setText(item.getSemestre());
            materiassemestre.setText(item.getMateriasemestre());
            abrir.setText(item.getAbrir());
        }

    }
}
